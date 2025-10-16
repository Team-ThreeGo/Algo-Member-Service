package com.threego.algomemberservice.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUserDetails extends User {
    private final int memberId;
    private final String nickname;

    public CustomUserDetails(int memberId,
                             String username,
                             String password,
                             String nickname,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.memberId = memberId;
        this.nickname = nickname;
    }
}

