package com.example.airport.countryHasTimezone;

import com.example.airport.ApiResponse;
import com.example.airport.countries.Country;
import com.example.airport.timezones.Timezone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/country-timezone")
public class CountryHasTimezoneController {

    @Autowired
    private CountryHasTimezoneRepository countryHasTimezoneDao;

    @GetMapping("")
    public ResponseEntity<?> getCountryHasTimezoneList(){
        List<CountryHasTimezone> countryHasTimezoneList = countryHasTimezoneDao.findAll();
        return ResponseEntity.ok(ApiResponse.success(countryHasTimezoneList));
    }

    @GetMapping("/country/{id}")
    public ResponseEntity<?> getTimezonesByCountry(@PathVariable("id") Integer id){
        List<Timezone> timezoneList = countryHasTimezoneDao.getTimezoneByCountryId(id);
        return ResponseEntity.ok(ApiResponse.success(timezoneList));
    }

    @GetMapping("/timezone/{id}")
    public ResponseEntity<?> getCountriesByTimezone(@PathVariable("id") Integer id){
        List<Country> countryList = countryHasTimezoneDao.getCountryByTimezoneId(id);
        return ResponseEntity.ok(ApiResponse.success(countryList));
    }


}
