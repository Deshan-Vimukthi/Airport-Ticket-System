package com.example.airport.seats;

import com.example.airport.availability.Availability;
import com.example.airport.flight.Flight;
import com.example.airport.seatClasses.SeatClasses;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "seats")
@Entity
@Data

@NoArgsConstructor
@AllArgsConstructor
public class Seats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "row_id")
    private Integer rowId;

    @Column(name = "column_id")
    private Integer columnId;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private SeatClasses seatClasses;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @ManyToOne
    @JoinColumn(name = "availability_id")
    private Availability availability;

}