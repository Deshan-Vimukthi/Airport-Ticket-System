package com.example.airport.airplane;

import com.example.airport.StringModify;
import com.example.airport.countries.CountryRepository;
import com.example.airport.exceptionHandling.AccessDeniedException;
import com.example.airport.models.AirplaneModelsRepository;
import com.example.airport.webConfigaration.RoleViewResolver;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/airplane")
public class AirplaneController {

    @Autowired
    private AirplaneRepository airplaneDao;

    @Autowired
    private AirplaneModelsRepository airplaneModelsDao;

    @Autowired
    private CountryRepository countryDao;

    @GetMapping("/list/data")
    public MappingJacksonValue getAllAirplanes(){
        List<Airplane> airplaneList = airplaneDao.findAll();
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(airplaneList);
        mappingJacksonValue.setSerializationView(RoleViewResolver.resolveJsonView());
        return mappingJacksonValue;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'OPERATOR')")
    @GetMapping("/list/id")
    public ModelAndView getListUI(@RequestParam("id") Integer id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view-airplane.html");
        return modelAndView;
    }

    @GetMapping("/data/id")
    public MappingJacksonValue getAirplaneData(@RequestParam("id") Integer id) {
        Airplane airplane = airplaneDao.getAirplaneById(id);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(airplane);
        mappingJacksonValue.setSerializationView(RoleViewResolver.resolveJsonView());
        return mappingJacksonValue;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'OPERATOR')")
    @GetMapping("/view/id")
    public ModelAndView getViewUI(@RequestParam("id") Integer id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view-airplane.html");
        return modelAndView;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create/save")
    public ResponseEntity<?> createAirplane(@RequestBody Airplane newAirplane) throws BadRequestException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Can't Create : You're not logged in properly.");
        }
        Integer countOfAirplane = airplaneDao.getAirplaneCountOfModelInCountry(newAirplane.getCountry().getId(),newAirplane.getModel().getId())+1;
        newAirplane.setCreatedDate(LocalDateTime.now());
        newAirplane.setRegistrationNumber(newAirplane.getCountry().getName()+"/"+newAirplane.getModel().getName()+"/"+ StringModify.padStart(countOfAirplane,7,'0'));
        airplaneDao.save(newAirplane);
        return ResponseEntity.ok("Airplane Created Successfully.");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/create")
    public ModelAndView getCreateAirplaneUi(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("create-airplane.html");
        return modelAndView;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update/save")
    public ResponseEntity<?> updateAirplane(@RequestBody Airplane newAirplane,@RequestParam(name = "id",required = false) Integer id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated()) throw new AccessDeniedException("You're not authenticated!");

        if(newAirplane.getId() == null && id == null){
            Integer countOfAirplane = airplaneDao.getAirplaneCountOfModelInCountry(newAirplane.getCountry().getId(),newAirplane.getModel().getId())+1;
            newAirplane.setRegistrationNumber(newAirplane.getCountry().getName()+"/"+newAirplane.getModel().getName()+"/"+ StringModify.padStart(countOfAirplane,7,'0'));
        }
        newAirplane.setUpdatedDate(LocalDateTime.now());
        airplaneDao.save(newAirplane);
        return ResponseEntity.ok("Airplane updated successfully!");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/update/id")
    public ModelAndView getUpdateAirplaneUi(@RequestParam("id") Integer id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("create-airplane.html");
        return modelAndView;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/id")
    public ResponseEntity<?> createAirplane(@RequestParam("id") Integer id){
        Airplane airplane = airplaneDao.getAirplaneById(id);
        airplane.setDeletedDate(LocalDateTime.now());
        airplaneDao.save(airplane);
        return ResponseEntity.ok("Airplane was deleted");
    }
}
