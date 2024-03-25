package com.example.ecommerce.ecommerceapplication.service.impl;

import com.example.ecommerce.ecommerceapplication.dto.OrderDto;
import com.example.ecommerce.ecommerceapplication.dto.ProductDto;
import com.example.ecommerce.ecommerceapplication.model.*;
import com.example.ecommerce.ecommerceapplication.repository.CustomerRepository;
import com.example.ecommerce.ecommerceapplication.repository.OrderRepository;
import com.example.ecommerce.ecommerceapplication.repository.PriceHistoryRepository;
import com.example.ecommerce.ecommerceapplication.repository.ProductRepository;
import com.example.ecommerce.ecommerceapplication.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final PriceHistoryRepository priceHistoryRepository;


    public OrderServiceImpl(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository, PriceHistoryRepository priceHistoryRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.priceHistoryRepository = priceHistoryRepository;
    }

    @Override
    @Transactional
    public String placeOrder(OrderDto orderDto, Cart cart) {
        Optional<Customer> customer = customerRepository.findByUuid(orderDto.getCustomerUuid());

        // Sepeti temizle ve sipariş oluştur
        List<ProductDto> productsInCart = orderDto.getProductsInCart();
        double totalAmount = 0.0;
        List<PriceHistory> priceHistories = new ArrayList<>();
        for (ProductDto productDto : productsInCart) {
            Optional<Product> productOptional = productRepository.findByUuid(productDto.getUuid());
            if (productOptional.isEmpty()) {
                throw new RuntimeException("Product not found: " + productDto.getUuid());
            }
            Product product = productOptional.get();
            if (product.getStockQuantity() < productDto.getStockQuantity()) {
                throw new RuntimeException("Not enough stock for product: " + product.getName());
            }
            totalAmount += product.getPrice() * productDto.getStockQuantity();

            PriceHistory priceHistory = new PriceHistory();
            priceHistory.setPrice(product.getPrice());
            priceHistory.setProductUuid(product.getUuid());
            priceHistory.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            priceHistories.add(priceHistory);
        }

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderNumber(generateOrderNumber());
        order.setOrderDate(new Timestamp(System.currentTimeMillis()));
        order.setTotalAmount(totalAmount);
        order.setStatus(false);
        order.setCustomer(customer.get());
        order.setCart(cart);
        Order savedOrder = orderRepository.save(order);


        for (PriceHistory priceHistory : priceHistories) {
            priceHistory.setOrderUuid(savedOrder.getUuid());
            priceHistoryRepository.save(priceHistory);
        }

        // Sepetteki ürünlerin stoğunu azalt
        updateStockQuantities(productsInCart);

        return "Order saved successfully!";
    }

    private void updateStockQuantities(List<ProductDto> productsInCart) {
        for (ProductDto productDto : productsInCart) {
            Optional<Product> productOptional = productRepository.findByUuid(productDto.getUuid());
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                product.setStockQuantity(product.getStockQuantity() - productDto.getStockQuantity());
                productRepository.save(product);
            }
        }
    }
    @Override
    @Transactional
    public String updateOrder(OrderDto orderDto, String orderUuid) {
        Optional<Order> order = orderRepository.findByUuid(orderUuid);
        Optional<Customer> customer = customerRepository.findByUuid(orderDto.getCustomerUuid());
        if (order.isEmpty() || customer.isEmpty()) {
            throw new RuntimeException("An error occurred during the update!");
        }
        Order updatedOrder = new Order();
        updatedOrder.setOrderNumber(orderDto.getOrderNumber());
        updatedOrder.setOrderDate(new Timestamp(System.currentTimeMillis()));
        updatedOrder.setTotalAmount(orderDto.getTotalAmount());
        updatedOrder.setCustomer(customer.get());
        orderRepository.save(updatedOrder);
        return "Order updated successfully!";
    }

    @Override
    @Transactional
    public void deleteOrder(String orderUuid) {
        Optional<Order> order = orderRepository.findByUuid(orderUuid);
        if (order.isEmpty()) {
            throw new RuntimeException("Order not found!");
        }
        orderRepository.delete(order.get());
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrderWithPriceHistory(String orderUuid, String productUuid) {
        Optional<Order> orderOptional = orderRepository.findByUuid(orderUuid);
        Optional<Product> productOptional = productRepository.findByUuid(productUuid);

        if (orderOptional.isEmpty() || productOptional.isEmpty()) {
            throw new RuntimeException("Order or Product not found");
        }

        Order order = orderOptional.get();
        Product product = productOptional.get();

        Optional<PriceHistory> priceHistoryOptional = priceHistoryRepository.findByOrderUuidAndProductUuid(orderUuid, productUuid);

        if (priceHistoryOptional.isEmpty()) {
            throw new RuntimeException("Price history not found for Order and Product combination");
        }

        OrderDto orderDto = convertToDto(order);
        List<PriceHistory> priceHistoryList = Collections.singletonList(priceHistoryOptional.get());
        orderDto.setPriceHistory(priceHistoryList);

        return orderDto;
    }


    @Override
    public List<OrderDto> getAllOrdersByUserUuid(String customerUuid) {
        List<OrderDto> orderList = new ArrayList<>();
        List<Order> orders = orderRepository.findByCustomerUuid(customerUuid);
        if (orders.isEmpty()) {
            throw new RuntimeException("Orders not found for user with UUID: " + customerUuid);
        }
        for (Order order : orders) {
            orderList.add(convertToDto(order));
        }
        return orderList;
    }

    @Override
    public OrderDto getOrderForCode(String orderCode) {
        Order order = orderRepository.findByOrderCode(orderCode);
        return convertToDto(order);
    }

    @Override
    public List<OrderDto> getAllOrdersForCustomer(String customerUuid) {
        List<Order> orders = orderRepository.findByCustomerUuid(customerUuid);
        return orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public OrderDto convertToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setUuid(order.getUuid());
        orderDto.setOrderNumber(order.getOrderNumber());
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setTotalAmount(order.getTotalAmount());
        orderDto.setOrderCode(order.getOrderCode());
        orderDto.setCustomerUuid(order.getCustomer().getUuid());

        // Ürünleri OrderDto'ya ekle
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : order.getProducts()) {
            ProductDto productDto = new ProductDto();
            productDto.setUuid(product.getUuid());
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setPrice(product.getPrice());
            productDto.setStockQuantity(product.getStockQuantity());
            // Diğer ürün özelliklerini de ekle
            productDtos.add(productDto);
        }
        orderDto.setProductsInCart(productDtos);

        return orderDto;
    }

    private String generateOrderNumber() {
        String formattedDate = LocalDate.now().toString().replace("-", "");
        String randomNumber = String.format("%04d", new Random().nextInt(10000));
        return formattedDate + "-" + randomNumber;
    }
}
