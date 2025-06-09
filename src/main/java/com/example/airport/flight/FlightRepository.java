package com.example.airport.flight;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Query("SELECT f FROM Flight f WHERE f.airplane.id = ?1")
    List<Flight> getFlightsByAirplane(Integer id);

    @Query("SELECT f FROM Flight f WHERE f.airplane.id = :?1 ORDER BY f.ArriveTime DESC")
    List<Flight> findLatestFlightByAirplaneId(Integer airplaneId);

}
