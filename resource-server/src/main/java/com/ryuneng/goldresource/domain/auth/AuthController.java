package com.ryuneng.goldresource.domain.auth;

import com.ryuneng.goldresource.grpc.AuthServiceClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "사용자 인증 관련 API")
public class AuthController {

    private final AuthServiceClient authServiceClient;

    @Operation(summary = "사용자 인증", description = "gRPC 통신을 통해 사용자의 액세스 토큰을 검증하고, 사용자 정보를 반환합니다.")
    @GetMapping
    public UserResponse authenticateUser(@RequestParam(name = "accessToken") String accessToken) {
        // AccessToken을 이용해 인증 서버에 gRPC 요청
        return authServiceClient.authenticateUser(accessToken);
    }
}
