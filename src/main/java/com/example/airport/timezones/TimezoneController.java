package com.example.airport.timezones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/timezone")
public class TimezoneController {
    @Autowired
    private TimezoneRepository timezoneDao;

    @GetMapping("/list/data")
    public List<Timezone> getTimezoneList(){
        return timezoneDao.findAll();
    }

    @GetMapping("/data/id")
    public Timezone getTimezone(@RequestParam("id") Integer id){
        return timezoneDao.getTimezoneById(id);
    }
}
