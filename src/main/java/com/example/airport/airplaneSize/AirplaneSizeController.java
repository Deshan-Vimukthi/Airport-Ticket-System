package com.example.airport.airplaneSize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/airplane-size")
public class AirplaneSizeController {

    @Autowired
    private AirplaneSizeRepository airplaneSizeDao;

    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER','OPERATOR')")
    @GetMapping(value = "/list/data")
    public List<AirplaneSize> getAirplaneList(){
        return airplaneSizeDao.findAll();
    }
}
