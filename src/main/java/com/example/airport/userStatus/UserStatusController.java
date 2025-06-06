package com.example.airport.userStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user-status")
public class UserStatusController {
    @Autowired
    private UserStatusRepository userStatusDao;

    @GetMapping("/list/data")
    public List<UserStatus> getUserStatusList(){
        return userStatusDao.findAll();
    }

    @GetMapping("/list/data/except-delete")
    public List<UserStatus> getStatusExceptDelete(){
        return userStatusDao.getUserStatusListWithoutDelete();
    }
}
