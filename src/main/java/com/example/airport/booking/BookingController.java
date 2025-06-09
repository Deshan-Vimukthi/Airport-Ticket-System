package com.example.airport.booking;

import com.example.airport.ApiResponse;
import com.example.airport.airport.Airport;
import com.example.airport.availability.AvailabilityRepository;
import com.example.airport.bookingStatus.BookingStatus;
import com.example.airport.bookingStatus.BookingStatusRepository;
import com.example.airport.exceptionHandling.AccessControlAnnotaion.HasAuthority;
import com.example.airport.exceptionHandling.ResourceNotFoundException;
import com.example.airport.exceptionHandling.accessDeniedException.AccessDeniedCode;
import com.example.airport.exceptionHandling.accessDeniedException.AccessDeniedException;
import com.example.airport.flightHasTicketPrice.FlightHasTicketPriceController;
import com.example.airport.flightHasTicketPrice.FlightHasTicketPriceRepository;
import com.example.airport.invoices.Invoice;
import com.example.airport.invoices.InvoiceRepository;
import com.example.airport.paymentMethod.PaymentMethod;
import com.example.airport.paymentMethod.PaymentMethodRepository;
import com.example.airport.paymentType.PaymentTypeRepository;
import com.example.airport.payments.Payment;
import com.example.airport.payments.PaymentRepository;
import com.example.airport.seats.Seats;
import com.example.airport.seats.SeatsRepository;
import com.example.airport.userRole.UserRole;
import com.example.airport.users.User;
import com.example.airport.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private SeatsRepository seatsDao;

    @Autowired
    private AvailabilityRepository availabilityDao;

    @Autowired
    private FlightHasTicketPriceRepository flightHasTicketPriceDao;

    @Autowired
    private PaymentRepository paymentDao;

    @Autowired
    private PaymentTypeRepository paymentTypeDao;

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

            Seats seat = seatsDao.getSeatById(booking.getSeat().getId()).orElseThrow(()->new ResourceNotFoundException("seat not found"));
            seat.setAvailability(availabilityDao.getReferenceById(2));
            seatsDao.save(seat);

            Payment payment = new Payment();
            payment.setAmount(flightHasTicketPriceDao.getFlightTicketPriceByFlightAndClass(booking.getSeat().getFlight().getId(),booking.getSeat().getSeatClasses().getId()).orElseThrow(()->new ResourceNotFoundException("Seat haven't any value")).getPrice());
            payment.setPaymentType(paymentTypeDao.getReferenceById(1));
            payment.setReason("Booking : " + booking.getSeat().getColumnId() + "_" + booking.getSeat().getRowId());
            payment.setPaymentMethod(invoice.getPaymentMethod());
            payment.setInvoice(invoice);

            paymentDao.save(payment);
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

    @HasAuthority(value = UserRole.RoleEnum.OPERATOR,enablePriorityOrder = true)
    @GetMapping("/flight/{id}")
    public ResponseEntity<?> bookingListByFlight(@PathVariable("id") Integer id){
        List<Booking> bookings = bookingDao.getBookingByFlight(id);
        if(!bookings.isEmpty())
            return ResponseEntity.ok(ApiResponse.success(bookings));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Flight have not bookings"));
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

        Seats seat = seatsDao.getSeatById(booking.getSeat().getId()).orElseThrow(()->new ResourceNotFoundException("seat not found"));
        seat.setAvailability(availabilityDao.getReferenceById(1));
        seatsDao.save(seat);

        Payment payment = new Payment();
        payment.setAmount(flightHasTicketPriceDao.getFlightTicketPriceByFlightAndClass(booking.getSeat().getFlight().getId(),booking.getSeat().getSeatClasses().getId()).orElseThrow(()->new ResourceNotFoundException("Seat haven't any value")).getPrice());
        payment.setPaymentType(paymentTypeDao.getReferenceById(4));
        payment.setReason("Cancelled : " + booking.getSeat().getColumnId() + "_" + booking.getSeat().getRowId());
        payment.setPaymentMethod(booking.getInvoice().getPaymentMethod());
        payment.setInvoice(booking.getInvoice());

        paymentDao.save(payment);
        bookingDao.save(booking);
        return ResponseEntity.ok(ApiResponse.success("Booking cancel successfully"));
    }

}
