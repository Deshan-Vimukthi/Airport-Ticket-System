package com.example.airport.seatHasClass;

import com.example.airport.ApiResponse;
import com.example.airport.exceptionHandling.ResourceNotFoundException;
import com.example.airport.seats.Seats;
import com.example.airport.seats.SeatsRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/seat-class")
public class SeatHasClassController {
    @Autowired
    private SeatHasClassRepository seatHasClassDao;

    @Autowired
    private SeatsRepository seatsDao;

    @GetMapping("/airplane/{id}")
    public ResponseEntity<?> getSeatClassesListByAirplane(@PathVariable("id") Integer id){
        List<SeatHasClass> seatHasClassList = seatHasClassDao.getSeatClassesByAirplaneId(id);
        return ResponseEntity.ok(
                ApiResponse.success(seatHasClassList)
        );
    }

    @GetMapping("/seat/{id}")
    public ResponseEntity<?> getSeatClass(@PathVariable("id") Integer id){
        Seats seat = seatsDao.getSeatById(id).orElseThrow(()->new ResourceNotFoundException("Seat not found"));
        List<SeatHasClass> seatHasClassList = seatHasClassDao.getSeatClassesByAirplaneId(seat.getFlight().getAirplane().getId());
        for(SeatHasClass seatHasClass:seatHasClassList){
            if(seat.isInRange(seatHasClass.getFirstSeatNo(),seatHasClass.getLastSeatNo())){
                return ResponseEntity.ok(ApiResponse.success(seatHasClass.getSeatClasses()));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Class not found.",HttpStatus.NOT_FOUND.toString()));
    }

    @PostMapping("")
    public ResponseEntity<?> seatClassAllocation(@RequestBody SeatHasClass seatHasClass) throws BadRequestException {
        if(seatHasClass.getSeatClasses() == null ||
                seatHasClass.getAirplane() == null ||
                seatHasClass.getSeatLayout() == null
        ) throw new BadRequestException("Missing attribute");

        List<SeatHasClass> seatHasClassList = seatHasClassDao.getSeatClassesByAirplaneId(seatHasClass.getAirplane().getId());
        for(SeatHasClass seatHasClass1:seatHasClassList){
            if(seatHasClass.getFirstSeatNo().isInRange(seatHasClass1.getFirstSeatNo(),seatHasClass1.getLastSeatNo())){
                throw new UnknownError("First seat has a class");
            }
            if(seatHasClass.getLastSeatNo().isInRange(seatHasClass1.getFirstSeatNo(),seatHasClass1.getLastSeatNo())){
                throw new UnknownError("Last seat has a class");
            }
        }

        seatHasClassDao.save(seatHasClass);
        return ResponseEntity.ok(ApiResponse.success("Seat classes allocated successfully!"));
    }

    @PostMapping("/list")
    public ResponseEntity<?> seatClassListAllocation(@RequestBody List<SeatHasClass> seatHasClassList) throws BadRequestException {
        for(SeatHasClass seatHasClass: seatHasClassList){
            ResponseEntity<?> entity = seatClassAllocation(seatHasClass);
        }
        return ResponseEntity.ok(ApiResponse.success("Seat classes allocated successfully!"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSeatClassAllocation(@PathVariable("id") Integer id,@RequestBody SeatHasClass seatHasClass){
        SeatHasClass seatHasClass1 = seatHasClassDao. getSeatHasClassById(id).orElseThrow(()-> new ResourceNotFoundException("Seat class allocation not found"));
        seatHasClass.setId(id);
        seatHasClassDao.save(seatHasClass);
        return ResponseEntity.ok(ApiResponse.success("Seat Class allocation updated"));
    }
}
