package com.example.airport.userStatus;

import com.example.airport.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user-status")
public class UserStatusController {
    @Autowired
    private UserStatusRepository userStatusDao;

    @GetMapping("")
    public ResponseEntity<?> getUserStatusList(){
        List<UserStatus> userStatusList = userStatusDao.findAll();
        return ResponseEntity.ok(ApiResponse.success(userStatusList));
    }

    @GetMapping("/except-delete")
    public ResponseEntity<?> getStatusExceptDelete(){
        List<UserStatus> userStatusList = userStatusDao.getUserStatusListWithoutDelete();
        return ResponseEntity.ok(ApiResponse.success(userStatusList));
    }
}
