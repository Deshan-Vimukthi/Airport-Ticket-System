package com.example.airport.employeeStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeStatusRepository extends JpaRepository<EmployeeStatus, Long> {
    @Query("SELECT es FROM EmployeeStatus es WHERE es.id = ?1")
    EmployeeStatus getEmployeeStatusById(Integer id);
}
