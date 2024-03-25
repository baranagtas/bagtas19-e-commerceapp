package com.example.ecommerce.ecommerceapplication.service;

import com.example.ecommerce.ecommerceapplication.dto.CustomerDto;

import java.util.List;

public interface CustomerService {
    String addCustomer(CustomerDto customerDto);

    List<CustomerDto> getAllCustomers();

    CustomerDto getCustomerByUuid(String uuid);

    String updateCustomer(String uuid, CustomerDto customerDto);

    String deleteCustomer(String uuid);
}
