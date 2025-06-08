package com.example.airport.countries;

import com.example.airport.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {
    @Autowired
    private CountryRepository countryDao;

    @GetMapping("")
    public ResponseEntity<?> getCountryList(){
        List<Country> countries = countryDao.findAll();
        return ResponseEntity.ok(ApiResponse.success(countries));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCountryData(@PathVariable("id") Integer id){
        Country country = countryDao.getCountryById(id);
        return ResponseEntity.ok(ApiResponse.success(country));
    }


}
