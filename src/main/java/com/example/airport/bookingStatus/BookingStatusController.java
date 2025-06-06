package com.example.airport.bookingStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/booking-status")
public class BookingStatusController {
    @Autowired
    private BookingStatusRepository bookingStatusDao;

    @GetMapping("/list/data")
    public List<BookingStatus> getBookingStatusList(){
        return bookingStatusDao.findAll();
    }
}
