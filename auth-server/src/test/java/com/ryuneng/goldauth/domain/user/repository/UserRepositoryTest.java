package com.ryuneng.goldauth.domain.user.repository;

import com.ryuneng.goldauth.domain.user.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.ryuneng.goldauth.domain.user.entity.Role.USER;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAllInBatch();
    }

    @DisplayName("이메일로 유저를 조회한다.")
    @Test
    void findUserByEmail() {
        // given
        User user = userRepository.save(createUserBuilder());

        // when
        userRepository.findByEmail(user.getEmail());

        // then
        assertThat(user)
                .extracting("id", "email", "password", "nickname", "phoneNumber")
                .contains(1L, "test@test.com", "user1234", "테스트유저", "01012345678");
    }

    @DisplayName("존재하지 않는 이메일로 유저를 조회한다.")
    @Test
    void findUserByWithNonExistentEmail() {
        // given
        String email = "test@test.com";

        // when // then
        assertThat(userRepository.findByEmail(email)).isEmpty();
    }

    // 유저 빌더 생성
    private static User createUserBuilder() {

        return User.builder()
                .id(1L)
                .email("test@test.com")
                .password("user1234")
                .nickname("테스트유저")
                .phoneNumber("01012345678")
                .roleList(List.of(USER))
                .build();
    }

}
