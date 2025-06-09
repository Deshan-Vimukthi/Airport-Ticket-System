package com.example.airport.flight;

import com.example.airport.exceptionHandling.accessDeniedException.AccessDeniedCode;
import com.example.airport.exceptionHandling.accessDeniedException.AccessDeniedException;
import com.example.airport.users.User;
import com.example.airport.users.UserRepository;
import com.example.airport.webConfigaration.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/flights")
public class FlightUIController {
    @Autowired
    private UserRepository userDao;

    @GetMapping("")
    public ModelAndView showFlights() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDao.findByUsername(auth.getName()).orElseThrow(()->new AccessDeniedException("User not authenticated", AccessDeniedCode.NOT_SIGNED_IN));
        ModelAndView mav = new ModelAndView();
        mav = MenuItem.addMenu(user, mav); // Sets sidebar and view name
        mav.addObject("content", "flight"); // Sets which fragment to load
        mav.addObject("selectedUrl", "/flights");
        mav.addObject("customJSURL","/flights.js");
        return mav;
    }

    @GetMapping("/from-airplane/{id}")
    public ModelAndView flightListFromAirplane(@PathVariable("id")Integer id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDao.findByUsername(auth.getName()).orElseThrow(()->new AccessDeniedException("User not authenticated", AccessDeniedCode.NOT_SIGNED_IN));
        ModelAndView mav = new ModelAndView();
        mav = MenuItem.addMenu(user, mav); // Sets sidebar and view name
        mav.addObject("content", "flight-from-airplane"); // Sets which fragment to load
        mav.addObject("selectedUrl", "/flights");
        mav.addObject("customJSURL","/flight-from-airplane.js");
        return mav;
    }

    @GetMapping("/{id}")
    public ModelAndView flightView(@PathVariable("id") Integer id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDao.findByUsername(auth.getName()).orElseThrow(()->new AccessDeniedException("User not authenticated", AccessDeniedCode.NOT_SIGNED_IN));
        ModelAndView mav = new ModelAndView();
        mav = MenuItem.addMenu(user, mav); // Sets sidebar and view name
        mav.addObject("content", "flight-from-airplane"); // Sets which fragment to load
        mav.addObject("selectedUrl", "/flights");
        mav.addObject("customJSURL","/flight-from-airplane.js");
        return mav;
    }
}
