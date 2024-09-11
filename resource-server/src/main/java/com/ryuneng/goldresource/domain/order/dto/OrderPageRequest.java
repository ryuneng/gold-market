package com.ryuneng.goldresource.domain.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "주문 목록 요청 DTO")
public class OrderPageRequest {

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "조회 시작일", example = "2024-09-01")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "조회 종료일", example = "2024-09-11")
    private LocalDate endDate;

    @Schema(description = "한 페이지당 출력할 데이터의 개수", example = "10")
    private int limit = 10;

    @Schema(description = "결과 집합에서 특정 페이지의 데이터를 가져오기 위해 건너뛸 레코드 수", example = "0")
    private int offset = 0;

    @Schema(description = "상품 유형으로 조회 (PURCHASE / SALE)", example = "PURCHASE")
    private String productType;
}
