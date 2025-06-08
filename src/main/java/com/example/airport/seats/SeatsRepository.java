package com.example.airport.seats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SeatsRepository extends JpaRepository<Seats, Integer> {
    @Query("SELECT s FROM Seats s WHERE s.id = ?1")
    Optional<Seats> getSeatById(Integer id);

    @Query("SELECT s FROM Seats s WHERE s.flight.id = ?1")
    List<Seats> getSeatsByAirplane(Integer id);

}
