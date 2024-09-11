package com.ryuneng.goldresource.domain.order.service;

import com.ryuneng.goldresource.domain.auth.UserResponse;
import com.ryuneng.goldresource.domain.order.dto.OrderCreateRequest;
import com.ryuneng.goldresource.domain.order.dto.OrderCreateResponse;
import com.ryuneng.goldresource.domain.order.entity.Order;
import com.ryuneng.goldresource.domain.order.enums.Status;
import com.ryuneng.goldresource.domain.order.repository.OrderRepository;
import com.ryuneng.goldresource.domain.product.entity.Product;
import com.ryuneng.goldresource.domain.product.repository.ProductRepository;
import com.ryuneng.goldresource.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.ryuneng.goldresource.global.exception.ErrorCode.PRODUCT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    /**
     * 신규 주문 생성
     *
     * @param user 검증된 사용자 정보가 포함된 UserResponse 객체
     * @param request 생성할 주문 정보가 포함된 OrderCreateRequest 객체
     * @return 생성된 신규 주문 정보가 포함된 OrderCreateResponse 객체
     */
    public OrderCreateResponse createOrder(UserResponse user, OrderCreateRequest request) {

        // 주문할 상품 조회
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new CustomException(PRODUCT_NOT_FOUND));

        // 주문번호에 포함될 날짜를 'yyyyMMdd' 형식으로 포맷팅
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = now.format(formatter);

        // 임시 주문번호 생성 (형식 : PURCHASE-20240911)
        StringBuilder orderNumberBuilder = new StringBuilder();
        orderNumberBuilder.append(product.getType().toString())
                .append("-")
                .append(formattedDate);

        // 수량과 상품 가격을 곱하여 총 가격 계산
        BigDecimal priceBigDecimal = new BigDecimal(product.getPrice());
        int totalPrice = request.getQuantity().multiply(priceBigDecimal).intValue();

        // 주문 객체 생성
        Order order = Order.builder()
                .orderNumber(orderNumberBuilder.toString())
                .userEmail(user.email())
                .product(product)
                .quantity(request.getQuantity())
                .totalPrice(totalPrice)
                .status(Status.ORDER_PLACED)
                .deliveryAddress(request.getDeliveryAddress())
                .build();

        // 주문 저장
        Order savedOrder = orderRepository.save(order);

        // 저장된 주문 객체의 ID를 사용하여 주문번호 최종 업데이트 (형식 : PURCHASE-20240911-100004)
        savedOrder.updateOrderNumber(orderNumberBuilder.append("-").append(savedOrder.getId()).toString());
        orderRepository.save(savedOrder);

        // 주문 생성 결과를 담아 응답 객체 반환
        return OrderCreateResponse.builder()
                .id(savedOrder.getId())
                .orderNumber(savedOrder.getOrderNumber())
                .userEmail(savedOrder.getUserEmail())
                .productId(savedOrder.getProduct().getId())
                .productName(savedOrder.getProduct().getName().getDescription())
                .productType(savedOrder.getProduct().getType().getDescription())
                .productPrice(savedOrder.getProduct().getPrice())
                .quantity(savedOrder.getQuantity())
                .totalPrice(savedOrder.getTotalPrice())
                .status(savedOrder.getStatus().getDescription())
                .deliveryAddress(savedOrder.getDeliveryAddress())
                .createdAt(savedOrder.getCreatedAt())
                .build();
    }
}
