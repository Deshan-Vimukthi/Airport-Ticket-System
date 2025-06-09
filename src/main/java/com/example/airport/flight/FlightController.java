package com.example.airport.flight;

import com.example.airport.ApiResponse;
import com.example.airport.airplane.Airplane;
import com.example.airport.flightStatus.FlightStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/flights")
public class FlightController {
    @Autowired
    private FlightRepository flightDao;

    @Autowired
    private FlightStatusRepository flightStatusDao;

    @GetMapping("")
    public ResponseEntity<?> getFlightList(){
        List<Flight> flights = flightDao.findAll();
        return ResponseEntity.ok(ApiResponse.success(flights));
    }

    @GetMapping("/airplane/{id}")
    public ResponseEntity<?> getFlight(@PathVariable("id") Integer id){
        List<Flight> flights = flightDao.getFlightsByAirplane(id);
        return ResponseEntity.ok(ApiResponse.success(flights));
    }

    @GetMapping("/last-destination/airport/{id}")
    public ResponseEntity<?> getAirplanesAvailableInAirport(@PathVariable("id") Integer airportId){

        List<Flight> allFlights = flightDao.findAll();

        // Group flights by airplane
        Map<Airplane, Optional<Flight>> latestFlightsByAirplane = allFlights.stream()
                .collect(Collectors.groupingBy(
                        Flight::getAirplane,
                        Collectors.maxBy(Comparator.comparing(Flight::getDepartureTime))
                ));

        List<Flight> availableLastFlight = latestFlightsByAirplane.values().stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(flight -> flight.getDepartureAirport().getId().equals(airportId))
                .toList();

        List<Airplane> availableAirplanes = availableLastFlight.stream().map(Flight::getAirplane).toList();

        // Filter the final flights whose departure airport matches the given airportId
        return ResponseEntity.ok(ApiResponse.success(availableAirplanes));
    }

    @PostMapping("")
    public ResponseEntity<?> createFlight(@RequestBody Flight flight){
        List<Flight> orderedFlightList = flightDao.findLatestFlightByAirplaneId(flight.getAirplane().getId());
        if(orderedFlightList.isEmpty()) {
            if(!Objects.equals(flight.getAirplane().getCountry().getId(), flight.getDepartureAirport().getCountryHasTimeZone().getCountryId().getId()))
                throw new RuntimeException("Airplane wasn't in that airport");
        }else{
            if(orderedFlightList.get(0).getArriveAirport().getId() != flight.getDepartureAirport().getId())
                throw new RuntimeException("Airplane wasn't in that airport");
        }

        flight.setFlightStatus(flightStatusDao.getFlightStatus(2));
        flight.setReason("");

        flight = flightDao.save(flight);

        return ResponseEntity.ok(ApiResponse.success("Flight created successfully",flight));
    }



}
