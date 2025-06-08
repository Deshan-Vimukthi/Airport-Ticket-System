package com.example.airport.bookingStatus;

import com.example.airport.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/booking-status")
public class BookingStatusController {
    @Autowired
    private BookingStatusRepository bookingStatusDao;

    @GetMapping("")
    public ResponseEntity<?> getBookingStatusList(){
        List<BookingStatus> bookingStatusList = bookingStatusDao.findAll();
        return ResponseEntity.ok(ApiResponse.success(bookingStatusList));
    }
}
