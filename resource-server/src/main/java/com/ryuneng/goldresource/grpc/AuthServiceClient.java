package com.ryuneng.goldresource.grpc;

import com.ryuneng.goldmarket.grpc.AuthRequest;
import com.ryuneng.goldmarket.grpc.AuthResponse;
import com.ryuneng.goldmarket.grpc.AuthServiceGrpc;
import com.ryuneng.goldresource.domain.auth.UserResponse;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceClient {

    // gRPC 클라이언트 스텁을 사용하여 인증 서버와 통신하는 서비스
    @GrpcClient("auth")
    private AuthServiceGrpc.AuthServiceBlockingStub authServiceBlockingStub;

    /**
     * 주어진 Access Token을 사용하여 사용자 인증을 요청합니다.
     *
     * @param accessToken 사용자 인증에 사용할 JWT Access Token
     * @return 인증 결과와 사용자 이메일을 포함하는 UserResponse 객체
     */
    public UserResponse authenticateUser(String accessToken) {

        try {
            // 인증 서버에 gRPC 요청을 보내 사용자 인증 수행
            AuthResponse response = authServiceBlockingStub.authenticateUser(
                    AuthRequest.newBuilder()
                            .setAccessToken(accessToken)
                            .build());

            // 응답 결과를 UserResponse 객체로 변환하여 반환
            return new UserResponse(response.getSuccess(), response.getEmail());
        } catch (StatusRuntimeException e) {
            // gRPC 호출 실패 시 오류 메시지 기록
            log.info("gRPC 호출 실패 : {}", e.getStatus().getCode().name());
            // 인증 실패 응답 반환
            return new UserResponse(false, "Unknown");
        }
    }
}
