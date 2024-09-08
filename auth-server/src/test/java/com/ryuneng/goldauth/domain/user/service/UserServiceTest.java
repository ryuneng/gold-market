package com.ryuneng.goldauth.domain.user.service;

import com.ryuneng.goldauth.domain.user.dto.UserCreateRequest;
import com.ryuneng.goldauth.domain.user.dto.UserCreateResponse;
import com.ryuneng.goldauth.domain.user.repository.UserRepository;
import com.ryuneng.goldauth.global.exception.CustomException;
import com.ryuneng.goldauth.global.exception.ErrorCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @AfterEach
    void tearDown() {
        userRepository.deleteAllInBatch();
    }

    @DisplayName("신규 유저를 등록(회원가입)한다.")
    @Test
    void signup() {
        // given
        UserCreateRequest request = createUserCreateRequestBuilder();

        // when
        UserCreateResponse response = userService.signup(request);

        // then
        assertThat(response)
                .extracting("username")
                .isEqualTo(response.getUsername());
    }

    @DisplayName("이미 존재하는 아이디로 회원가입을 시도한다.")
    @Test
    void signupWithDuplicationUsername() {
        // given
        UserCreateRequest request = createUserCreateRequestBuilder();

        // when
        userService.signup(request);

        // then
        assertThatThrownBy(() -> userService.signup(request))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.USERNAME_DUPLICATION.getMessage());
    }

    // 유저 Request DTO 빌더 생성
    private UserCreateRequest createUserCreateRequestBuilder() {

        return UserCreateRequest.builder()
                .username("user1")
                .password(passwordEncoder.encode("test1234"))
                .phoneNumber("01012345678")
                .build();
    }

}
