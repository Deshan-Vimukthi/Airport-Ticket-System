package com.example.airport.timezones;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TimezoneRepository extends JpaRepository<Timezone, Integer> {
    @Query("SELECT t FROM Timezone t WHERE t.id = ?1")
    Timezone getTimezoneById(Integer id);
}
