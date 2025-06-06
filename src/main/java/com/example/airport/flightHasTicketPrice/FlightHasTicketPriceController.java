package com.example.airport.flightHasTicketPrice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlightHasTicketPriceController {
    @Autowired
    private FlightHasTicketPriceRepository flightHasTicketPriceDao;


}
