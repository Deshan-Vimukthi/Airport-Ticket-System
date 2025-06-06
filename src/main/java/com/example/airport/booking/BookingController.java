package com.example.airport.booking;

import com.example.airport.bookingStatus.BookingStatus;
import com.example.airport.bookingStatus.BookingStatusRepository;
import com.example.airport.exceptionHandling.AccessDeniedException;
import com.example.airport.invoices.Invoice;
import com.example.airport.invoices.InvoiceRepository;
import com.example.airport.paymentMethod.PaymentMethod;
import com.example.airport.paymentMethod.PaymentMethodRepository;
import com.example.airport.users.User;
import com.example.airport.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingRepository bookingDao;

    @Autowired
    private PaymentMethodRepository paymentMethodDao;

    @Autowired
    private BookingStatusRepository bookingStatusDao;

    @Autowired
    private InvoiceRepository invoiceDao;

    @Autowired
    private UserRepository userDao;

    @PreAuthorize("hasAnyAuthority('ADMIN','OPERATION','CUSTOMER')")
    @GetMapping("/create/save")
    public ResponseEntity<?> createBooking(@RequestBody List<Booking> bookings){
        Invoice invoice = new Invoice();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth.isAuthenticated()) throw new AccessDeniedException("Authentication failed!");
        User user = userDao.findByUsername(auth.getName()).orElse(null);
        PaymentMethod paymentMethod = paymentMethodDao.getPaymentMethodById(1);
        BookingStatus status = bookingStatusDao.getBookingStatusById(1);

        invoice.setPaymentMethod(paymentMethod);
        invoice.setPaymentDateTime(LocalDateTime.now());
        invoice.setPayedAmount(0);

        invoice = invoiceDao.save(invoice);

        for (Booking booking:bookings){
            booking.setInvoice(invoice);
            booking.setBookingStatus(status);
            booking.setUser(user);

            bookingDao.save(booking);
        }
        return ResponseEntity.ok(invoice);
    }







}
