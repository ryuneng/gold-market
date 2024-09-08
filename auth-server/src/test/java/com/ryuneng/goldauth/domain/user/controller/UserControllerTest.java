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

    // 유저 Request DTO 빌더 생성
    private UserCreateRequest createUserCreateRequestBuilder(String username, String password, String phoneNumber) {

        return UserCreateRequest.builder()
                .username(username)
                .password(password)
                .phoneNumber(phoneNumber)
                .build();
    }

}
