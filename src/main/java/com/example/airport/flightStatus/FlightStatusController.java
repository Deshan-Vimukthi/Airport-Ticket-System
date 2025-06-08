package com.example.airport.flightStatus;

import com.example.airport.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/flight-status")
public class FlightStatusController {
    @Autowired
    private FlightStatusRepository flightStatusDao;

    @GetMapping("")
    public ResponseEntity<?> getFlightStatusList(){
        List<FlightStatus> flightStatuses =  flightStatusDao.findAll();
        return ResponseEntity.ok(ApiResponse.success(flightStatuses));
    }
}
