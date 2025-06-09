package com.example.airport.flightStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FlightStatusRepository extends JpaRepository<FlightStatus, Integer> {
    @Query("SELECT fs FROm FlightStatus fs WHERE fs.id = ?1")
    FlightStatus getFlightStatus(Integer id);
}
