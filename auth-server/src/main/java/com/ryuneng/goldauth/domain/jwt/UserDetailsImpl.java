package com.ryuneng.goldauth.domain.jwt;

import com.ryuneng.goldauth.domain.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public record UserDetailsImpl(User user) implements UserDetails { // 인증된 사용자 정보를 제공하는 클래스

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return user.getRoleList();
    }

    @Override
    public String getPassword() {

        return user.getPassword();
    }

    @Override
    public String getUsername() {

        return user.getEmail();
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
}
