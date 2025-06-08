package com.example.airport.availability;

import com.example.airport.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {

    @Autowired
    private AvailabilityRepository availabilityDao;

    @GetMapping("")
    public ResponseEntity<?> getAvailabilitySatatusList(){
        List<Availability> availabilityList = availabilityDao.findAll();
        return ResponseEntity.ok(ApiResponse.success("Availability list retrieved",availabilityList));
    }
}
