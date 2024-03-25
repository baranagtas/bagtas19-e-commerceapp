package com.example.ecommerce.ecommerceapplication.controller;

import com.example.ecommerce.ecommerceapplication.dto.CartDto;
import com.example.ecommerce.ecommerceapplication.dto.OrderDto;
import com.example.ecommerce.ecommerceapplication.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{cartUuid}")
    public ResponseEntity<CartDto> getCartByUuid(@PathVariable String cartUuid) {
        CartDto cartDto = cartService.getCartByUuid(cartUuid);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCart(@RequestBody OrderDto orderDto) {
        String cartUuid = cartService.createCart(orderDto);
        return new ResponseEntity<>(cartUuid, HttpStatus.CREATED);
    }

    @PutMapping("/{cartUuid}")
    public ResponseEntity<CartDto> updateCart(@PathVariable String cartUuid, @RequestBody String[] productUuid) {
        CartDto updatedCartDto = cartService.updateCart(cartUuid, productUuid);
        return new ResponseEntity<>(updatedCartDto, HttpStatus.OK);
    }

    @DeleteMapping("/empty/{cartUuid}")
    public ResponseEntity<Void> emptyCart(@PathVariable String cartUuid) {
        cartService.emptyCart(cartUuid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{cartUuid}/add-product")
    public ResponseEntity<CartDto> addProductToCart(@PathVariable String cartUuid, @RequestParam String productUuid, @RequestParam int quantity) {
        CartDto cartDto = cartService.addProductToCart(cartUuid, productUuid, quantity);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/{cartUuid}/remove-product")
    public ResponseEntity<String> removeProductFromCart(@PathVariable String cartUuid, @RequestParam String productUuid) {
        String message = cartService.removeProductFromCart(cartUuid, productUuid);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
