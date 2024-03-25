package com.example.ecommerce.ecommerceapplication.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private String uuid;
    private String firstName;
    private String lastName;
    private String email;
    private String telephone;
    private String address;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
