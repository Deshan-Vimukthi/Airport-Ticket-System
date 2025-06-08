package com.example.airport.payments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {

    @Query("SELECT p FROM Payment p WHERE p.id = ?1")
    List<Payment> getPaymentsByInvoice(Integer id);
}
