package com.ryuneng.goldauth.domain.user.dto;

import com.ryuneng.goldauth.domain.user.entity.User;
import com.ryuneng.goldauth.domain.user.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {

    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 5, max = 20, message = "아이디는 5자에서 20자 사이여야 합니다.")
    @Pattern(regexp = "^[a-z0-9-_]+$", message = "아이디는 영문 소문자, 숫자, 특수기호(-, _)만 사용 가능합니다.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 16, message = "비밀번호는 8자에서 16자 사이여야 합니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d|.*\\W)([a-zA-Z\\d\\W]+)$",
            message = "비밀번호는 영문 대/소문자, 숫자, 특수문자 중 2가지 이상을 포함해야 합니다.")
    private String password;

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "^01[0-9]\\d{3,4}\\d{4}$", message = "하이픈(-)을 제외한 숫자만 입력해주세요. (예: 01012345678)")
    @Size()
    private String phoneNumber;

    // UserCreateRequest 값을 받아 유저 객체 생성
    public User createUser(String password) {
        return User.builder()
                .username(username)
                .password(password)
                .phoneNumber(phoneNumber)
                .role(Role.USER) // 회원가입 시 기본 권한은 USER
                .build();
    }

}
