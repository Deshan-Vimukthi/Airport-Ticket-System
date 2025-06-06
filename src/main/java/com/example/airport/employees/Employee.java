package com.example.airport.employees;

import com.example.airport.employeeStatus.EmployeeStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Table(name = "employees")
@Entity
@Data

@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "emp_code")
    private String code;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private EmployeeStatus status;

    @Column(name = "join_date")
    private LocalDate joinDate;

    @Column(name = "resign_date")
    private LocalDate resignDate;

    @Column(name = "updated_date")
    private LocalTime updatedDate;
}
