package com.example.ecommerce.ecommerceapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceHistoryDto {
    private double price;
    private String productUuid;
    private String orderUuid;
    private Timestamp createdAt;
}
