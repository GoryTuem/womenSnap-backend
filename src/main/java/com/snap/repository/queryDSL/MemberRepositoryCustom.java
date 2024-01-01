package com.snap.repository.queryDSL;

import com.snap.domain.Board;
import com.snap.domain.Member;

import java.util.List;
import java.util.Optional;

public interface  MemberRepositoryCustom {


    Integer  findMemberBoardCount(String mem_email);
    Integer findMemberLikeCount(String mem_email);
}
