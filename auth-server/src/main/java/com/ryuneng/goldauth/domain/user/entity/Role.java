package com.ryuneng.goldauth.domain.user.entity;

import lombok.Getter;

@Getter
public enum Role {

    ADMIN("관리자"),
    MANAGER("매니저"),
    USER("사용자");

    private final String description;

    Role(String description) {

        this.description = description;
    }

}
