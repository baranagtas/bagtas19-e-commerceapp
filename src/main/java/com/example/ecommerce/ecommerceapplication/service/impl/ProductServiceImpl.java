package com.example.ecommerce.ecommerceapplication.service.impl;

import com.example.ecommerce.ecommerceapplication.dto.ProductDto;
import com.example.ecommerce.ecommerceapplication.model.Product;
import com.example.ecommerce.ecommerceapplication.repository.ProductRepository;
import com.example.ecommerce.ecommerceapplication.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductByUuid(String uuid) {
        Product product = productRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return convertToDto(product);
    }

    @Override
    @Transactional
    public String saveProduct(ProductDto productDto) {

        Product product = convertToEntity(productDto);

        productRepository.save(product);

        return "Ürün başarıyla kaydedildi";
    }

    @Override
    @Transactional
    public String updateProduct(String uuid, ProductDto updatedProductDto) {

        Optional<Product> productOptional = productRepository.findByUuid(uuid);
        if (productOptional.isPresent()) {
            Product existingProduct = productOptional.get();
            existingProduct.setName(updatedProductDto.getName());
            existingProduct.setDescription(updatedProductDto.getDescription());
            existingProduct.setPrice(updatedProductDto.getPrice());
            existingProduct.setStockQuantity(updatedProductDto.getStockQuantity());
            existingProduct.setUpdatedAt(LocalDate.now());
            productRepository.save(existingProduct);
            return "Product is updated";
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    @Override
    @Transactional
    public String deleteProduct(String uuid) {
        Optional<Product> productForDeletion = productRepository.findByUuid(uuid);

        if (productForDeletion.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        productRepository.delete(productForDeletion.get());

        return "Product is deleted";
    }

    private ProductDto convertToDto(Product product) {

        ProductDto productDto = new ProductDto();
        productDto.setUuid(product.getUuid());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setStockQuantity(product.getStockQuantity());
        productDto.setCreatedAt(product.getCreatedAt());
        productDto.setUpdatedAt(product.getUpdatedAt());

        return productDto;
    }

    private Product convertToEntity(ProductDto productDto) {

        Product product = new Product();
        product.setUuid(productDto.getUuid());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStockQuantity(productDto.getStockQuantity());
        product.setCreatedAt(productDto.getCreatedAt());
        product.setUpdatedAt(productDto.getUpdatedAt());

        return product;
    }
}
