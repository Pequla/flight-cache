package com.pequla.flightcache.service;

import com.pequla.flightcache.domain.FlightType;
import com.pequla.flightcache.repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TypeService {

    private final TypeRepository repository;

    public FlightType getFlightTypeByInternalType(String value) {
        if (value.toCharArray()[1] == 'D') {
            return repository.findByName("DEPARTURE").orElseThrow();
        }
        return repository.findByName("ARRIVAL").orElseThrow();
    }
}
