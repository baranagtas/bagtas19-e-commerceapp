package com.example.ecommerce.ecommerceapplication.dto;

import com.example.ecommerce.ecommerceapplication.model.Cart;
import com.example.ecommerce.ecommerceapplication.model.PriceHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private String uuid;
    private String orderNumber;
    private Date orderDate;
    private double totalAmount;
    private String orderStatus;
    private String orderCode;
    private String customerUuid;
    private Cart cart;
    private List<ProductDto> productsInCart;
    private List<PriceHistory> priceHistory;
}
