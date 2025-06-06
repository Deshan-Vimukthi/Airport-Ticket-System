package com.example.airport.seatClasses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seat-class")
public class SeatClassesControllers {
    @Autowired
    private SeatClassesRepository seatClassesDao;

    @GetMapping("/list/data")
    public List<SeatClasses> getSeatClassesList(){
        return seatClassesDao.findAll();
    }
}
