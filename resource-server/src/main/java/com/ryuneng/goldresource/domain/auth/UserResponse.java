package com.ryuneng.goldresource.domain.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 인증 결과를 담고 있는 응답 객체")
public record UserResponse(
        @Schema(description = "인증 성공 여부") boolean success,
        @Schema(description = "사용자 이메일") String email) {
}
