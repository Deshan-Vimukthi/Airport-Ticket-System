package com.example.airport.payments;

import com.example.airport.invoices.Invoice;
import com.example.airport.paymentMethod.PaymentMethod;
import com.example.airport.paymentType.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "payments")
@Entity
@Data

@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "amount")
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "payment_type_id")
    private PaymentType paymentType;

    @Column(name = "reason")
    private String reason;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
}

