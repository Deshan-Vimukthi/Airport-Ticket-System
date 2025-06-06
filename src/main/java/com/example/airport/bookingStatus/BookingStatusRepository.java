package com.example.airport.bookingStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookingStatusRepository extends JpaRepository<BookingStatus, Integer> {
    @Query("SELECT bs FROM BookingStatus bs WHERE bs.id = ?1")
    BookingStatus getBookingStatusById(Integer id);
}
