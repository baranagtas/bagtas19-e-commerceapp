package com.example.ecommerce.ecommerceapplication.controller;

import com.example.ecommerce.ecommerceapplication.dto.OrderDto;
import com.example.ecommerce.ecommerceapplication.model.Cart;
import com.example.ecommerce.ecommerceapplication.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place")
    public ResponseEntity<String> placeOrder(@RequestBody OrderDto orderDto, @RequestBody Cart cart) {
        String result = orderService.placeOrder(orderDto, cart);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{orderUuid}")
    public ResponseEntity<String> updateOrder(@PathVariable String orderUuid, @RequestBody OrderDto orderDto) {
        String result = orderService.updateOrder(orderDto, orderUuid);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{orderUuid}")
    public ResponseEntity<String> deleteOrder(@PathVariable String orderUuid) {
        orderService.deleteOrder(orderUuid);
        return new ResponseEntity<>("Order deleted successfully!", HttpStatus.OK);
    }

    @GetMapping("/{orderUuid}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable String orderUuid) {
        OrderDto order = orderService.getOrderForCode(orderUuid);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/customer/{customerUuid}")
    public ResponseEntity<List<OrderDto>> getOrdersByCustomer(@PathVariable String customerUuid) {
        List<OrderDto> orders = orderService.getAllOrdersByUserUuid(customerUuid);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{orderUuid}/withPriceHistory/{productUuid}")
    public ResponseEntity<OrderDto> getOrderWithPriceHistory(@PathVariable String orderUuid, @PathVariable String productUuid) {
        OrderDto orderDto = orderService.getOrderWithPriceHistory(orderUuid, productUuid);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @GetMapping("/all/customer")
    public ResponseEntity<List<OrderDto>> getAllOrdersForCustomer(@RequestParam String customerUuid) {
        List<OrderDto> orderDtoList = orderService.getAllOrdersForCustomer(customerUuid);
        return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
    }
}