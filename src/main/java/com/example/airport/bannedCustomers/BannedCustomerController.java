package com.example.airport.bannedCustomers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/banned-customer")
public class BannedCustomerController {
    @Autowired
    private BannedCustomersRepository bannedCustomersDao;

    @GetMapping("/list/data")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OPERATION')")
    public List<BannedCustomers> getBannedCustomersList(@RequestParam(name = "valid", required = false, defaultValue = "false") boolean isValid) {
        List<BannedCustomers> bannedCustomers = bannedCustomersDao.findAll();
        return isValid?bannedCustomers.stream().filter(BannedCustomers::isValid).toList():bannedCustomers;
    }

    @GetMapping("/data/id")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OPERATION')")
    public BannedCustomers getBannedCustomersById(@RequestParam(name = "id") Integer id) {
        return bannedCustomersDao.getBannedCustomersById(id);
    }

    @GetMapping("/list/data/customer/id")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OPERATION')")
    public List<BannedCustomers> getBannedCustomersListByCustomerId(@RequestParam("id") Integer id ,@RequestParam(name = "valid", required = false, defaultValue = "false") boolean isValid) {
        List<BannedCustomers> bannedCustomers = bannedCustomersDao.getBannedCustomersByCustomerId(id);
        return isValid?bannedCustomers.stream().filter(BannedCustomers::isValid).toList():bannedCustomers;
    }

    @GetMapping("/list/data/country/id")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OPERATION')")
    public List<BannedCustomers> getBannedCustomersListByCountryId(@RequestParam("id") Integer id ,@RequestParam(name = "valid", required = false, defaultValue = "false") boolean isValid) {
        List<BannedCustomers> bannedCustomers = bannedCustomersDao.getBannedCustomersByCountryId(id);
        return isValid?bannedCustomers.stream().filter(BannedCustomers::isValid).toList():bannedCustomers;
    }

    @GetMapping("/list/data/all/id")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OPERATION')")
    public BannedCustomers getBannedCustomersListByCountryIdAndCustomerId(@RequestParam("customerId") Integer customerId,@RequestParam("countryId") Integer countryId  ) {
        return bannedCustomersDao.getBannedCustomersByCustomerAndCountryId(customerId, countryId);
    }

    @GetMapping("/is-blocked")
    public ResponseEntity<Boolean> checkIsBlocked(@RequestParam("customerId") Integer customerId,@RequestParam("countryId") Integer countryId){
        BannedCustomers bannedCustomers = bannedCustomersDao.getBannedCustomersByCustomerAndCountryId(customerId, countryId);
        return ResponseEntity.ok(bannedCustomers.isValid());
    }
}
