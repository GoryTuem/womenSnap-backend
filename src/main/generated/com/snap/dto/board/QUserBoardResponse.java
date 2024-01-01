package com.snap.dto.board;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * com.snap.dto.board.QUserBoardResponse is a Querydsl Projection type for UserBoardResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QUserBoardResponse extends ConstructorExpression<UserBoardResponse> {

    private static final long serialVersionUID = 490068398L;

    public QUserBoardResponse(com.querydsl.core.types.Expression<Long> board_id, com.querydsl.core.types.Expression<String> member_nickname, com.querydsl.core.types.Expression<? extends java.util.Set<String>> tagResponsesIn, com.querydsl.core.types.Expression<? extends java.util.Set<String>> fileResponsesIn) {
        super(UserBoardResponse.class, new Class<?>[]{long.class, String.class, java.util.Set.class, java.util.Set.class}, board_id, member_nickname, tagResponsesIn, fileResponsesIn);
    }

}

