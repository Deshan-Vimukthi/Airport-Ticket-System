package com.example.airport.countries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CountryRepository  extends JpaRepository<Country,Integer> {

    @Query("SELECT c FROM Country c WHERE c.id =?1")
    Country getCountryById(Integer id);
}
