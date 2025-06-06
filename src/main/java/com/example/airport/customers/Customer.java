package com.example.airport.customers;

import com.example.airport.travelStatus.TravelStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Data

@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name="code")
    private String code;

    @Column(name = "passport_number")
    private String passport_number;

    @Column(name = "email")
    private String email;

    @Column(name="phone")
    private String phone;

    @ManyToOne
    @JoinColumn(name = "status_id",referencedColumnName = "id")
    private TravelStatus status;

    @Column(name = "join_date")
    private LocalDateTime joinDate;

    @Column(name = "resign_date")
    private LocalDateTime resignDate;

    @Column(name = "updatedDate")
    private LocalDateTime updatedDate;

}
