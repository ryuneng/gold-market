package com.ryuneng.goldauth.grpc;

import com.ryuneng.goldauth.domain.jwt.TokenProvider;
import com.ryuneng.goldauth.domain.jwt.UserDetailsServiceImpl;
import com.ryuneng.goldmarket.grpc.AuthRequest;
import com.ryuneng.goldmarket.grpc.AuthResponse;
import com.ryuneng.goldmarket.grpc.AuthServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.security.core.userdetails.UserDetails;

// gRPC 통신을 통해 자원 서버의 요청을 처리하는 서비스
@Slf4j
@GrpcService
@RequiredArgsConstructor
public class AuthServiceImpl extends AuthServiceGrpc.AuthServiceImplBase { // gRPC 통신을 위한 인터페이스는 proto 파일을 통해 자동 생성됨

    private final TokenProvider tokenProvider;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public void authenticateUser(AuthRequest request, StreamObserver<AuthResponse> responseObserver) {

        String accessToken = request.getAccessToken();

        // AccessToken 유효성 검사
        if (tokenProvider.validateToken(accessToken)) {
            // 토큰에서 사용자 이메일 추출
            String email = tokenProvider.getEmailFromToken(accessToken);
            // 사용자 정보 로드
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // 인증 성공 응답 생성
            AuthResponse response = AuthResponse.newBuilder()
                    .setSuccess(true)
                    .setEmail(userDetails.getUsername()) // 이메일 반환
                    .build();

            log.info("자원 서버로 전송할 gRPC 통신 성공 응답 success: {}, email: {}", response.getSuccess(), response.getEmail());

            // 응답 전송
            responseObserver.onNext(response);
        } else {
            // 토큰이 유효하지 않을 경우 실패 응답 생성
            AuthResponse response = AuthResponse.newBuilder()
                    .setSuccess(false)
                    .build();

            responseObserver.onNext(response);
        }

        // 스트림 전송 완료
        responseObserver.onCompleted();
    }
}
