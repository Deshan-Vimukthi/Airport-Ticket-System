package com.example.airport.employeeStatus;

import com.example.airport.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employee-status")
public class EmployeeStatusController {
    @Autowired
    private EmployeeStatusRepository employeeStatusDao;

    @GetMapping("")
    public ResponseEntity<?> getEmployeeStatusList(){
        List<EmployeeStatus> employeeStatusList = employeeStatusDao.findAll();
        return ResponseEntity.ok(ApiResponse.success(employeeStatusList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeStatus(@RequestParam("id") Integer id){
        EmployeeStatus employeeStatus = employeeStatusDao.getEmployeeStatusById(id);
        return ResponseEntity.ok(ApiResponse.success(employeeStatus));
    }
}
