package com.example.airport.travelStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/travel-status")
public class TravelStatusController {
    @Autowired
    private TravelStatusRepository travelStatusDao;

    @GetMapping("/list/data")
    public List<TravelStatus> getTravelStatusList(){
        return travelStatusDao.findAll();
    }
}
