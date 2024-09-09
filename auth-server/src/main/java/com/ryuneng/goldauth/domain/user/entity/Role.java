package com.ryuneng.goldauth.domain.user.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum Role implements GrantedAuthority {

    ADMIN("관리자"),
    MANAGER("매니저"),
    USER("사용자");

    private final String description;

    Role(String description) {

        this.description = description;
    }

    @Override
    public String getAuthority() {

        return "ROLE_" + this.name();
    }
}
