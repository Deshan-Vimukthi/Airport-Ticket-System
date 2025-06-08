package com.example.airport.airplane;

import com.example.airport.countries.CountryRepository;
import com.example.airport.models.AirplaneModelsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/airplanes")
public class AirplaneUIController {

    @Autowired
    private AirplaneRepository airplaneDao;

    @Autowired
    private AirplaneModelsRepository airplaneModelsDao;

    @Autowired
    private CountryRepository countryDao;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'OPERATION_MANAGER')")
    @GetMapping("/list/id")
    public ModelAndView getListUI(@RequestParam("id") Integer id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view-airplane.html");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'OPERATION_MANAGER')")
    @GetMapping("/view/id")
    public ModelAndView getViewUI(@RequestParam("id") Integer id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view-airplane.html");
        return modelAndView;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/create")
    public ModelAndView getCreateAirplaneUi(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("create-airplane.html");
        return modelAndView;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/update/id")
    public ModelAndView getUpdateAirplaneUi(@RequestParam("id") Integer id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("create-airplane.html");
        return modelAndView;
    }
}
