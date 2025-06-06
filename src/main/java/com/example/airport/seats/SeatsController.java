package com.example.airport.seats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeatsController {
    @Autowired
    private SeatsRepository seatsDao;
}
