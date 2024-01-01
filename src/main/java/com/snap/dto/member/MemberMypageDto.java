package com.snap.dto.member;

import lombok.Data;
@Data
public class MemberMypageDto {

    private String mem_email;
    private String mem_name;
    private String mem_nickname;
    private int writeCount;
    private int likeCount;

}