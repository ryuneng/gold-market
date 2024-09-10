package com.ryuneng.goldauth.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryuneng.goldauth.domain.jwt.TokenProvider;
import com.ryuneng.goldauth.domain.user.dto.UserCreateRequest;
import com.ryuneng.goldauth.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false) // Spring Security 필터 비활성화
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private TokenProvider tokenProvider;

    @DisplayName("신규 유저를 등록(회원가입)한다.")
    @Test
    void signup() throws Exception {
        // given
        UserCreateRequest request = createUserCreateRequestBuilder("test@test.com", "test1234", "테스트유저", "01012345678");

        // when // then
        mockMvc.perform(
                        post("/api/users")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("회원가입이 완료되었습니다."));
    }

    @DisplayName("형식에 맞지 않는 이메일로 회원가입을 시도한다.")
    @Test
    void signupWithWrongEmail() throws Exception {
        // given
        UserCreateRequest request = createUserCreateRequestBuilder("test@", "test1234", "테스트유저", "01012345678");

        // when // then
        mockMvc.perform(
                        post("/api/users")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("아이디는 이메일 형식으로 입력해주세요."));
    }

    @DisplayName("유효 범위를 벗어난 길이의 비밀번호로 회원가입을 시도한다.")
    @Test
    void signupWithWrongLengthPassword() throws Exception {
        // given
        UserCreateRequest request = createUserCreateRequestBuilder("test@test.com", "test1", "테스트유저", "01012345678");

        // when // then
        mockMvc.perform(
                        post("/api/users")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("비밀번호는 8자에서 16자 사이여야 합니다."));
    }

    @DisplayName("형식에 맞지 않는 비밀번호로 회원가입을 시도한다.")
    @Test
    void signupWithWrongPassword() throws Exception {
        // given
        UserCreateRequest request = createUserCreateRequestBuilder("test@test.com", "testtest", "테스트유저", "01012345678");

        // when // then
        mockMvc.perform(
                        post("/api/users")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("비밀번호는 영문 대/소문자, 숫자, 특수문자 중 2가지 이상을 포함해야 합니다."));
    }

    @DisplayName("유효 범위를 벗어난 길이의 닉네임으로 회원가입을 시도한다.")
    @Test
    void signupWithWrongLengthNickname() throws Exception {
        // given
        UserCreateRequest request = createUserCreateRequestBuilder("test@test.com", "test1234", "김", "01012345678");

        // when // then
        mockMvc.perform(
                        post("/api/users")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("닉네임은 2자에서 10자 사이여야 합니다."));
    }

    @DisplayName("형식에 맞지 않는 전화번호로 회원가입을 시도한다.")
    @Test
    void signupWithWrongPhoneNumber() throws Exception {
        // given
        UserCreateRequest request = createUserCreateRequestBuilder("test@test.com", "test1234", "테스트유저", "010-1234-5678");

        // when // then
        mockMvc.perform(
                        post("/api/users")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("전화번호는 하이픈(-)을 제외한 숫자만 입력해주세요. (예: 01012345678)"));
    }

    // 유저 Request DTO 빌더 생성
    private UserCreateRequest createUserCreateRequestBuilder(String email, String password, String nickname, String phoneNumber) {

        return UserCreateRequest.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .build();
    }

}
