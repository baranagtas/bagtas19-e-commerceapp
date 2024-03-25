package com.example.ecommerce.ecommerceapplication.service.impl;

import com.example.ecommerce.ecommerceapplication.dto.CartDto;
import com.example.ecommerce.ecommerceapplication.dto.OrderDto;
import com.example.ecommerce.ecommerceapplication.dto.ProductDto;
import com.example.ecommerce.ecommerceapplication.model.*;
import com.example.ecommerce.ecommerceapplication.repository.*;
import com.example.ecommerce.ecommerceapplication.service.CartService;
import com.example.ecommerce.ecommerceapplication.service.OrderService;
import com.example.ecommerce.ecommerceapplication.utils.CommonUtils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;


@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderService orderService;

    public CartServiceImpl(CartRepository cartRepository, CustomerRepository customerRepository, ProductRepository productRepository, OrderService orderService) {
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderService = orderService;
    }


    @Override
    public CartDto getCartByUuid(String cartUuid) {
        Cart cart = cartRepository.findByUuid(cartUuid)
                .orElseThrow(() -> new RuntimeException("Cart not found with UUID: " + cartUuid));

        String[] uuids = CommonUtils.commaSeperatedToArray(cart.getProductUuids());
        List<Product> products = new ArrayList<>();
        for (String uuid : uuids) {
            products.add(productRepository.findByUuid(uuid).orElse(null));
        }
        CartDto cartDto = convertToDto(cart);
        cartDto.setProducts(products);

        return cartDto;
    }


    @Override
    @Transactional
    public String createCart(OrderDto orderDto) {
        Optional<Customer> customer = customerRepository.findByUuid(orderDto.getCustomerUuid());
        if (customer.isEmpty()) {
            throw new RuntimeException("Customer not found witht this uuid: " + orderDto.getCustomerUuid());
        }

        String[] uuids = new String[orderDto.getProductsInCart().size()];
        int i = 0;
        for (ProductDto s : orderDto.getProductsInCart()) {
            uuids[i] = s.getUuid();
        }
        Cart cart = new Cart();
        cart.setUuid(UUID.randomUUID().toString());
        cart.setOrderDate(new Timestamp(System.currentTimeMillis()));
        cart.setCustomer(customer.get());
        cart.setTotalPrice(orderDto.getTotalAmount());
        cart.setProductUuids(CommonUtils.arrayToString(uuids));
        Cart savedCart = cartRepository.save(cart);

        orderService.placeOrder(orderDto, savedCart);
        return cart.getUuid();
    }


    @Override
    public CartDto updateCart(String cartUuid, String[] productUuids) {
        Cart cart = cartRepository.findByUuid(cartUuid)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.setProductUuids(CommonUtils.arrayToString(productUuids));
        return convertToDto(cart);
    }

    @Override
    public void emptyCart(String cartUuid) {
        Cart cart = cartRepository.findByUuid(cartUuid)
                .orElseThrow(() -> new RuntimeException("Cart not found with UUID: " + cartUuid));
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);
    }


    @Override
    public CartDto addProductToCart(String cartUuid, String productUuid, int quantity) {
        Cart cart = cartRepository.findByUuid(cartUuid)
                .orElseThrow(() -> new RuntimeException("Cart not found with UUID: " + cartUuid));

        Product product = productRepository.findByUuid(productUuid)
                .orElseThrow(() -> new RuntimeException("Product not found with UUID: " + productUuid));

        if (product.getStockQuantity() == 0) {
            throw new RuntimeException(product.getName() + " is not available");
        }

        if (product.getStockQuantity() < quantity) {
            throw new RuntimeException("Please, make an order of " + product.getName()
                    + " less than or equal to the quantity " + product.getStockQuantity() + ".");
        }


        double totalPrice = cart.getTotalPrice() + (product.getPrice() * quantity);
        cart.setTotalPrice(totalPrice);

        cartRepository.save(cart);

        int updatedStockQuantity = product.getStockQuantity() - quantity;
        product.setStockQuantity(updatedStockQuantity);
        productRepository.save(product);

        return convertToDto(cart);
    }

    @Override
    public String removeProductFromCart(String cartUuid, String productUuid) {
        Cart cart = cartRepository.findByUuid(cartUuid)
                .orElseThrow(() -> new RuntimeException("Cart not found with UUID: " + cartUuid));

        String[] productUuids = CommonUtils.commaSeperatedToArray(cart.getProductUuids());

        List<String> updatedProductUuids = new ArrayList<>(Arrays.asList(productUuids));
        boolean removed = updatedProductUuids.remove(productUuid);

        if (removed) {
            // Sepetin toplam fiyatını güncelle
            double totalPrice = cart.getTotalPrice();
            Product product = productRepository.findByUuid(productUuid)
                    .orElseThrow(() -> new RuntimeException("Product not found with UUID: " + productUuid));
            totalPrice -= product.getPrice();
            cart.setTotalPrice(totalPrice);

            cart.setProductUuids(CommonUtils.arrayToString(updatedProductUuids.toArray(new String[0])));

            cartRepository.save(cart);

            return "Product with UUID " + productUuid + " removed from the cart.";
        } else {
            throw new RuntimeException("Product with UUID " + productUuid + " is not in the cart.");
        }
    }

    private CartDto convertToDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setUuid(cart.getUuid());
        cartDto.setOrderDate(cart.getOrderDate());
        cartDto.setCustomer(cart.getCustomer());
        cartDto.setTotalPrice(cart.getTotalPrice());

        return cartDto;
    }
}
