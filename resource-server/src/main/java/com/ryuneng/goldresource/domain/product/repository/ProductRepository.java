package com.ryuneng.goldresource.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ryuneng.goldresource.domain.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
