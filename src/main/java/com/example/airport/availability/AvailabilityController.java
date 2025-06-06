package com.example.airport.availability;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {

    @Autowired
    private AvailabilityRepository availabilityDao;

    @GetMapping("/list/data")
    public List<Availability> getAvailabilitySatatusList(){
        return availabilityDao.findAll();
    }
}
