package com.example.airport.airport;

import com.example.airport.ApiResponse;
import com.example.airport.exceptionHandling.AccessControlAnnotaion.HasAuthority;
import com.example.airport.exceptionHandling.ResourceNotFoundException;
import com.example.airport.userRole.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
public class AirportApiController {

    @Autowired
    private AirportRepository airportDao;

    @HasAuthority(UserRole.RoleEnum.ADMIN)
    @PostMapping("")
    public ResponseEntity<?> createAirport(@RequestBody Airport airport) {
        airport.setCode(generateAirportCode(airport));
        airportDao.save(airport);
        return ResponseEntity.ok(ApiResponse.success("Airport added successfully!", airport));
    }

    @HasAuthority(UserRole.RoleEnum.ADMIN)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAirport(@PathVariable Integer id, @RequestBody Airport airport) {
        Airport existing = airportDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found with ID: " + id));

        airport.setId(id);
        airport.setCode(generateAirportCode(airport));
        airportDao.save(airport);

        return ResponseEntity.ok(ApiResponse.success("Airport updated successfully!", airport));
    }

    @GetMapping("")
    public ResponseEntity<?> getAirportList() {
        List<Airport> airportList = airportDao.findAll();
        return ResponseEntity.ok(ApiResponse.success("Airport list retrieved", airportList));
    }

    // Helper method
    private String generateAirportCode(Airport airport) {
        String code = airport.getCode();
        if (code == null || code.isBlank()) {
            return airport.getName().substring(0, 2).toUpperCase();
        } else {
            return (code.length() > 3 ? code.substring(0, 2) : code).toUpperCase();
        }
    }
}

