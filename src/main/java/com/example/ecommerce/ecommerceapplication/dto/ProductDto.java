package com.example.ecommerce.ecommerceapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String uuid;
    private String name;
    private String description;
    private double price;
    private int stockQuantity;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
