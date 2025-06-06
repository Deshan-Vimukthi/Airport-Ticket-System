package com.example.airport.bannedCustomers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BannedCustomersRepository extends JpaRepository<BannedCustomers,Integer> {

    @Query("SELECT b FROM BannedCustomers b WHERE b.id = ?1")
    BannedCustomers getBannedCustomersById(Integer id);

    @Query("SELECT b FROM BannedCustomers b WHERE b.customerId.id = ?1")
    List<BannedCustomers> getBannedCustomersByCustomerId(Integer customerId);

    @Query("SELECT b FROM BannedCustomers b WHERE b.countryId.id = ?1")
    List<BannedCustomers> getBannedCustomersByCountryId(Integer countryId);

    @Query("SELECT b FROM BannedCustomers b WHERE b.customerId.id = ?1 AND b.countryId.id = ?2")
    BannedCustomers getBannedCustomersByCustomerAndCountryId(Integer customerId,Integer countryId);



}
