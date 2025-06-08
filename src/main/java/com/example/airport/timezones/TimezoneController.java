package com.example.airport.timezones;

import com.example.airport.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/timezone")
public class TimezoneController {
    @Autowired
    private TimezoneRepository timezoneDao;

    @GetMapping("")
    public ResponseEntity<?> getTimezoneList(){
        List<Timezone> timezoneList = timezoneDao.findAll();
        return ResponseEntity.ok(ApiResponse.success(timezoneList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTimezone(@RequestParam("id") Integer id){
        Timezone timezone = timezoneDao.getTimezoneById(id);
        return ResponseEntity.ok(ApiResponse.success(timezone));
    }
}
