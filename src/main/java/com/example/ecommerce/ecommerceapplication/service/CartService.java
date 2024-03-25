package com.example.ecommerce.ecommerceapplication.service;

import com.example.ecommerce.ecommerceapplication.dto.CartDto;
import com.example.ecommerce.ecommerceapplication.dto.OrderDto;

public interface CartService {
    CartDto getCartByUuid(String cartUuid);

    String createCart(OrderDto orderDto);

    CartDto updateCart(String cartUuid,String[] productUuids);

    void emptyCart(String cartUuid);

    CartDto addProductToCart(String cartUuid, String productUuid, int quantity);

    String removeProductFromCart(String cartUuid, String productUuid);

}
