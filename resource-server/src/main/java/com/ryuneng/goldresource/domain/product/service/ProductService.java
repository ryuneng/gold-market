package com.ryuneng.goldresource.domain.product.service;

import com.ryuneng.goldresource.domain.product.dto.ProductListResponse;
import com.ryuneng.goldresource.domain.product.entity.Product;
import com.ryuneng.goldresource.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * 상품 목록 조회
     *
     * @return 모든 상품 목록
     */
    @Transactional(readOnly = true)
    public List<ProductListResponse> getProducts() {

        List<Product> products = productRepository.findAll();

        // 데이터베이스에서 조회한 상품 목록을 ProductListResponse 형태로 변환 후 반환
        return getProductListResponses(products);
    }

    /**
     * 상품 목록을 ProductListResponse 리스트로 변환하는 메서드
     * 
     * @param products 변환할 Product 객체 리스트
     * @return 변환된 ProductListResponse 리스트
     */
    private static List<ProductListResponse> getProductListResponses(List<Product> products) {

        return products.stream()
                .map(product -> new ProductListResponse(
                        product.getId(), product.getName(), product.getType(), product.getPrice()))
                .collect(Collectors.toList());
    }
}
