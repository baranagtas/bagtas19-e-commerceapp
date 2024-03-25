package com.example.ecommerce.ecommerceapplication.dto;

import com.example.ecommerce.ecommerceapplication.model.Customer;
import com.example.ecommerce.ecommerceapplication.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private String uuid;
    private String productUuid;
    private Date orderDate;
    private Customer customer;
    private List<Product> products;
    private double totalPrice;
}
