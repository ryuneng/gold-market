package com.ryuneng.goldauth.domain.user.dto;

import com.ryuneng.goldauth.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "회원가입 응답 DTO")
public class UserCreateResponse {

    @Schema(description = "사용자 ID", example = "3")
    private Long id;
    @Schema(description = "사용자 이메일", example = "user1@test.com")
    private String email;

    public UserCreateResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
    }

}
