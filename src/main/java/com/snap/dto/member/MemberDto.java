package com.snap.dto.member;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

public class MemberDto {

    @Data
    public static class RefreshTokenRequest{
        @NotEmpty
        private String refreshToken;
    }

    @Data
    public static class CreateMemberRequest {
        @NotEmpty
        private String mem_email;
        @NotEmpty
        private String mem_name;
        @NotEmpty
        private String mem_nickname;
        @NotEmpty
        private String mem_password;
        @NotEmpty
        private String mem_phone;
    }

    @Data
    public static class MemberLoginRequest {
        @NotEmpty
        private String mem_email;
        @NotEmpty
        private String mem_password;
    }
}
