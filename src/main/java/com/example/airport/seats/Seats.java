package com.example.airport.seats;

import com.example.airport.availability.Availability;
import com.example.airport.flight.Flight;
import com.example.airport.seatClasses.SeatClasses;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Table(name = "seats")
@Entity
@Data

@NoArgsConstructor
@AllArgsConstructor
public class Seats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "row_id")
    private Integer rowId;

    @Column(name = "column_id")
    private Integer columnId;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private SeatClasses seatClasses;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @ManyToOne
    @JoinColumn(name = "availability_id")
    private Availability availability;

    public boolean isInRange(Seats start, Seats end) {
        if (start == null || end == null) return false;

        // Normalize the range
        Seats from = start;
        Seats to = end;

        if (compareSeats(start, end) > 0) {
            from = end;
            to = start;
        }

        // Check if current seat is between from and to
        return compareSeats(this, from) >= 0 && compareSeats(this, to) <= 0;
    }

    // Compares seats like strings on column then numberically on row
    private int compareSeats(Seats a, Seats b) {
        if (!Objects.equals(a.columnId, b.columnId)) {
            return Integer.compare(a.columnId, b.columnId);
        }
        return Integer.compare(a.rowId, b.rowId);
    }


}