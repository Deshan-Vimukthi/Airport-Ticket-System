package com.example.airport.bannedCustomers;

import com.example.airport.countries.Country;
import com.example.airport.customers.Customer;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "banned_customers")
@Entity
@Data

@NoArgsConstructor
@AllArgsConstructor
public class BannedCustomers {
    @Id
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    private Customer customerId;

    @ManyToOne
    @JoinColumn(name = "country_id",referencedColumnName = "id")
    private Country countryId;

    @Column(name = "reason")
    private String reason;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "last_date")
    private LocalDateTime endDate;

    @Transient
    @JsonProperty("valid")
    public boolean isValid() {
        LocalDateTime now = LocalDateTime.now();
        return createdDate != null && now.isAfter(createdDate) && (endDate == null || now.isBefore(endDate));
    }
}
