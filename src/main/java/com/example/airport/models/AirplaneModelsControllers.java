package com.example.airport.models;

import com.example.airport.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/airplane-models")
public class AirplaneModelsControllers {
    @Autowired
    private AirplaneModelsRepository airplaneModelsDao;

    @GetMapping("")
    public ResponseEntity<?> getAirplaneModelsList(){
        List<AirplaneModels> airplaneModels = airplaneModelsDao.findAll();
        return ResponseEntity.ok(
                ApiResponse.success(airplaneModels)
        );
    }
}
