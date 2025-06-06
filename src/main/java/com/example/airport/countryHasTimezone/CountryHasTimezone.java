package com.example.airport.countryHasTimezone;

import com.example.airport.countries.Country;
import com.example.airport.timezones.Timezone;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "country_has_timezone")

@NoArgsConstructor
@AllArgsConstructor
public class CountryHasTimezone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "country_id",referencedColumnName = "id")
    private Country countryId;

    @ManyToOne
    @JoinColumn(name = "timezone_id",referencedColumnName = "id")
    private Timezone timezoneId;
}
