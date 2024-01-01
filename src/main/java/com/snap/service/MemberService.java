package com.snap.service;

import com.snap.domain.Member;
import com.snap.domain.MemberTypes;
import com.snap.domain.RoleType;
import com.snap.dto.member.MemberDto;
import com.snap.errorhandling.exception.ForbiddenException;
import com.snap.jwt.JwtTokenProvider;
import com.snap.jwt.TokenInfo;
import com.snap.dto.member.MemberMypageDto;
import com.snap.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    private final RedisTemplate<String, Object> redisTemplate;
    private long refreshTokenValidTime = Duration.ofDays(1).toMillis(); // 만료시간 하루

    /* 회원 가입 */
    @Transactional
    public String join(MemberDto.CreateMemberRequest request){
        //이메일, 닉네임 중복 검사.
        validateMember(request.getMem_email());
        validateNickname(request.getMem_nickname());

        Member member = Member.createMember(request.getMem_email()
                , request.getMem_password()
                , MemberTypes.EMAIL
                , request.getMem_name()
                , request.getMem_nickname()
                , request.getMem_phone()
                , "Y"
                , RoleType.USER);
        member.encodingPassword(passwordEncoder.encode(member.getMem_password()));

        memberRepository.save(member);
        return member.getMem_email();
    }
    /* 이메일 중복 체크 */
    public void validateMember(String email){
        Optional<Member> findMember = memberRepository.findById(email);
        if(findMember.isPresent()){
           throw new IllegalStateException("이미 사용중인 이메일입니다.");
        }
    }

    public void validateNickname(String nickname){
        Optional<Member> findMember = memberRepository.findByMemNickname(nickname);
        if(findMember.isPresent()){
            throw new IllegalStateException("이미 사용중인 닉네임입니다.");
        }
    }

    /* 로그인 체크 */
    @Transactional
    public TokenInfo login(String memberId, String password) {

        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        Optional<Member> fmember = memberRepository.findById(memberId);
        fmember.ifPresent(member -> {
            member.updateToken(tokenInfo.getRefreshToken());
            memberRepository.save(member);
        });

        return tokenInfo;

    }
    @Transactional
    public TokenInfo refresh(String requestToken){
        Optional<Member> fmember = memberRepository.findByRefreshToken(requestToken);
        fmember.orElseThrow(() -> new ForbiddenException("회원 정보가 존재하지 않습니다."));

        String mem_email = fmember.get().getMem_email();

        String redisRefreshToken = (String) redisTemplate.opsForValue().get(fmember.get().getMem_email());
        if(redisRefreshToken == null || !redisRefreshToken.equals(requestToken)){
            throw new ForbiddenException("refresh 토큰이 일치하지 않습니다.");
        }


        String accessToken = jwtTokenProvider.createAccessToken(mem_email, "ROLE_"+fmember.get().getMem_role().name());
        String refreshToken = jwtTokenProvider.createRefreshToken();

        TokenInfo tokenInfo = TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .mem_email(mem_email)
                .build();

        // refreshToken 다시 저장 해주자!
        //redis 정보 삭제 후 토큰 발급시 다시 저장.
        redisTemplate.delete(mem_email);
        //redis 저장
        redisTemplate.opsForValue().set(mem_email, refreshToken, refreshTokenValidTime, TimeUnit.MILLISECONDS);
        //db 업데이트
        fmember.ifPresent(member -> {
            member.updateToken(refreshToken);
            memberRepository.save(member);
        });

        return tokenInfo;
    }

    @Transactional
    public void deleteToken(String mem_email){

        Optional<Member> fmember = memberRepository.findById(mem_email);
        fmember.ifPresent(member -> {
            member.updateToken("");
            memberRepository.save(member);
        });

        redisTemplate.delete(mem_email);

    }

    /* 마이페이지 정보 조회 */
    public MemberMypageDto findMember(String mem_email) {
        Optional<Member> fmember = memberRepository.findById(mem_email);
        MemberMypageDto memberMypageDto = new MemberMypageDto();

        fmember.ifPresent(member -> {
            memberMypageDto.setMem_email(member.getMem_email());
            memberMypageDto.setMem_nickname(member.getMemNickname());
            memberMypageDto.setMem_name(member.getMem_name());
            memberMypageDto.setWriteCount(memberRepository.findMemberBoardCount(member.getMem_email()));
            memberMypageDto.setLikeCount(memberRepository.findMemberLikeCount(member.getMem_email()));

        });

        return memberMypageDto;
    }
}
