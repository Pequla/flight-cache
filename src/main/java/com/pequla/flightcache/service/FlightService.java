package com.pequla.flightcache.service;

import com.pequla.flightcache.domain.Flight;
import com.pequla.flightcache.model.FlightModel;
import com.pequla.flightcache.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightService {

    private final FlightRepository repository;
    private final AccessService accessService;
    private final WebService webService;

    public Page<Flight> getFlights(Pageable pageable, HttpServletRequest request) {
        accessService.saveAccess(request);
        return repository.findAll(pageable);
    }

    public Optional<Flight> getFlightById(Integer id, HttpServletRequest request) {
        accessService.saveAccess(request);
        return repository.findById(id);
    }

    public Page<Flight> getFlightsByDestination(String destination, Pageable pageable, HttpServletRequest request) {
        accessService.saveAccess(request);
        return repository.findFlightsByDestinationContainsIgnoreCase(destination, pageable);
    }

    public List<String> getDestinations(HttpServletRequest request) {
        accessService.saveAccess(request);
        return repository.findDistinctDestination();
    }

    public Page<Flight> getTodayFlights(Pageable pageable, HttpServletRequest request) {
        accessService.saveAccess(request);
        return repository.findAllByScheduledAtAfter(LocalDateTime.now(), pageable);
    }

    public Page<Flight> getTodayFlightsByDestination(String destination, Pageable pageable, HttpServletRequest request) {
        accessService.saveAccess(request);
        LocalDateTime now = LocalDateTime.now();
        return repository.findFlightsByDestinationContainsIgnoreCaseAndScheduledAtAfter(destination, now, pageable);
    }

    public void updateFlightData() {
        try {
            for (FlightModel f : webService.getCurrentDay()) {
                log.info("Indexing flight: " + f.getKey());

                // Record exists
                if (repository.existsByFlightKey(f.getKey())) {
                    log.warn("Flight exists, skipping");
                    continue;
                }

                log.info("Parsing flight data");
                LocalTime st = LocalTime.parse(f.getST());
                LocalDate date = LocalDate.now();

                // Making sure ET exists
                LocalDateTime estimated = null;
                if (f.getET() != null && !f.getET().equals("")) {
                    LocalTime et = LocalTime.parse(f.getET());
                    estimated = LocalDateTime.of(date, et);
                }

                Flight flight = Flight.builder()
                        .flightKey(f.getKey())
                        .flightNumber(f.getBROJ_LETA())
                        .destination(f.getDESTINACIJA())
                        .scheduledAt(LocalDateTime.of(date, st))
                        .estimatedAt(estimated)
                        .connectedType(validate(f.getTIP_VEZE()))
                        .connectedFlight(validate(f.getVEZAN_LET()))
                        .plane(validate(f.getTIP_AVIONA()))
                        .gate(validate(f.getGATE_BAY()))
                        .terminal(validate(f.getTERMINAL()))
                        .build();

                log.info("Saving flight data for " + flight.getFlightKey());
                repository.save(flight);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String validate(String value) {
        if (value.equals("")) return null;
        return value;
    }
}
