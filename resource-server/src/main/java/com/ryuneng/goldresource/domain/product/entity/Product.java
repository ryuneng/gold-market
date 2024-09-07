package com.ryuneng.goldresource.domain.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import com.ryuneng.goldresource.domain.product.enums.Name;
import com.ryuneng.goldresource.domain.product.enums.Type;
import com.ryuneng.goldresource.global.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("상품 ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("상품명")
    private Name name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("상품 타입 (구매/판매)")
    private Type type;

    @Column(nullable = false)
    @Comment("상품 가격")
    private int price;
}
