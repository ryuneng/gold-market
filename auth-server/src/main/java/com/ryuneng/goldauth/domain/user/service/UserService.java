package com.ryuneng.goldauth.domain.user.service;

import com.ryuneng.goldauth.domain.jwt.TokenProvider;
import com.ryuneng.goldauth.domain.user.dto.LoginRequest;
import com.ryuneng.goldauth.domain.user.dto.UserCreateRequest;
import com.ryuneng.goldauth.domain.user.dto.UserCreateResponse;
import com.ryuneng.goldauth.domain.user.entity.User;
import com.ryuneng.goldauth.domain.user.repository.UserRepository;
import com.ryuneng.goldauth.global.exception.CustomException;
import com.ryuneng.goldauth.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static com.ryuneng.goldauth.global.exception.ErrorCode.EMAIL_DUPLICATION;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    /**
     * 유저 회원가입
     * 
     * @param request 가입할 유저 정보가 포함된 UserCreateRequest 객체
     * @return 가입 완료된 유저 정보가 포함된 UserCreateResponse 객체
     */
    @Transactional
    public UserCreateResponse signup(UserCreateRequest request) {

        // 유저 이메일 중복 체크
        emailDuplicationCheck(request.getEmail());

        return new UserCreateResponse(userRepository.save(
                request.createUser(passwordEncoder.encode(request.getPassword())) // 패스워드 암호화 및 유저 객체 생성
        ));
    }

    /**
     * 유저 로그인
     *
     * @param request 로그인할 유저 정보가 포함된 LoginRequest 객체
     * @return 로그인 후 생성된 JWT 토큰 문자열
     */
    @Transactional
    public Map<String, String> login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        // JWT Access Token 및 Refresh Token 생성
        String accessToken = tokenProvider.createToken(user.getEmail());
        String refreshToken = tokenProvider.createRefreshToken(user.getEmail());

        // 토큰을 Map으로 반환
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
    }

    /**
     * 유저 이메일 중복 체크
     * 
     * @param email 유저 이메일
     */
    private void emailDuplicationCheck(String email) {

        if (userRepository.findByEmail(email).isPresent()) {
            throw new CustomException(EMAIL_DUPLICATION);
        }
    }
}
