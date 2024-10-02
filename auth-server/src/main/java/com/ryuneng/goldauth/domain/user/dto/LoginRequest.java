package com.ryuneng.goldauth.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "로그인 요청 DTO")
public class LoginRequest {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Schema(description = "사용자 이메일", example = "user1@test.com")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Schema(description = "사용자 비밀번호", example = "test1234")
    private String password;
}
