package com.example.airport.seatClasses;

import com.example.airport.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/seat-class")
public class SeatClassesControllers {
    @Autowired
    private SeatClassesRepository seatClassesDao;

    @GetMapping("")
    public ResponseEntity<?> getSeatClassesList(){
        List<SeatClasses> seatClasses = seatClassesDao.findAll();
        return ResponseEntity.ok(
                ApiResponse.success(seatClasses)
        );
    }
}
