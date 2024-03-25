package com.example.ecommerce.ecommerceapplication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "uuid")
    private String uuid;
    @Column(name = "order_date")
    private Timestamp orderDate;
    @Column(name = "total_price")
    private double totalPrice = 0.0;

    @Column(name = "product_uuids")
    private String productUuids;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "cart")
    private List<Order> orders = new ArrayList<>();

}
