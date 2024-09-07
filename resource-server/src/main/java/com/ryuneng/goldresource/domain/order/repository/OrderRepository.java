package com.ryuneng.goldresource.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ryuneng.goldresource.domain.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
