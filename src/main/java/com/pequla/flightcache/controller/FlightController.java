package com.pequla.flightcache.controller;

import com.pequla.flightcache.domain.Flight;
import com.pequla.flightcache.service.FlightService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping(path = "/api/flight")
public class FlightController {

    private final FlightService service;

    @GetMapping
    public Page<Flight> getToday(Pageable pageable, HttpServletRequest request) {
        return service.getTodayFlights(pageable, request);
    }

    @GetMapping(path = "/all")
    public Page<Flight> getAll(Pageable pageable, HttpServletRequest request) {
        return service.getFlights(pageable, request);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Flight> getById(@PathVariable Integer id, HttpServletRequest request) {
        return ResponseEntity.of(service.getFlightById(id, request));
    }

    @PostMapping(path = "/list")
    public List<Flight> getByIds(@RequestBody List<Integer> ids, HttpServletRequest request) {
        return service.getFlightsByIds(ids, request);
    }

    @GetMapping(path = "/destination")
    public List<String> getDestinations(HttpServletRequest request) {
        return service.getTodayDestinations(request);
    }

    @GetMapping(path = "/destination/search/{input}")
    public List<String> searchDestinations(@PathVariable String input, HttpServletRequest request) {
        return service.getTodayDestinationsStatsWith(input, request);
    }

    @GetMapping(path = "/destination/all")
    public List<String> getAllDestinations(HttpServletRequest request) {
        return service.getDestinations(request);
    }

    @GetMapping(path = "/destination/all/search/{input}")
    public List<String> searchAllDestinations(@PathVariable String input, HttpServletRequest request) {
        return service.getDestinationsStatsWith(input, request);
    }

    @GetMapping(path = "/destination/{destination}")
    public Page<Flight> getByDestination(@PathVariable String destination, Pageable pageable, HttpServletRequest request) {
        return service.getTodayFlightsByDestination(destination, pageable, request);
    }

    @PostMapping(path = "/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateFlightCache(HttpServletRequest request) {
        service.updateFlightData(request);
    }
}
