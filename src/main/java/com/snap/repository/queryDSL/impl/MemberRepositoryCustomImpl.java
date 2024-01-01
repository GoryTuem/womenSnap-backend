package com.snap.repository.queryDSL.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.snap.domain.Board;
import com.snap.domain.Member;
import com.snap.domain.QBoard;
import com.snap.domain.QMemberLike;
import com.snap.repository.queryDSL.MemberRepositoryCustom;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom
{
    private final JPAQueryFactory queryFactory;

    /*작성한 게시글 카운트*/
    @Override
    public Integer  findMemberBoardCount(String mem_email) {
        QBoard board = QBoard.board;
        return Math.toIntExact(queryFactory
                .select(board.count())
                .from(board)
                .where(board.member.mem_email.eq(mem_email))
                .fetchFirst());
    }

    /*좋아요한 게시글 카운트*/
    @Override
    public Integer findMemberLikeCount(String mem_email) {
        QMemberLike like = QMemberLike.memberLike;
        return Math.toIntExact(queryFactory
                .select(like.count())
                .from(like)
                .where(like.member.mem_email.eq(mem_email))
                .fetchFirst());
    }
}
