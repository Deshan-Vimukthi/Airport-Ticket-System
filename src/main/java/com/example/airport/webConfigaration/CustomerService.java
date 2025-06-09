package com.example.airport.webConfigaration;

import com.example.airport.customers.Customer;
import com.example.airport.customers.CustomerRepository;
import com.example.airport.users.User;
import com.example.airport.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public boolean register(RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUser().getUsername()).isEmpty()) {
            return false;
        }

        Customer customer = new Customer();
        customer.setName(registerRequest.getCustomer().getFullName());
        customer.setEmail(registerRequest.getCustomer().getEmail());
        customer.setPhone(registerRequest.getCustomer().getPhone());
        customer.setPassport_number(registerRequest.getCustomer().getPassportNumber());

        User user = new User();
        user.setUsername(registerRequest.getUser().getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getUser().getPassword()));


        customer = customerRepository.save(customer);

        // associate
        user.setCustomer(customer);

        // persist both
        user = userRepository.save(user);

        authService.login(new LoginRequest(user.getUsername(), user.getPassword()));

        return true;
    }
}

