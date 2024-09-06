package wantedgold.resourceserver.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wantedgold.resourceserver.domain.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
