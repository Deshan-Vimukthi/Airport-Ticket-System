package com.example.airport.countries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/countries")
public class CountryController {
    @Autowired
    private CountryRepository countryDao;

    @GetMapping("/list/data")
    public List<Country> getCountryList(){
        return countryDao.findAll();
    }

    @GetMapping("/data/id")
    public Country getCountryData(@RequestParam("id") Integer id){
        return countryDao.getCountryById(id);
    }


}
