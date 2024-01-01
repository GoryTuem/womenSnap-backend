package com.snap.repository.queryDSL.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.snap.domain.*;
import com.snap.dto.board.BoardListResponse;
import com.snap.dto.board.UserBoardResponse;
import com.snap.repository.queryDSL.BoardRepositoryCustom;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.querydsl.core.group.GroupBy.*;

@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    QBoard board = QBoard.board;
    QBoardTag boardTags= QBoardTag.boardTag;
    QBoardFile file = QBoardFile.boardFile;
    QTag tag = QTag.tag;
    QMemberLike memberLike = QMemberLike.memberLike;
    QMember member = QMember.member;

    @Override
    public List<UserBoardResponse> findMemberBoard(String mem_email, Long pagingNum){

        return queryFactory
                .selectFrom(board)
                .join(board.member, member)
                .join(board.boardFiles, file)
                .join(board.boardTags, boardTags)
                .join(boardTags.tag, tag)
                .where(board.member.mem_email.eq(mem_email))
                .distinct()
                .transform(
                        groupBy(board.id).list(
                                Projections.constructor(UserBoardResponse.class
                                     ,board.id
                                     ,member.memNickname
                                     ,set(tag.tagName)
                                     ,set(file.filePath)
                                )
                        )
                );
    }

    @Override
    public List<UserBoardResponse> findMemberLike(String mem_email, Long pagingNum){
        return queryFactory
                .selectFrom(board)
                .join(board.member, member)
                .join(board.memberLikes, memberLike)
                .join(board.boardFiles, file)
                .join(board.boardTags, boardTags)
                .join(boardTags.tag, tag)
                .where(memberLike.member.mem_email.eq(mem_email))
                .distinct()
                .transform(
                        groupBy(board.id).list(
                                Projections.constructor(UserBoardResponse.class
                                        ,board.id
                                        ,member.memNickname
                                        ,set(tag.tagName)
                                        ,set(file.filePath)

                                )
                        )
                );
    }

    @Override
    public List<Tag> findTopTagList() {
        return queryFactory.select(tag)
                .from(boardTags)
                .join(boardTags.tag,tag)
                .groupBy(tag)
                .orderBy(boardTags.count().desc())
                .offset(1)
                .limit(10)
                .fetch();
    }

    @Override
    public List<BoardListResponse> getBoardList(Long[] tagId, String mem_email) {
        return queryFactory
                .selectFrom(board)
                .join(board.member, member)
                .join(board.boardFiles, file)
                .join(board.boardTags, boardTags)
                .join(boardTags.tag, tag)
                .leftJoin(board.memberLikes, memberLike)
                .on(memberLike.member.mem_email.eq(mem_email))
                .where(tagEq(tagId))
                .distinct()
                .transform(
                        groupBy(board.id).list(
                                Projections.constructor(BoardListResponse.class
                                        ,board.id
                                        ,member.memNickname
                                        ,set(tag.tagName)
                                        ,set(file.filePath)
                                        ,set(memberLike.member.mem_email)

                                )
                        )
                );
    }

    private BooleanExpression tagEq(Long[] tagId) {	// (2)
        return tagId.length > 0  ? tag.id.in(tagId) : null;
    }
}
