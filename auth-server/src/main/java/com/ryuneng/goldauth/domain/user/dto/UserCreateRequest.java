package com.ryuneng.goldauth.domain.user.dto;

import com.ryuneng.goldauth.domain.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.ryuneng.goldauth.domain.user.entity.Role.USER;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "아이디는 이메일 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 16, message = "비밀번호는 8자에서 16자 사이여야 합니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d|.*\\W)([a-zA-Z\\d\\W]+)$",
            message = "비밀번호는 영문 대/소문자, 숫자, 특수문자 중 2가지 이상을 포함해야 합니다.")
    private String password;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 2, max = 10, message = "닉네임은 2자에서 10자 사이여야 합니다.")
    private String nickname;

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "^01[0-9]\\d{3,4}\\d{4}$", message = "전화번호는 하이픈(-)을 제외한 숫자만 입력해주세요. (예: 01012345678)")
    private String phoneNumber;

    // UserCreateRequest 값을 받아 유저 객체 생성
    public User createUser(String password) {
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .roleList(List.of(USER)) // 회원가입 시 기본 권한은 USER
                .build();
    }

}
