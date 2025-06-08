package com.example.airport.seatHasClass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface SeatHasClassRepository extends JpaRepository<SeatHasClass,Integer> {
    @Query("SELECT sc FROM SeatHasClass sc WHERE sc.airplane.id = ?1")
    List<SeatHasClass> getSeatClassesByAirplaneId(Integer id);

    @Query("SELECT sc FROM SeatHasClass sc WHERE sc.id = ?1")
    Optional<SeatHasClass> getSeatHasClassById(Integer id);
}
