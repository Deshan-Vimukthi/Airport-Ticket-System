package com.example.airport.seatHasClass;

import com.example.airport.flight.Flight;
import com.example.airport.seatClasses.SeatClasses;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "seat_class_allocation")
@Entity
@Data

@NoArgsConstructor
@AllArgsConstructor
public class SeatHasClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private SeatClasses seatClasses;

    @Column(name = "price")
    private Integer price;
}
