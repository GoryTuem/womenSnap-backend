package com.snap.api;

import com.snap.dto.member.MemberDto;
import com.snap.errorhandling.dto.Code;
import com.snap.errorhandling.dto.DataResponseDto;
import com.snap.errorhandling.dto.ErrorResponseDto;
import com.snap.errorhandling.dto.ResponseDto;
import com.snap.jwt.TokenInfo;
import com.snap.dto.member.MemberMypageDto;
import com.snap.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;


@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/api/auth/join")
    public ResponseDto JoinMember(@RequestBody @Valid MemberDto.CreateMemberRequest request){
        try {
            String id = memberService.join(request);
            return DataResponseDto.of(id);
        }catch (Exception e){
            return ErrorResponseDto.of(Code.VALIDATION_ERROR,e.getMessage());
        }
    }

    @PostMapping("/api/auth/login")
    public ResponseDto login(@RequestBody MemberDto.MemberLoginRequest memberLoginRequest) {
        try{
            String mem_email = memberLoginRequest.getMem_email();
            String mem_password = memberLoginRequest.getMem_password();
            TokenInfo tokenInfo =  memberService.login(mem_email, mem_password);
            return DataResponseDto.of(tokenInfo);
        }catch (Exception e){
            return ErrorResponseDto.of(Code.NOT_FOUND,"일치하는 회원이 없습니다.");
        }
    }

    @PostMapping("/api/auth/logout")
    public ResponseEntity<String> logout(Authentication authentication) {
        memberService.deleteToken(authentication.getName());
        SecurityContextHolder.getContext().setAuthentication(null);
        return  ResponseEntity.ok().body(authentication.getName());
    }

    @PostMapping("/api/auth/refresh")
    public TokenInfo myPage(@RequestBody MemberDto.RefreshTokenRequest refreshTokenRequest) {

        return memberService.refresh(refreshTokenRequest.getRefreshToken());
    }

    @GetMapping("/api/user/mypage")
    public ResponseDto myPage(Principal principal) {
        try{
            MemberMypageDto member = memberService.findMember(principal.getName());
            return DataResponseDto.of(member);
        }catch (Exception e){
            return ErrorResponseDto.of(Code.NOT_FOUND,"일치하는 회원이 없습니다.");
        }
    }

}
