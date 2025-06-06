package com.example.airport.airplane;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AirplaneRepository extends JpaRepository<Airplane,Integer> {

    @Query("SELECT COUNT(*) FROM Airplane a WHERE a.country.id = ?1 AND a.model.id = ?2")
    Integer getAirplaneCountOfModelInCountry(Integer countryId, Integer modelId);

    @Query("SELECT a FROM Airplane a")
    List<Airplane> findAll();

    @Query("SELECT a FROM Airplane a WHERE a.id = ?1")
    Airplane getAirplaneById(Integer id);

}
