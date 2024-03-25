package com.example.ecommerce.ecommerceapplication.service;

import com.example.ecommerce.ecommerceapplication.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> getAllProducts();

    ProductDto getProductByUuid(String uuid);

    String saveProduct(ProductDto productDto);

    String updateProduct(String uuid, ProductDto updatedProductDto);

    String deleteProduct(String uuid);

}
