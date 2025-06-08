package com.example.airport.customers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query("SELECT c FROM Customer c WHERE c.id = ?1")
    Customer getCustomerById(Integer id);

    @Query("SELECT MAX(c.id) FROM Customer c")
    Optional<Integer> getNextId();
}
