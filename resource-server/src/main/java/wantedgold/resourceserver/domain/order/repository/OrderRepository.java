package wantedgold.resourceserver.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wantedgold.resourceserver.domain.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
