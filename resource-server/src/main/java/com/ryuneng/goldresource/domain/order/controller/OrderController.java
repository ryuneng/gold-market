package com.ryuneng.goldresource.domain.order.controller;

import com.ryuneng.goldresource.domain.auth.UserResponse;
import com.ryuneng.goldresource.domain.order.dto.OrderCreateRequest;
import com.ryuneng.goldresource.domain.order.dto.OrderResponse;
import com.ryuneng.goldresource.domain.order.dto.OrderListResponse;
import com.ryuneng.goldresource.domain.order.dto.OrderListRequest;
import com.ryuneng.goldresource.domain.order.service.OrderService;
import com.ryuneng.goldresource.global.exception.CustomException;
import com.ryuneng.goldresource.global.exception.response.SuccessResponse;
import com.ryuneng.goldresource.grpc.AuthServiceClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ryuneng.goldresource.global.exception.ErrorCode.UNKNOWN_USER;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order", description = "주문 API")
public class OrderController {

    private final AuthServiceClient authServiceClient;
    private final OrderService orderService;

    @Operation(summary = "주문 생성", description = "인증 서버를 통해 사용자 검증 후 신규 주문을 생성합니다.")
    @PostMapping
    public ResponseEntity<SuccessResponse<OrderResponse>> createOrder(
                                                            @RequestHeader("Authorization") String accessToken,
                                                            @Valid @RequestBody OrderCreateRequest request) {

        UserResponse user = validateUser(accessToken); // 사용자 검증

        SuccessResponse<OrderResponse> response = SuccessResponse.ok(
                "주문이 완료되었습니다.", orderService.createOrder(user, request));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "주문 목록 조회", description = "인증 서버를 통해 사용자 검증 후 주문 목록을 조회합니다.")
    @GetMapping
    public SuccessResponse<Page<OrderListResponse>> getOrders(@RequestHeader("Authorization") String accessToken,
                                                              @RequestBody OrderListRequest request) {

        UserResponse user = validateUser(accessToken); // 사용자 검증

        return SuccessResponse.ok("주문 목록 조회 성공", orderService.getOrders(user, request));
    }

    // 주어진 액세스 토큰을 통해 사용자 인증을 수행하는 메서드
    private UserResponse validateUser(String accessToken) {

        UserResponse user = authServiceClient.authenticateUser(accessToken);
        if (!user.success()) {
            throw new CustomException(UNKNOWN_USER);
        }
        return user;
    }
}
