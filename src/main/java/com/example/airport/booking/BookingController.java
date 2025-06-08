package com.example.airport.booking;

import com.example.airport.ApiResponse;
import com.example.airport.airport.Airport;
import com.example.airport.bookingStatus.BookingStatus;
import com.example.airport.bookingStatus.BookingStatusRepository;
import com.example.airport.exceptionHandling.AccessControlAnnotaion.HasAuthority;
import com.example.airport.exceptionHandling.ResourceNotFoundException;
import com.example.airport.exceptionHandling.accessDeniedException.AccessDeniedCode;
import com.example.airport.exceptionHandling.accessDeniedException.AccessDeniedException;
import com.example.airport.invoices.Invoice;
import com.example.airport.invoices.InvoiceRepository;
import com.example.airport.paymentMethod.PaymentMethod;
import com.example.airport.paymentMethod.PaymentMethodRepository;
import com.example.airport.userRole.UserRole;
import com.example.airport.users.User;
import com.example.airport.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/bookings")
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

    @HasAuthority(value = UserRole.RoleEnum.CUSTOMER,enablePriorityOrder = true)
    @PostMapping("")
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
        return ResponseEntity.ok(ApiResponse.success("Booking created successfully",invoice));
    }

    @HasAuthority(value = UserRole.RoleEnum.CUSTOMER,enablePriorityOrder = true)
    @GetMapping("/my-booking")
    public ResponseEntity<?> myBookingList(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDao.findByUsername(auth.getName()).orElseThrow(
                ()-> new AccessDeniedException("", AccessDeniedCode.NOT_SIGNED_IN));

        List<Booking> bookings = bookingDao.getBookingListByUserId(user.getId());
        return ResponseEntity.ok(ApiResponse.success(bookings));
    }

    @HasAuthority(value = UserRole.RoleEnum.OPERATOR,enablePriorityOrder = true)
    @GetMapping("")
    public ResponseEntity<?> bookingList(){
        List<Booking> bookingList = bookingDao.findAll();
        return ResponseEntity.ok(ApiResponse.success(bookingList));
    }

    @HasAuthority(value = UserRole.RoleEnum.CUSTOMER,enablePriorityOrder = true)
    @GetMapping("/{id}")
    public ResponseEntity<?> bookingData(@PathVariable Integer id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDao.findByUsername(auth.getName()).orElseThrow(()-> new AccessDeniedException("User not found",AccessDeniedCode.NOT_SIGNED_IN));
        Booking booking = bookingDao.getBookingById(id).orElseThrow(()->new ResourceNotFoundException("Booking not founded"));
        if(user.getRole().getId() == 3 && !Objects.equals(booking.getUser().getId(), user.getId())){
            throw new AccessDeniedException("Authentication failed",AccessDeniedCode.ROLE_DENIED);
        }
        return ResponseEntity.ok(ApiResponse.success(booking));
    }

    @HasAuthority(value = UserRole.RoleEnum.CUSTOMER,enablePriorityOrder = true)
    @PutMapping("/cancel/{id}")
    public ResponseEntity<?> cancelBooking(@PathVariable("id") Integer id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDao.findByUsername(auth.getName()).orElseThrow(()->new AccessDeniedException("Sign in or Sign up",AccessDeniedCode.NOT_SIGNED_IN));
        Booking booking = bookingDao.getBookingById(id).orElseThrow(()->new ResourceNotFoundException("Booking not founded"));
        if(user.getRole().getId() == 3 && !Objects.equals(booking.getUser().getId(), user.getId())){
            throw new AccessDeniedException("Authentication failed",AccessDeniedCode.ROLE_DENIED);
        }
        booking.setBookingStatus(bookingStatusDao.getBookingStatusById(3));


        bookingDao.save(booking);
        return ResponseEntity.ok(ApiResponse.success("Booking cancel successfully"));
    }

}
