package com.example.airport.seatClasses;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SeatClassesRepository extends JpaRepository<SeatClasses,Integer> {
    @Query("SELECT sc FROM SeatClasses sc WHERE sc.id = ?1")
    Optional<SeatClasses> getSeatClassById(Integer id);
}
