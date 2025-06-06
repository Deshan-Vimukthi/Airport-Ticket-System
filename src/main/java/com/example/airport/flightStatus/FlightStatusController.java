package com.example.airport.flightStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FlightStatusController {
    @Autowired
    private FlightStatusRepository flightStatusDao;

    @GetMapping("/list/data")
    public List<FlightStatus> getFlightStatusList(){
        return flightStatusDao.findAll();
    }
}
