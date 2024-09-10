package com.ryuneng.goldresource.domain.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import com.ryuneng.goldresource.domain.order.enums.Status;
import com.ryuneng.goldresource.domain.product.entity.Product;
import com.ryuneng.goldresource.global.entity.BaseEntity;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("주문 ID")
    private Long id;

    @Column(nullable = false)
    @Comment("주문번호")
    private String orderNumber;

    @Column(nullable = false)
    @Comment("주문자 ID")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @Comment("주문상품")
    private Product product;

    @Column(nullable = false)
    @Comment("수량")
    private BigDecimal quantity;

    @Column(nullable = false)
    @Comment("총 금액")
    private int totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("주문상태")
    private Status status;

    @Comment("배송지")
    private String deliveryAddress;

}
