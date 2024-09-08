package com.ryuneng.goldauth.domain.user.controller;

import com.ryuneng.goldauth.domain.user.dto.UserCreateRequest;
import com.ryuneng.goldauth.domain.user.dto.UserCreateResponse;
import com.ryuneng.goldauth.domain.user.service.UserService;
import com.ryuneng.goldauth.global.exception.response.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping()
    public SuccessResponse<UserCreateResponse> signup(@Valid @RequestBody UserCreateRequest request) {

        return SuccessResponse.created("회원가입이 완료되었습니다.", userService.signup(request));
    }

}
