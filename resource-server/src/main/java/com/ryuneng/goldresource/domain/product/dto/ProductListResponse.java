package com.ryuneng.goldresource.domain.product.dto;

import com.ryuneng.goldresource.domain.product.enums.Name;
import com.ryuneng.goldresource.domain.product.enums.Type;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "상품 목록 DTO")
public class ProductListResponse {

    @Schema(description = "상품 ID", example = "1")
    private Long id;

    @Schema(description = "상품명", example = "GOLD_99_9")
    private Name name;

    @Schema(description = "상품 타입 (구매/판매)", example = "PURCHASE")
    private Type type;

    @Schema(description = "상품 가격", example = "159229")
    private int price;
}
