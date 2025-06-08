package com.example.airport.seats;

import com.example.airport.ApiResponse;
import com.example.airport.exceptionHandling.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatsController {
    @Autowired
    private SeatsRepository seatsDao;

    @GetMapping("/airplane/{id}")
    public ResponseEntity<?> getSeatsList(@PathVariable("id") Integer id){
        List<Seats> seats = seatsDao.getSeatsByAirplane(id);
        return ResponseEntity.ok(ApiResponse.success(seats));
    }
}
