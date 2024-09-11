package com.ryuneng.goldresource.domain.order.service;

import com.ryuneng.goldresource.domain.auth.UserResponse;
import com.ryuneng.goldresource.domain.order.dto.*;
import com.ryuneng.goldresource.domain.order.entity.Order;
import com.ryuneng.goldresource.domain.order.enums.Status;
import com.ryuneng.goldresource.domain.order.repository.OrderRepository;
import com.ryuneng.goldresource.domain.product.entity.Product;
import com.ryuneng.goldresource.domain.product.enums.Type;
import com.ryuneng.goldresource.domain.product.repository.ProductRepository;
import com.ryuneng.goldresource.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.ryuneng.goldresource.global.exception.ErrorCode.ORDER_NOT_FOUND;
import static com.ryuneng.goldresource.global.exception.ErrorCode.PRODUCT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    /**
     * 신규 주문 생성
     *
     * @param user 검증된 사용자 정보
     * @param request 생성할 주문 정보
     * @return 생성된 신규 주문 정보
     */
    public OrderResponse createOrder(UserResponse user, OrderCreateRequest request) {

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
        return OrderResponse.builder()
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

    /**
     * 주문 목록 조회
     *
     * @param user 검증된 사용자 정보
     * @param request 페이지 요청 정보
     * @return 페이징 처리된 주문 목록
     */
    public Page<OrderListResponse> getOrders(UserResponse user, OrderListRequest request) {

        // 시작일과 종료일을 LocalDateTime으로 변환
        LocalDateTime startDateTime = request.getStartDate().atStartOfDay();
        LocalDateTime endDateTime = request.getEndDate().atTime(23, 59, 59);

        Pageable pageable = PageRequest.of(
                request.getOffset() / request.getLimit(), // 페이지 번호 계산
                request.getLimit() // 페이지 크기
        );

        Page<Order> orders;

        // 상품 유형(구매/판매)에 따라 조회
        if (request.getProductType() != null && !request.getProductType().isEmpty()) {
            orders = orderRepository.findByProductTypePageable(
                    user.email(),
                    Type.valueOf(request.getProductType()), // 문자열을 Enum으로 변환
                    startDateTime,
                    endDateTime,
                    pageable
            );
        } else {
            orders = orderRepository.findByPageable(
                    user.email(),
                    startDateTime,
                    endDateTime,
                    pageable
            );
        }

        // 주문 목록을 응답 DTO로 변환하여 반환
        return orders.map(order -> OrderListResponse.builder()
                .orderNumber(order.getOrderNumber())
                .userEmail(order.getUserEmail())
                .productName(order.getProduct().getName().getDescription())
                .productType(order.getProduct().getType().getDescription())
                .productPrice(order.getProduct().getPrice())
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus().getDescription())
                .createdAt(order.getCreatedAt())
                .build());
    }

    /**
     * 주문 상세 조회
     *
     * @param user 검증된 사용자 정보
     * @param orderId 주문 ID
     * @return 주문 상세 정보
     */
    public OrderResponse getOrderDetail(UserResponse user, Long orderId) {

        Order order = orderRepository.findByIdAndUserEmail(user.email(), orderId);

        if (order == null) {
            throw new CustomException(ORDER_NOT_FOUND);
        }

        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .userEmail(order.getUserEmail())
                .productId(order.getProduct().getId())
                .productName(order.getProduct().getName().getDescription())
                .productType(order.getProduct().getType().getDescription())
                .productPrice(order.getProduct().getPrice())
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus().getDescription())
                .deliveryAddress(order.getDeliveryAddress())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
