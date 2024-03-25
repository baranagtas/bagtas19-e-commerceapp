package com.example.ecommerce.ecommerceapplication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "price_histories")
public class PriceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "price")
    private double price;
    @Column(name = "product_uuid")
    private String productUuid;
    @Column(name = "order_uuid")
    private String orderUuid;
    @Column(name = "created_at")
    private Timestamp createdAt;

}
