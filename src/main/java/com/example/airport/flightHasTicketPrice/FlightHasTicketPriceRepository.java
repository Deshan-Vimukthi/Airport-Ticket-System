package com.example.airport.flightHasTicketPrice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FlightHasTicketPriceRepository extends JpaRepository<FlightHasTicketPrice, Long> {
    @Query("SELECT ft FROM FlightHasTicketPrice ft WHERE ft.flight_id.id = ?1")
    List<FlightHasTicketPrice> getListByFlightId(Integer id);

    @Query("SELECT ft FROM FlightHasTicketPrice ft WHERE ft.id = ?1")
    Optional<FlightHasTicketPrice> getFlightTicketPriceById(Integer id);
    @Query("SELECT ft FROM FlightHasTicketPrice ft WHERE ft.flight_id.id =?1 AND ft.class_id.id =?2")
    Optional<FlightHasTicketPrice> getFlightTicketPriceByFlightAndClass(Integer flightId, Integer classId);
}
