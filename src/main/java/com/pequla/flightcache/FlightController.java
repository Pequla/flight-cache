package com.pequla.flightcache;

import com.pequla.flightcache.model.Flight;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping(path = "/api/flight")
public class FlightController {

    private final WebService service;
    private static final String API_URL = "https://beg.aero/sites/belgrade/files/data/redLetenja.xml";

    @GetMapping
    public Flight[] getFlights() throws IOException, InterruptedException {
        Flight[] flights = service.getFlightsFromBelgrade();
        System.out.println(flights.length);
        return flights;

    }
}
