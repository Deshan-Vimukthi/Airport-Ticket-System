package com.example.airport.seatLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seat-layout")
public class SeatLayoutController {
    @Autowired
    private SeatLayoutRepository seatLayoutDao;

    @GetMapping("/list/data")
    public List<SeatLayout> getSeatLayoutList(){
        return seatLayoutDao.findAll();
    }
}
