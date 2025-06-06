package com.example.airport.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/airplane-models")
public class AirplaneModelsControllers {
    @Autowired
    private AirplaneModelsRepository airplaneModelsDao;

    @GetMapping("/list/data")
    public List<AirplaneModels> getAirplaneModelsList(){
        return airplaneModelsDao.findAll();
    }
}
