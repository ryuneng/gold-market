package com.ryuneng.goldauth.domain.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class TokenProvider { // JWT 토큰 생성 및 검증 클래스

    @Value("${JWT_SECRET_KEY}")
    private String jwtSecretKey;

    @Value("${JWT_EXPIRATION}")
    private long jwtExpiration;        // 1시간

    @Value("${JWT_REFRESH_EXPIRATION}")
    private long jwtRefreshExpiration; // 7일

    private final RedisTemplate<String, String> redisTemplate;

    // JWT Access Token 생성
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

    // Access Token 유효성 검사
    public boolean validateToken(String token) {

        try {
            JWT.require(generateAlgorithm(jwtSecretKey)).build().verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Refresh Token 생성 및 Redis 저장
    public String createRefreshToken(String email) {

        String refreshToken = JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtRefreshExpiration))
                .sign(generateAlgorithm(jwtSecretKey));

        // Redis에 저장 (key: email, value: refreshToken)
        redisTemplate.opsForValue().set(email, refreshToken, jwtRefreshExpiration, TimeUnit.MILLISECONDS);

        return refreshToken;
    }

    // Refresh Token 유효성 검사
    public boolean validateRefreshToken(String email, String refreshToken) {

        String storedRefreshToken = redisTemplate.opsForValue().get(email);
        return storedRefreshToken != null && storedRefreshToken.equals(refreshToken);
    }

    // HMAC256 형식의 알고리즘 생성
    private static Algorithm generateAlgorithm(String jwtSecretKey) {

        return Algorithm.HMAC256(jwtSecretKey.getBytes());
    }
}
