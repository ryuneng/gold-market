package com.ryuneng.goldresource.domain.product.controller;

import com.ryuneng.goldresource.domain.product.dto.ProductListResponse;
import com.ryuneng.goldresource.domain.product.service.ProductService;
import com.ryuneng.goldresource.global.exception.CustomException;
import com.ryuneng.goldresource.global.exception.ErrorCode;
import com.ryuneng.goldresource.global.exception.response.SuccessResponse;
import com.ryuneng.goldresource.grpc.AuthServiceClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product", description = "상품 API")
public class ProductController {

    private final AuthServiceClient authServiceClient;
    private final ProductService productService;

    @Operation(summary = "상품 목록 조회", description = "인증 서버를 통해 사용자 검증 후 상품 목록을 조회합니다.")
    @GetMapping
    public SuccessResponse<List<ProductListResponse>> getProducts(@RequestHeader("Authorization") String accessToken) {

        // 검증되지 않은 사용자 예외처리
        if (!authServiceClient.authenticateUser(accessToken).success()) {
            throw new CustomException(ErrorCode.UNKNOWN_USER);
        }

        return SuccessResponse.ok("상품 목록 조회 성공", productService.getProducts());
    }
}
