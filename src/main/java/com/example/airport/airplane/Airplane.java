package com.example.airport.airplane;

import com.example.airport.View;
import com.example.airport.countries.Country;
import com.example.airport.models.AirplaneModels;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "airplanes")
@Data

@NoArgsConstructor
@AllArgsConstructor
public class Airplane {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({View.Public.class,View.Admin.class,View.Operator.class,View.Customer.class})
    private Integer id;

    @Column(name = "name")
    @JsonView({View.Public.class,View.Admin.class,View.Operator.class,View.Customer.class})
    private String name;

    @ManyToOne
    @JoinColumn(name = "model_id")
    @JsonView({View.Admin.class,View.Operator.class,View.Customer.class})
    private AirplaneModels model;

    @Column(name = "manufacturer")
    @JsonView({View.Admin.class,View.Operator.class})
    private String manufacture;

    @Column(name = "seat_row_capacity")
    @JsonView({View.Admin.class,View.Operator.class,View.Customer.class})
    private Integer rowCount;

    @ManyToOne
    @JoinColumn(name = "country_id")
    @JsonView(View.Admin.class)
    private Country country;

    @Column(name = "airline_name")
    @JsonView({View.Admin.class,View.Operator.class,View.Customer.class})
    private String airlineName;

    @Column(name = "registration_number")
    @JsonView(View.Admin.class)
    private String registrationNumber;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;
}
