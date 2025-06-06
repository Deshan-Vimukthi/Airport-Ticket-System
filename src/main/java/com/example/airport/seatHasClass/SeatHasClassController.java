package com.example.airport.seatHasClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeatHasClassController {
    @Autowired
    private SeatHasClassRepository seatHasClassDao;
}
