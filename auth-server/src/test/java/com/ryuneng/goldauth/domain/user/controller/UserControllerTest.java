package com.ryuneng.goldauth.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.junit.jupiter.api.Assertions.*;
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

    @DisplayName("신규 유저를 등록(회원가입)한다.")
    @Test
    void signup() throws Exception {
        // given
        UserCreateRequest request = createUserCreateRequestBuilder("user1", "test1234", "01012345678");

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

    @DisplayName("유효 범위를 벗어난 길이의 아이디로 회원가입을 시도한다.")
    @Test
    void signupWithWrongLengthUsername() throws Exception {
        // given
        UserCreateRequest request = createUserCreateRequestBuilder("u", "test1234", "01012345678");

        // when // then
        mockMvc.perform(
                        post("/api/users")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("아이디는 5자에서 20자 사이여야 합니다."));
    }

    @DisplayName("형식에 맞지 않는 아이디로 회원가입을 시도한다.")
    @Test
    void signupWithWrongUsername() throws Exception {
        // given
        UserCreateRequest request = createUserCreateRequestBuilder("User!", "test1234", "01012345678");

        // when // then
        mockMvc.perform(
                        post("/api/users")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("아이디는 영문 소문자, 숫자, 특수기호(-, _)만 사용 가능합니다."));
    }

    @DisplayName("유효 범위를 벗어난 길이의 비밀번호로 회원가입을 시도한다.")
    @Test
    void signupWithWrongLengthPassword() throws Exception {
        // given
        UserCreateRequest request = createUserCreateRequestBuilder("user1", "test", "01012345678");

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
        UserCreateRequest request = createUserCreateRequestBuilder("user1", "testtest", "01012345678");

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

    @DisplayName("형식에 맞지 않는 전화번호로 회원가입을 시도한다.")
    @Test
    void signupWithWrongPhoneNumber() throws Exception {
        // given
        UserCreateRequest request = createUserCreateRequestBuilder("user1", "test1234", "010-1234-5678");

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
    private UserCreateRequest createUserCreateRequestBuilder(String username, String password, String phoneNumber) {

        return UserCreateRequest.builder()
                .username(username)
                .password(password)
                .phoneNumber(phoneNumber)
                .build();
    }

}
