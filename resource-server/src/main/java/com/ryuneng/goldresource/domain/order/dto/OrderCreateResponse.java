package com.ryuneng.goldresource.domain.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "주문 생성 응답 DTO")
public class OrderCreateResponse {

    @Schema(description = "주문 ID", example = "100000")
    private Long id;

    @Schema(description = "주문번호", example = "PURCHASE-20240909-100000")
    private String orderNumber;

    @Schema(description = "주문자 이메일", example = "user1@test.com")
    private String userEmail;

    @Schema(description = "주문 상품 ID", example = "1")
    private Long productId;

    @Schema(description = "주문 상품명", example = "99.9% 금")
    private String productName;

    @Schema(description = "주문 상품 타입", example = "구매")
    private String productType;

    @Schema(description = "주문 상품 가격", example = "159229")
    private int productPrice;

    @Schema(description = "주문 수량", example = "3.75")
    private BigDecimal quantity;

    @Schema(description = "총 주문 금액", example = "597108")
    private int totalPrice;

    @Schema(description = "주문 상태", example = "ORDER_PLACED")
    private String status;

    @Schema(description = "배송지", example = "서울 서초구 방배로10길 10-20")
    private String deliveryAddress;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "주문일자", example = "2024-09-09 12:00:00")
    private LocalDateTime createdAt;
}
