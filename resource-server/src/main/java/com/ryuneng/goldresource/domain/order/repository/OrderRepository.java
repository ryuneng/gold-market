package com.ryuneng.goldresource.domain.order.repository;

import com.ryuneng.goldresource.domain.product.enums.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ryuneng.goldresource.domain.order.entity.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 상품 타입과 날짜 범위로 조회
    @Query("SELECT o FROM Order o WHERE o.product.type = :productType AND o.userEmail = :userEmail AND o.createdAt BETWEEN :startDate AND :endDate ORDER BY o.id DESC")
    Page<Order> findByProductTypePageable(
            @Param("userEmail") String userEmail,
            @Param("productType") Type productType,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    // 날짜 범위만으로 조회
    @Query("SELECT o FROM Order o WHERE o.userEmail = :userEmail AND o.createdAt BETWEEN :startDate AND :endDate ORDER BY o.id DESC")
    Page<Order> findByPageable(
            @Param("userEmail") String userEmail,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    // 주문 상세 조회
    @Query("SELECT o FROM Order o WHERE o.userEmail = :userEmail AND o.id = :orderId")
    Order findByIdAndUserEmail(String userEmail, Long orderId);
}
