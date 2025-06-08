package com.example.airport.userRole;

import com.example.airport.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user-role")
public class UserRoleController {
    @Autowired
    private UserRoleRepository userRoleDao;

    @GetMapping("")
    public ResponseEntity<?> userRoleList(){
        List<UserRole> userRoleList = userRoleDao.findAll();
        return ResponseEntity.ok(ApiResponse.success(userRoleList));
    }
}
