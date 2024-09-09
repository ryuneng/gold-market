package com.ryuneng.goldauth.domain.user.controller;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<SuccessResponse<UserCreateResponse>> signup(@Valid @RequestBody UserCreateRequest request) {

        SuccessResponse<UserCreateResponse> response = SuccessResponse.ok("회원가입이 완료되었습니다.", userService.signup(request));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        String token = userService.login(request);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body("로그인이 완료되었습니다.");
    }
}
