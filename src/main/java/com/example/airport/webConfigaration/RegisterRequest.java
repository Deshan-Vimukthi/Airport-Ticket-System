package com.example.airport.webConfigaration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private CustomerDTO customer;
    private UserDTO user;

    // getters and setters

    @Data
    public static class CustomerDTO {
        private String fullName;
        private String email;
        private String phone;
        private String passportNumber;
        // getters/setters
    }

    @Data
    public static class UserDTO {
        private String username;
        private String password;
        // getters/setters
    }
}

