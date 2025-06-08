package com.example.airport.countryHasTimezone;

import com.example.airport.countries.Country;
import com.example.airport.timezones.Timezone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CountryHasTimezoneRepository extends JpaRepository<CountryHasTimezone, Long> {

    @Query("SELECT ct.timezoneId FROM CountryHasTimezone ct WHERE ct.countryId.id = ?1")
    List<Timezone> getTimezoneByCountryId(Integer id);

    @Query("SELECT ct.countryId FROM CountryHasTimezone ct WHERE ct.timezoneId.id = ?1")
    List<Country> getCountryByTimezoneId(Integer id);
}
