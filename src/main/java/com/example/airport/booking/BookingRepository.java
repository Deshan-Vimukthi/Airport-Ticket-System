package com.example.airport.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query("SELECT b FROM Booking b WHERE b.id = ?1")
    Optional<Booking> getBookingById(Integer id);

    @Query("SELECT b FROM Booking b WHERE b.user.id = ?1")
    List<Booking> getBookingListByUserId(Integer id);

}
