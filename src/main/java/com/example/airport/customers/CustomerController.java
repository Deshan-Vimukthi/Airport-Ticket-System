package com.example.airport.customers;

import com.example.airport.travelStatus.TravelStatusRepository;
import com.example.airport.userRole.UserRoleRepository;
import com.example.airport.userStatus.UserStatusRepository;
import com.example.airport.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @PreAuthorize("hasAuthority('ADMIN','OPERATOR')")
    @GetMapping("/list/data")
    public List<Customer> getCustomerList(){
        return customerDao.findAll();
    }

    @PreAuthorize("hasAthourity('ADMIN')")
    @GetMapping("/data/id")
    public Customer getCustomer(@RequestParam("id") Integer id){
        return customerDao.getReferenceById(id);
    }
}
