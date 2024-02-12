package app.practice.cafeapitask.product.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByOwnerId(Long ownerId, Pageable pageable);

    List<Product> findAllByOwnerId(Long ownerId);

    List<Product> findAllLikeName(String name, Pageable pageable);
}
