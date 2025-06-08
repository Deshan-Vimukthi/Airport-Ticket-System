package com.example.airport.bannedCustomers;

import com.example.airport.exceptionHandling.AccessControlAnnotaion.HasAuthority;
import com.example.airport.userRole.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/banned-customer")
public class BannedCustomerController {
    @Autowired
    private BannedCustomersRepository bannedCustomersDao;

    @GetMapping("/")
    @HasAuthority(value =UserRole.RoleEnum.ADMIN, enablePriorityOrder = true)
    public List<BannedCustomers> getBannedCustomersList(@RequestParam(name = "valid", required = false, defaultValue = "false") boolean isValid) {
        List<BannedCustomers> bannedCustomers = bannedCustomersDao.findAll();
        return isValid?bannedCustomers.stream().filter(BannedCustomers::isValid).toList():bannedCustomers;
    }

    @GetMapping("/{id}")
    @HasAuthority(value =UserRole.RoleEnum.ADMIN, enablePriorityOrder = true)
    public BannedCustomers getBannedCustomersById(@PathVariable(name = "id") Integer id) {
        return bannedCustomersDao.getBannedCustomersById(id);
    }

    @GetMapping("/customer/{id}")
    @HasAuthority(value =UserRole.RoleEnum.ADMIN, enablePriorityOrder = true)
    public List<BannedCustomers> getBannedCustomersListByCustomerId(@PathVariable("id") Integer id ,@RequestParam(name = "valid", required = false, defaultValue = "false") boolean isValid) {
        List<BannedCustomers> bannedCustomers = bannedCustomersDao.getBannedCustomersByCustomerId(id);
        return isValid?bannedCustomers.stream().filter(BannedCustomers::isValid).toList():bannedCustomers;
    }

    @GetMapping("/country/{id}")
    @HasAuthority(value =UserRole.RoleEnum.ADMIN, enablePriorityOrder = true)
    public List<BannedCustomers> getBannedCustomersListByCountryId(@PathVariable("id") Integer id ,@RequestParam(name = "valid", required = false, defaultValue = "false") boolean isValid) {
        List<BannedCustomers> bannedCustomers = bannedCustomersDao.getBannedCustomersByCountryId(id);
        return isValid?bannedCustomers.stream().filter(BannedCustomers::isValid).toList():bannedCustomers;
    }

    @GetMapping("/id")
    @HasAuthority(value =UserRole.RoleEnum.ADMIN, enablePriorityOrder = true)
    public BannedCustomers getBannedCustomersListByCountryIdAndCustomerId(@RequestParam("customerId") Integer customerId,@RequestParam("countryId") Integer countryId  ) {
        return bannedCustomersDao.getBannedCustomersByCustomerAndCountryId(customerId, countryId);
    }

    @GetMapping("/is-blocked")
    @HasAuthority(value =UserRole.RoleEnum.CUSTOMER, enablePriorityOrder = true)
    public ResponseEntity<Boolean> checkIsBlocked(@RequestParam("customerId") Integer customerId,@RequestParam("countryId") Integer countryId){
        BannedCustomers bannedCustomers = bannedCustomersDao.getBannedCustomersByCustomerAndCountryId(customerId, countryId);
        return ResponseEntity.ok(bannedCustomers.isValid());
    }
}
