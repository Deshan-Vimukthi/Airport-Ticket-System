package com.example.airport.flight;

import com.example.airport.airplane.Airplane;
import com.example.airport.airport.Airport;
import com.example.airport.flightStatus.FlightStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Table(name = "flight")
@Entity
@Data

@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "arrive_date")
    private LocalDate arriveDate;

    @Column(name = "arrive_time")
    private LocalTime arriveTime;

    @ManyToOne
    @JoinColumn(name = "arrive_airport_id")
    private Airport arriveAirport;

    @Column(name = "departure_date")
    private LocalDate departureDate;

    @Column(name = "departure_time")
    private LocalTime departureTime;

    @ManyToOne
    @JoinColumn(name = "departure_airport_id")
    private Airport departureAirport;

    @ManyToOne
    @JoinColumn(name = "airplane_id")
    private Airplane airplane;

    @ManyToOne
    @JoinColumn(name = "flight_status_id")
    private FlightStatus flightStatus;

    @Column(name = "flight_status_reason")
    private String reason;
}
