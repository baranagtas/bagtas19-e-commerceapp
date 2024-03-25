package com.example.ecommerce.ecommerceapplication.repository;

import com.example.ecommerce.ecommerceapplication.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findByUuid(String uuid);
}
