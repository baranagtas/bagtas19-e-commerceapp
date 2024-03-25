package com.example.ecommerce.ecommerceapplication.controller;

import com.example.ecommerce.ecommerceapplication.dto.ProductDto;
import com.example.ecommerce.ecommerceapplication.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ProductDto> getProductByUuid(@PathVariable String uuid) {
        ProductDto product = productService.getProductByUuid(uuid);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<String> saveProduct(@RequestBody ProductDto productDto) {
        String message = productService.saveProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<String> updateProduct(@PathVariable String uuid, @RequestBody ProductDto updatedProductDto) {
        String message = productService.updateProduct(uuid, updatedProductDto);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> deleteProduct(@PathVariable String uuid) {
        String message = productService.deleteProduct(uuid);
        return ResponseEntity.ok(message);
    }
}