package com.example.airport.customers;

import com.example.airport.StringModify;
import com.example.airport.travelStatus.TravelStatus;
import com.example.airport.travelStatus.TravelStatusRepository;
import com.example.airport.userRole.UserRole;
import com.example.airport.userRole.UserRoleRepository;
import com.example.airport.userStatus.UserStatus;
import com.example.airport.userStatus.UserStatusRepository;
import com.example.airport.users.User;
import com.example.airport.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerDao;

    @Autowired
    private TravelStatusRepository travelStatusDao;

    @Autowired
    private UserRepository userDao;

    @Autowired
    private UserStatusRepository userStatusDao;

    @Autowired
    private UserRoleRepository userRoleDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PreAuthorize("hasAuthority('ADMIN','OPERATION_MANAGER')")
    @GetMapping("/list/data")
    public List<Customer> getCustomerList(){
        return customerDao.findAll();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/data/id")
    public Customer getCustomer(@RequestParam("id") Integer id){
        return customerDao.getReferenceById(id);
    }

    @PostMapping("/create/save")
    public ResponseEntity<?> createCustomer(@RequestBody CustomerRequest customerRequest){
        int customerIndex = customerDao.getNextId().orElse(0) + 1;
        TravelStatus travelStatus = travelStatusDao.getTravelStatusById(1);
        UserStatus userStatus = userStatusDao.getUserStatusById(1);
        UserRole role = userRoleDao.getUserRoleById(3);

        Customer customer = customerRequest.getCustomer();
        if(customer.getCode() != null) customer.setCode("EMP/"+ LocalDate.now().getYear()+"/"+ StringModify.padStart(customerIndex,7,'0'));
        customer.setStatus(travelStatus);
        customer.setJoinDate(LocalDateTime.now());
        customer.setResignDate(null);
        customer.setUpdatedDate(null);

        customer = customerDao.save(customer);
        if(customerRequest.getUser() != null){
            User user = customerRequest.getUser();
            user.setUserStatus(userStatus);
            user.setCustomer(customer);
            user.setEmployee(null);
            user.setRole(role);
            user.setCreateDate(LocalDateTime.now());
            user.setDeletedDate(null);
            user.setUpdatedDate(null);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

            userDao.save(user);
        }


        return ResponseEntity.ok("");
    }

    @PostMapping("/update/save")
    public ResponseEntity<?> updateCustomer(@RequestParam Customer customer){
        return ResponseEntity.ok("");
    }
}
