package com.example.airport.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AirplaneModelsRepository extends JpaRepository<AirplaneModels, Integer> {

    @Query("SELECT am FROM AirplaneModels am WHERE am.id = ?1")
    AirplaneModels getAirplaneModelById(Integer id);
}
