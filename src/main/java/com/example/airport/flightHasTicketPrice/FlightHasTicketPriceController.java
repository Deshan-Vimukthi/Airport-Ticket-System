package com.example.airport.flightHasTicketPrice;

import com.example.airport.ApiResponse;
import com.example.airport.exceptionHandling.AccessControlAnnotaion.HasAuthority;
import com.example.airport.exceptionHandling.ResourceNotFoundException;
import com.example.airport.userRole.UserRole;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flight-ticket-price")
public class FlightHasTicketPriceController {
    @Autowired
    private FlightHasTicketPriceRepository flightHasTicketPriceDao;

    @GetMapping("")
    public ResponseEntity<?> getFlightTicketPriceList(){
        return ResponseEntity.ok(ApiResponse.success(flightHasTicketPriceDao.findAll()));
    }

    @GetMapping("/flight/{id}")
    public ResponseEntity<?> getFlightTicketPriceListByFlight(@PathVariable("id") Integer id){
        return ResponseEntity.ok(ApiResponse.success(flightHasTicketPriceDao.getListByFlightId(id)));
    }

    @GetMapping("/flight/{flight-id}/class/{class-id}")
    public ResponseEntity<?> getFlightTicketPriceByFlightAndClass(@PathVariable("flight-id")Integer flightId,@PathVariable("class-id") Integer classId){
        return ResponseEntity.ok(
                ApiResponse.success(
                        flightHasTicketPriceDao.getFlightTicketPriceByFlightAndClass(flightId,classId)
                                .orElseThrow(()->new ResourceNotFoundException("This class in given airplane haven't any allocated ticket price."))
                )
        );
    }

    @HasAuthority(UserRole.RoleEnum.ADMIN)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFlightTicketPriceById(@PathVariable("id") Integer id,@RequestBody Double price){
        FlightHasTicketPrice flightHasTicketPrice = flightHasTicketPriceDao.getFlightTicketPriceById(id).orElseThrow(()-> new ResourceNotFoundException("This id does not have a price allocation"));
        flightHasTicketPrice.setPrice(price);
        flightHasTicketPriceDao.save(flightHasTicketPrice);
        return ResponseEntity.ok(ApiResponse.success("Flight-Class Prices are allocated successfully"));
    }

    @HasAuthority(UserRole.RoleEnum.ADMIN)
    @PutMapping("/flight/{flight-id}/class/{class-id}")
    public ResponseEntity<?> updateFlightTicketPriceByFlightAndClass(@PathVariable("flight-id")Integer flightId,@PathVariable("class-id") Integer classId,@RequestBody Double price){
        FlightHasTicketPrice flightHasTicketPrice = flightHasTicketPriceDao.getFlightTicketPriceByFlightAndClass(flightId,classId).orElseThrow(()-> new ResourceNotFoundException("This id does not have a price allocation"));
        flightHasTicketPrice.setPrice(price);
        flightHasTicketPriceDao.save(flightHasTicketPrice);
        return ResponseEntity.ok(ApiResponse.success("Flight-Class Prices are allocated successfully"));
    }

    @HasAuthority(UserRole.RoleEnum.ADMIN)
    @PostMapping("")
    public ResponseEntity<?> createFlightTicketPrice(@RequestBody FlightHasTicketPrice flightHasTicketPrice) throws BadRequestException {
        if(flightHasTicketPrice.getFlight_id() == null || flightHasTicketPrice.getFlight_id().getId() == null)
            throw new BadRequestException("Flight not valid");

        if(flightHasTicketPrice.getClass_id() == null || flightHasTicketPrice.getClass_id().getId() == null)
            throw new BadRequestException("Ticket Class not valid");

        flightHasTicketPriceDao.save(flightHasTicketPrice);

        return ResponseEntity.ok(ApiResponse.success("Flight ticket price allocated successfully"));
    }



}
