package com.ryuneng.goldauth.domain.user.dto;

import com.ryuneng.goldauth.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateResponse {

    private Long id;
    private String email;

    public UserCreateResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
    }

}
