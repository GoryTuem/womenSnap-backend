package com.snap.domain;

import lombok.Getter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
public class Member implements UserDetails {

    @Id
    private String mem_email;

    private String mem_password;

    @Enumerated(EnumType.STRING)
    private MemberTypes mem_type;

    private String mem_name;
    @Column(name = "mem_nickname")
    private String memNickname;
    private String mem_phone;
    private String mem_receive_yn;
    private LocalDateTime mem_join_date;
    private LocalDateTime mem_modify_date;
    @Enumerated(EnumType.STRING)
    private RoleType mem_role;
    private String refreshToken;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.getMem_role().toString()));
        return authorities;
    }
    @Override
    public String getUsername() {
        return mem_email;
    }

    @Override
    public String getPassword() {
        return mem_password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public static Member createMember(String mem_email, String mem_password, MemberTypes mem_type, String mem_name, String mem_nickname, String mem_phone, String mem_receive_yn , RoleType mem_role) {
        Member member = new Member();
        member.mem_email = mem_email;
        member.mem_password = mem_password;
        member.mem_type = mem_type;
        member.mem_name = mem_name;
        member.memNickname = mem_nickname;
        member.mem_phone = mem_phone;
        member.mem_receive_yn = mem_receive_yn;
        member.mem_join_date = LocalDateTime.now();
        member.mem_role = mem_role;
        return member;
    }

    public void encodingPassword(String mem_password) {
        this.mem_password = mem_password;
    }

    public void updateToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}