package com.example.airport.travelStatus;

import com.example.airport.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/travel-status")
public class TravelStatusController {
    @Autowired
    private TravelStatusRepository travelStatusDao;

    @GetMapping("")
    public ResponseEntity<?> getTravelStatusList(){
        List<TravelStatus> travelStatusList = travelStatusDao.findAll();
        return ResponseEntity.ok(ApiResponse.success(travelStatusList));
    }
}
