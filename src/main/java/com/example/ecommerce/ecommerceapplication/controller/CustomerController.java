package com.example.ecommerce.ecommerceapplication.controller;

import com.example.ecommerce.ecommerceapplication.dto.CustomerDto;
import com.example.ecommerce.ecommerceapplication.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<String> addCustomer(@RequestBody CustomerDto customerDto) {
        String message = customerService.addCustomer(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        List<CustomerDto> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CustomerDto> getCustomerByUuid(@PathVariable String uuid) {
        CustomerDto customer = customerService.getCustomerByUuid(uuid);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<String> updateCustomer(@PathVariable String uuid, @RequestBody CustomerDto updatedCustomerDto) {
        String message = customerService.updateCustomer(uuid, updatedCustomerDto);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String uuid) {
        String message = customerService.deleteCustomer(uuid);
        return ResponseEntity.ok(message);
    }
}