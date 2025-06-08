package com.example.airport.seatLayout;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "seat_layouts")
@Entity
@Data

@NoArgsConstructor
@AllArgsConstructor
public class SeatLayout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Transient
    @JsonProperty("rowSize")
    public int getRowSize(){
        int count = 0;
        String[] seatGroups = name.split("-");
        for(String group:seatGroups){
            count+=Integer.parseInt(group);
        }
        return count;
    }
}
