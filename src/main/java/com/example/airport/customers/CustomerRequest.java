package com.example.airport.customers;

import com.example.airport.users.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerRequest {
    private User user;
    private Customer customer;
}
