package com.ryuneng.goldresource.domain.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "주문 생성 요청 DTO")
public class OrderCreateRequest {

    @NotNull(message = "주문 상품 ID는 필수 입력값입니다.")
    @Schema(description = "주문 상품 ID", example = "1")
    private Long productId;

    @NotBlank(message = "주문 수량을 입력해주세요.")
    @Schema(description = "주문 수량", example = "3.75")
    private BigDecimal quantity;

    @NotBlank(message = "배송지를 입력해주세요.")
    @Schema(description = "배송지", example = "서울 서초구 방배로10길 10-20")
    private String deliveryAddress;
}
