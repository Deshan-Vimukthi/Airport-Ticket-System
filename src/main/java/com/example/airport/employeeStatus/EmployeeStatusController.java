package com.example.airport.employeeStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee-status")
public class EmployeeStatusController {
    @Autowired
    private EmployeeStatusRepository employeeStatusDao;

    @GetMapping("/list/data")
    public List<EmployeeStatus> getEmployeeStatusList(){
        return employeeStatusDao.findAll();
    }

    @GetMapping("/data/id")
    public EmployeeStatus getEmployeeStatus(@RequestParam("id") Integer id){
        return employeeStatusDao.getEmployeeStatusById(id);
    }
}
