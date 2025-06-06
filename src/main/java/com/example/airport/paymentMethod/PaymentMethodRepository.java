package com.example.airport.paymentMethod;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod,Integer> {

    @Query("SELECT pm FROM PaymentMethod pm WHERE pm.id = ?1")
    PaymentMethod getPaymentMethodById(Integer id);
}
