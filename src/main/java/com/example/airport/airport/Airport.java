package com.example.airport.airport;

import com.example.airport.countryHasTimezone.CountryHasTimezone;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "airports")
@Entity
@Data

@NoArgsConstructor
@AllArgsConstructor
public class Airport {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @ManyToOne
    @JoinColumn(name = "country_and_time_id")
    private CountryHasTimezone countryHasTimeZone;
}
