package com.example.airport.airplane;

import com.example.airport.StringModify;
import com.example.airport.countries.CountryRepository;
import com.example.airport.exceptionHandling.AccessControlAnnotaion.HasAuthority;
import com.example.airport.models.AirplaneModelsRepository;
import com.example.airport.userRole.UserRole;
import com.example.airport.webConfigaration.RoleViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/airplanes")
public class AirplaneApiController {

    @Autowired
    private AirplaneRepository airplaneDao;

    @Autowired
    private AirplaneModelsRepository airplaneModelsDao;

    @Autowired
    private CountryRepository countryDao;

    @GetMapping("")
    public ResponseEntity<?> airplaneList(){
        List<Airplane> airplaneList = airplaneDao.findAll();
        MappingJacksonValue result = new MappingJacksonValue(airplaneList);
        result.setSerializationView(RoleViewResolver.resolveJsonView());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAirplaneData(@PathVariable Integer id) {
        Airplane airplane = airplaneDao.getAirplaneById(id);
        if (airplane == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Airplane not found");
        }
        MappingJacksonValue result = new MappingJacksonValue(airplane);
        result.setSerializationView(RoleViewResolver.resolveJsonView());
        return ResponseEntity.ok(result);
    }

    @PostMapping("")
    public ResponseEntity<?> createAirplane(@RequestBody Airplane newAirplane) {
        Integer count = airplaneDao.getAirplaneCountOfModelInCountry(
                newAirplane.getCountry().getId(),
                newAirplane.getModel().getId()) + 1;

        newAirplane.setCreatedDate(LocalDateTime.now());
        newAirplane.setRegistrationNumber(newAirplane.getCountry().getName() + "/" +
                newAirplane.getModel().getName() + "/" + StringModify.padStart(count, 7, '0'));

        airplaneDao.save(newAirplane);
        return ResponseEntity.ok(Map.of("message", "Airplane created successfully."));
    }

    @HasAuthority(UserRole.RoleEnum.ADMIN)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAirplane(@RequestBody Airplane newAirplane,
                                            @PathVariable Integer id) {
        Airplane existing = airplaneDao.getAirplaneById(id);
        if (existing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Airplane not found");
        }

        newAirplane.setId(id);
        newAirplane.setCreatedDate(existing.getCreatedDate()); // keep original
        newAirplane.setUpdatedDate(LocalDateTime.now());
        airplaneDao.save(newAirplane);
        return ResponseEntity.ok(Map.of("message", "Airplane updated successfully."));
    }
    @HasAuthority(UserRole.RoleEnum.ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAirplane(@PathVariable("id") Integer id){
        Airplane airplane = airplaneDao.getAirplaneById(id);
        if (airplane == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Airplane not found");
        }
        airplane.setDeletedDate(LocalDateTime.now());
        airplaneDao.save(airplane);
        return ResponseEntity.ok(Map.of("message", "Airplane was deleted"));
    }
}
