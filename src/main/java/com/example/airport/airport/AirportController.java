package com.example.airport.airport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/airport")
public class AirportController {

    @Autowired
    private AirportRepository airportDao;

    @PreAuthorize("hasAuthorize('ADMIN')")
    @PostMapping("/create/save")
    public ResponseEntity<?> createAirport(@RequestBody Airport airport){
        if(airport.getCode().equalsIgnoreCase("")){
            airport.setCode(airport.getName().substring(0,2).toUpperCase());
        }else{
            airport.setCode(airport.getCode().length()>3?
                    airport.getCode().substring(0,2).toUpperCase():
                    airport.getCode().toUpperCase());
        }

        airportDao.save(airport);
        return ResponseEntity.ok("Airport added successfully!");
    }


    @PreAuthorize("hasAuthorize('ADMIN')")
    @PostMapping("/updated/save")
    public ResponseEntity<?> updatedAirport(@RequestBody Airport airport){
        if(airport.getCode().equalsIgnoreCase("")){
            airport.setCode(airport.getName().substring(0,2).toUpperCase());
        }else{
            airport.setCode(airport.getCode().length()>3?
                    airport.getCode().substring(0,2).toUpperCase():
                    airport.getCode().toUpperCase());
        }

        airportDao.save(airport);
        return ResponseEntity.ok("Airport added successfully!");
    }

    @GetMapping("/list/data")
    public List<Airport> getAirportList(){
        return airportDao.findAll();
    }


}
