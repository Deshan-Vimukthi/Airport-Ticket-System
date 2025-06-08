package com.example.airport.travelStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TravelStatusRepository extends JpaRepository<TravelStatus, Integer> {
    @Query("SELECT ts FROM TravelStatus ts WHERE ts.id = ?1")
    TravelStatus getTravelStatusById(Integer id);
}
