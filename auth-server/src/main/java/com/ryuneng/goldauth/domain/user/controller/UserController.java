package com.ryuneng.goldauth.domain.user.controller;

import com.ryuneng.goldauth.domain.jwt.TokenProvider;
import com.ryuneng.goldauth.domain.user.dto.LoginRequest;
import com.ryuneng.goldauth.domain.user.dto.UserCreateRequest;
import com.ryuneng.goldauth.domain.user.dto.UserCreateResponse;
import com.ryuneng.goldauth.domain.user.service.UserService;
import com.ryuneng.goldauth.global.exception.response.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    @PostMapping
    public ResponseEntity<SuccessResponse<UserCreateResponse>> signup(@Valid @RequestBody UserCreateRequest request) {

        SuccessResponse<UserCreateResponse> response = SuccessResponse.ok("회원가입이 완료되었습니다.", userService.signup(request));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequest request) {

        Map<String, String> tokens = userService.login(request);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokens.get("accessToken"));

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(tokens);
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        String email = tokenProvider.getEmailFromToken(refreshToken);

        // Refresh Token 유효성 검증
        if (tokenProvider.validateRefreshToken(email, refreshToken)) {
            String newAccessToken = tokenProvider.createToken(email);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + newAccessToken);

            return ResponseEntity.status(HttpStatus.OK).headers(headers).body("새로운 Access Token이 발급되었습니다.");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token이 유효하지 않습니다.");
    }
}
