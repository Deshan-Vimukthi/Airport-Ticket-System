package com.example.airport.seatHasClass;

import com.example.airport.airplane.Airplane;
import com.example.airport.airport.Airport;
import com.example.airport.flight.Flight;
import com.example.airport.seatClasses.SeatClasses;
import com.example.airport.seatLayout.SeatLayout;
import com.example.airport.seats.Seats;
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
    @JoinColumn(name = "airplane_id")
    private Airplane airplane;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private SeatClasses seatClasses;

    @ManyToOne
    @JoinColumn(name = "first_seat_no")
    private Seats firstSeatNo;

    @ManyToOne
    @JoinColumn(name = "last_seat_no")
    private Seats lastSeatNo;

    @ManyToOne
    @JoinColumn(name = "seat_layout_id")
    private SeatLayout seatLayout;


}
