package com.example.ecommerce.ecommerceapplication.repository;

import com.example.ecommerce.ecommerceapplication.model.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriceHistoryRepository extends JpaRepository<com.example.ecommerce.ecommerceapplication.model.PriceHistory, Long> {
    Optional<PriceHistory> findByOrderUuidAndProductUuid(String orderUuid, String productUuid);

}
