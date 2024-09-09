package com.ryuneng.goldauth.domain.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenProvider { // JWT 토큰 생성 및 검증 클래스

    @Value("${JWT_SECRET_KEY}")
    private String jwtSecretKey;

    @Value("${JWT_EXPIRATION}")
    private long jwtExpiration; // 1시간

    // JWT 토큰 생성
    public String createToken(String email) {

        return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpiration)) // 만료 시간
                .sign(generateAlgorithm(jwtSecretKey));
    }

    // JWT 토큰에서 이메일 추출
    public String getEmailFromToken(String token) {

        return JWT.require(generateAlgorithm(jwtSecretKey))
                .build()
                .verify(token)
                .getSubject();
    }

    // JWT 토큰 유효성 검증
    public boolean validateToken(String token) {

        try {
            JWT.require(generateAlgorithm(jwtSecretKey)).build().verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // HMAC256 형식의 알고리즘 생성
    private static Algorithm generateAlgorithm(String jwtSecretKey) {

        return Algorithm.HMAC256(jwtSecretKey.getBytes());
    }
}
