package com.example.ecommerce.ecommerceapplication.repository;

import com.example.ecommerce.ecommerceapplication.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Optional<Product> findByUuid(String uuid);
}
