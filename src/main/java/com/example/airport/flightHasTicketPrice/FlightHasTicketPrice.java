package com.example.airport.flightHasTicketPrice;

import com.example.airport.flight.Flight;
import com.example.airport.seatClasses.SeatClasses;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "flight_ticket_price_allocation")
@Entity
@Data

@NoArgsConstructor
@AllArgsConstructor
public class FlightHasTicketPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "flight_id", referencedColumnName = "id")
    private Flight flight_id;

    @ManyToOne
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    private SeatClasses class_id;

    @Column(name = "price")
    private Double price;
}
