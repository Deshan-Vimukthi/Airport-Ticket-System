package com.example.airport.booking;

import com.example.airport.bookingStatus.BookingStatus;
import com.example.airport.customers.Customer;
import com.example.airport.invoices.Invoice;
import com.example.airport.seats.Seats;
import com.example.airport.users.User;
import com.example.airport.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import lombok.Data;

@Table(name = "booking")
@Entity
@Data

@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({View.Customer.class,View.Operator.class,View.Admin.class})
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonView({View.Customer.class,View.Operator.class,View.Admin.class})
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonView({View.Operator.class,View.Admin.class})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonView({View.Customer.class,View.Operator.class,View.Admin.class})
    @JoinColumn(name = "seat_id")
    private Seats seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonView({View.Customer.class,View.Operator.class,View.Admin.class})
    @JoinColumn(name = "booking_status_id")
    private BookingStatus bookingStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonView({View.Customer.class,View.Operator.class,View.Admin.class})
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
}
