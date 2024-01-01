package com.snap.dto.board;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class BoardListResponse {

    private Long board_id;
    private String likeYN;
    private String member_nickname;
    private Set<String> tags = new LinkedHashSet<>();
    private Set<String> files = new LinkedHashSet<>();

    @QueryProjection
    public BoardListResponse(Long board_id,String member_nickname, Set<String> tagResponsesIn , Set<String> fileResponsesIn, Set<String> memberLike){
        this.board_id = board_id;
        this.tags = tagResponsesIn;
        this.files = fileResponsesIn;
        this.member_nickname = member_nickname;
        if(!memberLike.isEmpty()){
            this.likeYN = "Y";
        }else{
            this.likeYN = "N";
        }
    }
}