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
import java.time.Month;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightService {

    private final FlightRepository repository;
    private final AccessService accessService;
    private final TypeService typeService;
    private final WebService webService;

    public Page<Flight> getFlights(Pageable pageable, String type, HttpServletRequest request) {
        accessService.saveAccess(request);
        if (type == null || type.equals(""))
            return repository.findAll(pageable);
        return repository.findAllByTypeNameIgnoreCase(type, pageable);
    }

    public Optional<Flight> getFlightById(Integer id, HttpServletRequest request) {
        accessService.saveAccess(request);
        return repository.findById(id);
    }

    public List<Flight> getFlightsByIds(List<Integer> ids, HttpServletRequest request) {
        accessService.saveAccess(request);
        return repository.findAllById(ids);
    }

    public List<String> getTodayDestinations(HttpServletRequest request) {
        accessService.saveAccess(request);
        return repository.findDistinctDestinationForToday();
    }

    public List<String> getTodayDestinationsStatsWith(String input, HttpServletRequest request) {
        accessService.saveAccess(request);
        return repository.findDistinctDestinationForTodayLike(input + "%");
    }

    public List<String> getDestinations(HttpServletRequest request) {
        accessService.saveAccess(request);
        return repository.findDistinctDestination();
    }

    public List<String> getDestinationsStatsWith(String input, HttpServletRequest request) {
        accessService.saveAccess(request);
        return repository.findDistinctDestinationLike(input + "%");
    }

    public Page<Flight> getTodayFlights(Pageable pageable, String type, HttpServletRequest request) {
        accessService.saveAccess(request);
        LocalDateTime now = LocalDateTime.now();
        if (type == null || type.equals(""))
            return repository.findAllByScheduledAtAfter(now, pageable);
        return repository.findAllByScheduledAtAfterAndTypeNameIgnoreCase(now, type, pageable);
    }

    public List<Flight> getTodayFlightsAsList(String type, HttpServletRequest request) {
        accessService.saveAccess(request);
        LocalDateTime now = LocalDateTime.now();
        if (type == null || type.equals(""))
            return repository.findAllByScheduledAtAfter(now);
        return repository.findAllByScheduledAtAfterAndTypeNameIgnoreCase(now, type);
    }

    public Page<Flight> getTodayFlightsByDestination(String destination, String type, Pageable pageable, HttpServletRequest request) {
        accessService.saveAccess(request);
        LocalDateTime now = LocalDateTime.now();
        if (type == null || type.equals(""))
            return repository.findFlightsByDestinationContainsIgnoreCaseAndScheduledAtAfter(destination, now, pageable);
        return repository.findFlightsByDestinationContainsIgnoreCaseAndTypeNameIgnoreCaseAndScheduledAtAfter(destination, type, now, pageable);
    }

    public void updateFlightData(HttpServletRequest request) {
        try {
            for (FlightModel f : webService.getFlightsFromBelgrade()) {
                log.info("Indexing flight: " + f.getKey());
                Flight parsed = parseFlightModel(f);

                // Ignore same flights under different number
                if (parsed.getConnectedFlight() != null &&
                        repository.existsByFlightNumberAndScheduledAt(parsed.getConnectedFlight(), parsed.getScheduledAt())) {
                    log.warn("Skipping flight, already exists under number " + parsed.getConnectedFlight());
                    continue;
                }

                // Record exists
                Optional<Flight> optional = repository.findByFlightKey(f.getKey());
                if (optional.isPresent()) {
                    log.info("Flight exists, updating data");
                    Flight existing = optional.get();
                    parsed.setId(existing.getId());
                }

                log.info("Saving flight data for " + parsed.getFlightKey());
                accessService.saveAccess(request);
                repository.save(parsed);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Flight parseFlightModel(FlightModel f) {
        log.info("Parsing flight data");
        LocalTime st = LocalTime.parse(f.getST());

        // Making sure ET exists
        LocalDateTime estimated = null;
        if (f.getET() != null && !f.getET().equals("")) {
            LocalTime et = LocalTime.parse(f.getET());
            estimated = LocalDateTime.of(parseDate(f.getDATUM_E()), et);
        }

        return Flight.builder()
                .type(typeService.getFlightTypeByInternalType(f.getTIP()))
                .flightKey(f.getKey())
                .flightNumber(f.getBROJ_LETA())
                .destination(f.getDESTINACIJA())
                .scheduledAt(LocalDateTime.of(parseDate(f.getDATUM()), st))
                .estimatedAt(estimated)
                .connectedType(validate(f.getTIP_VEZE()))
                .connectedFlight(validate(f.getVEZAN_LET()))
                .plane(validate(f.getTIP_AVIONA()))
                .gate(validate(f.getGATE_BAY()))
                .terminal(validate(f.getTERMINAL()))
                .build();
    }

    private String validate(String value) {
        if (value.equals("")) return null;
        return value;
    }

    private LocalDate parseDate(String date) {
        String[] split = date.split("-");
        int year = Integer.parseInt("20" + split[2]);
        Month month = switch (split[1]) {
            case "Feb" -> Month.FEBRUARY;
            case "Mar" -> Month.MARCH;
            case "Apr" -> Month.APRIL;
            case "May" -> Month.MAY;
            case "Jun" -> Month.JUNE;
            case "Jul" -> Month.JULY;
            case "Aug" -> Month.AUGUST;
            case "Sep" -> Month.SEPTEMBER;
            case "Oct" -> Month.OCTOBER;
            case "Nov" -> Month.NOVEMBER;
            case "Dec" -> Month.DECEMBER;
            default -> Month.JANUARY;
        };
        return LocalDate.of(year, month, Integer.parseInt(split[0]));
    }
}
