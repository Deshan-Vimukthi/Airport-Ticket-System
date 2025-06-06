package com.example.airport.userRole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user-role")
public class UserRoleController {
    @Autowired
    private UserRoleRepository userRoleDao;

    @GetMapping("/list/data")
    public List<UserRole> userRoleList(){
        return userRoleDao.findAll();
    }
}
