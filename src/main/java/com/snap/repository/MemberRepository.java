package com.snap.repository;

import com.snap.domain.Member;
import com.snap.repository.queryDSL.MemberRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, String>, MemberRepositoryCustom {
    Optional<Member> findByMemNickname(String nickname);
    Optional<Member> findByRefreshToken(String token);
}



