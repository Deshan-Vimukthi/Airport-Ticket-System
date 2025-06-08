package com.example.airport.seatLayout;

import com.example.airport.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/seat-layout")
public class SeatLayoutController {
    @Autowired
    private SeatLayoutRepository seatLayoutDao;

    @GetMapping("")
    public ResponseEntity<?> getSeatLayoutList(){
        List<SeatLayout> seatLayouts = seatLayoutDao.findAll();
        return ResponseEntity.ok(ApiResponse.success(seatLayouts));
    }
}
