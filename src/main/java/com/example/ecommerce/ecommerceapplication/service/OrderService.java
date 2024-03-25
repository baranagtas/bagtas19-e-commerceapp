package com.example.ecommerce.ecommerceapplication.service;

import com.example.ecommerce.ecommerceapplication.dto.OrderDto;
import com.example.ecommerce.ecommerceapplication.dto.ProductDto;
import com.example.ecommerce.ecommerceapplication.model.Cart;
import com.example.ecommerce.ecommerceapplication.model.Order;
import com.example.ecommerce.ecommerceapplication.model.Product;

import java.util.List;

public interface OrderService {
    String placeOrder(OrderDto orderDto, Cart cart);

    String updateOrder(OrderDto orderDto, String orderUuid);

    void deleteOrder(String orderUuid);

    List<OrderDto> getAllOrders();
    OrderDto getOrderWithPriceHistory(String orderUuid, String productUuid);

    List<OrderDto> getAllOrdersByUserUuid(String customerUuid);

    OrderDto getOrderForCode(String orderCode);

    List<OrderDto> getAllOrdersForCustomer(String customerUuid);

}
