package com.example.solace.Repository;

import com.example.solace.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    ProductEntity findByProductId(Integer productId);
}
