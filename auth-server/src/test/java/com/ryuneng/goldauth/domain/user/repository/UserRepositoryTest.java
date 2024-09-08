package com.ryuneng.goldauth.domain.user.repository;

import com.ryuneng.goldauth.domain.user.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.ryuneng.goldauth.domain.user.enums.Role.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAllInBatch();
    }

    @DisplayName("유저 아이디로 유저를 조회한다.")
    @Test
    void findUserByUsername() {
        // given
        User user = userRepository.save(createUserBuilder());

        // when
        userRepository.findByUsername(user.getUsername());

        // then
        assertThat(user)
                .extracting("id", "username", "password", "phoneNumber", "role")
                .contains(1L, "username", "user1234", "01012345678", USER);
    }

    @DisplayName("존재하지 않는 유저 아이디로 유저를 조회한다.")
    @Test
    void findUserByWithNonExistentUsername() {
        // given
        String username = "username";

        // when // then
        assertThat(userRepository.findByUsername(username)).isEmpty();
    }

    // 유저 빌더 생성
    private static User createUserBuilder() {

        return User.builder()
                .id(1L)
                .username("username")
                .password("user1234")
                .phoneNumber("01012345678")
                .role(USER)
                .build();
    }

}
