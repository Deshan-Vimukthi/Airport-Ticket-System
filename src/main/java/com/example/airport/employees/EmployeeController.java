package com.example.airport.employees;

import com.example.airport.StringModify;
import com.example.airport.employeeStatus.EmployeeStatusRepository;
import com.example.airport.userRole.UserRoleRepository;
import com.example.airport.userStatus.UserStatusRepository;
import com.example.airport.users.User;
import com.example.airport.users.UserRepository;
import com.sun.jdi.request.InvalidRequestStateException;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeDao;

    @Autowired
    private EmployeeStatusRepository employeeStatusDao;

    @Autowired
    private UserRepository userDao;

    @Autowired
    private UserStatusRepository userStatusDao;

    @Autowired
    private UserRoleRepository userRoleDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/list/data")
    public List<Employee> getEmployeeList(){
        return employeeDao.findAll();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/data/id")
    public Employee getEmployee(@RequestParam("id") Integer id){
        return employeeDao.getEmployeeById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create/save")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeRequest employeeRequest) throws BadRequestException {
        if(employeeRequest.getEmployee() == null || employeeRequest.getUser() == null) throw new BadRequestException("Enter valid employee and user details.");

        Employee employee = employeeRequest.getEmployee();

        employee.setJoinDate(LocalDate.now());
        employee.setResignDate(null);
        employee.setUpdatedDate(null);
        int employeeMaxId = 0;
        employee.setCode("EMP/"+LocalDate.now().getYear()+"/"+ StringModify.padStart(0,7,'0'));
        employee.setStatus(employeeStatusDao.getEmployeeStatusById(1));

        employee = employeeDao.save(employee);

        User user = employeeRequest.getUser();

        user.setCreateDate(LocalDateTime.now());
        user.setDeletedDate(null);
        user.setUpdatedDate(null);
        user.setEmployee(employee);
        user.setCustomer(null);
        user.setRole(userRoleDao.getUserRoleById(2));
        user.setUserStatus(userStatusDao.getUserStatusById(1));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        userDao.save(user);

        return ResponseEntity.ok("Employee Created Successfully");


    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update/save")
    public ResponseEntity<?> updateEmployee(@RequestBody Employee employee){
        employee.setUpdatedDate(LocalTime.now());
        employeeDao.save(employee);

        return ResponseEntity.ok("Employee was updated successfully");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/approve-leave/id")
    public ResponseEntity<?> approveLeave(@RequestParam("id") Integer id){
        Employee employee = employeeDao.getEmployeeById(id);
        if(employee == null) throw new EntityNotFoundException("Employee not found");

        if(employee.getStatus()== null || employee.getStatus().getId() != 3){
            employee.setStatus(employeeStatusDao.getEmployeeStatusById(2));
            employeeDao.save(employee);
            return ResponseEntity.ok("Employee leave approved");
        }else {
            throw new InvalidRequestStateException("Employee was deactivated!");
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/end-leave/id")
    public ResponseEntity<?> endLeave(@RequestParam("id")Integer id){
        Employee employee = employeeDao.getEmployeeById(id);
        if(employee == null) throw new EntityNotFoundException("Employee not found");

        if(employee.getStatus()== null || employee.getStatus().getId() != 3){
            employee.setStatus(employeeStatusDao.getEmployeeStatusById(1));
            employeeDao.save(employee);
            return ResponseEntity.ok("Employee leave ended successfully");
        }else {
            throw new InvalidRequestStateException("Employee was deactivated!");
        }
    }



}
