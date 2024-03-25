package com.example.ecommerce.ecommerceapplication.service.impl;

import com.example.ecommerce.ecommerceapplication.dto.CustomerDto;
import com.example.ecommerce.ecommerceapplication.model.Customer;
import com.example.ecommerce.ecommerceapplication.repository.CustomerRepository;
import com.example.ecommerce.ecommerceapplication.service.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public String addCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setEmail(customerDto.getEmail());
        customer.setTelephone(customerDto.getTelephone());
        customer.setAddress(customerDto.getAddress());

        customerRepository.save(customer);
        return "saved";
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto getCustomerByUuid(String uuid) {
        Optional<Customer> customerOptional = customerRepository.findByUuid(uuid);
        if (customerOptional.isPresent()) {
            return convertToDto(customerOptional.get());
        } else {
            throw new RuntimeException("Customer not found");
        }
    }

    @Override
    @Transactional
    public String updateCustomer(String uuid, CustomerDto customerDto) {
        Optional<Customer> customerOptional = customerRepository.findByUuid(uuid);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();

            customer.setFirstName(customerDto.getFirstName());
            customer.setEmail(customerDto.getEmail());
            customer.setTelephone(customerDto.getTelephone());
            customer.setAddress(customerDto.getAddress());
            customer.setUpdatedAt(LocalDate.now());
            customerRepository.save(customer);
        } else {
            throw new RuntimeException("Customer not found");
        }
        return "Customer is updated";
    }

    @Override
    @Transactional
    public String deleteCustomer(String uuid) {
        Optional<Customer> userOptional = customerRepository.findByUuid(uuid);
        if (userOptional.isPresent()) {
            customerRepository.delete(userOptional.get());
        } else {
            throw new RuntimeException("Customer not found");
        }
        return "Customer is deleted";
    }

    private CustomerDto convertToDto(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        CustomerDto customerDto = new CustomerDto();
        customerDto.setUuid(customer.getUuid());
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setTelephone(customer.getTelephone());
        customerDto.setAddress(customer.getAddress());
        customerDto.setCreatedAt(customer.getCreatedAt());
        customerDto.setUpdatedAt(customer.getUpdatedAt());
        return customerDto;
    }

    private Customer convertToEntity(CustomerDto customerDto) {
        if (customerDto == null) {
            throw new IllegalArgumentException("CustomerDto cannot be null");
        }
        Customer customer = new Customer();
        customer.setUuid(customerDto.getUuid());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setEmail(customerDto.getEmail());
        customer.setTelephone(customerDto.getTelephone());
        customer.setAddress(customerDto.getAddress());
        customer.setCreatedAt(customerDto.getCreatedAt());
        customer.setUpdatedAt(customerDto.getUpdatedAt());
        return customer;
    }
}
