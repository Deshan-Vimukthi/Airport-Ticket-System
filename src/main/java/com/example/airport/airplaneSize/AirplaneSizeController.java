package com.example.airport.airplaneSize;

import com.example.airport.exceptionHandling.AccessControlAnnotaion.HasAuthority;
import com.example.airport.userRole.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/airplane-sizes")
public class AirplaneSizeController {

    @Autowired
    private AirplaneSizeRepository airplaneSizeDao;

    @HasAuthority({UserRole.RoleEnum.ADMIN, UserRole.RoleEnum.CUSTOMER, UserRole.RoleEnum.OPERATOR})
    @GetMapping(value = "")
    public List<AirplaneSize> getAirplaneList(){
        return airplaneSizeDao.findAll();
    }
}
