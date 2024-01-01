package com.snap.dto.board;

import com.querydsl.core.annotations.QueryProjection;
import com.snap.domain.*;
import lombok.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class UserBoardResponse {

    private Long board_id;
    private String member_nickname;
    private Set<String> tags = new LinkedHashSet<>();
    private Set<String> files = new LinkedHashSet<>();

    @QueryProjection
    public UserBoardResponse(Long board_id, String member_nickname, Set<String> tagResponsesIn , Set<String> fileResponsesIn){
        this.board_id = board_id;
        this.member_nickname = member_nickname;
        this.tags = tagResponsesIn;
        this.files = fileResponsesIn;
    }
}