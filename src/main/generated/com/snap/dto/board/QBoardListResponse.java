package com.snap.dto.board;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * com.snap.dto.board.QBoardListResponse is a Querydsl Projection type for BoardListResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QBoardListResponse extends ConstructorExpression<BoardListResponse> {

    private static final long serialVersionUID = -1296533641L;

    public QBoardListResponse(com.querydsl.core.types.Expression<Long> board_id, com.querydsl.core.types.Expression<String> member_nickname, com.querydsl.core.types.Expression<? extends java.util.Set<String>> tagResponsesIn, com.querydsl.core.types.Expression<? extends java.util.Set<String>> fileResponsesIn, com.querydsl.core.types.Expression<? extends java.util.Set<String>> memberLike) {
        super(BoardListResponse.class, new Class<?>[]{long.class, String.class, java.util.Set.class, java.util.Set.class, java.util.Set.class}, board_id, member_nickname, tagResponsesIn, fileResponsesIn, memberLike);
    }

}

