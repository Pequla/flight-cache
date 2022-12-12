package com.pequla.flightcache.runner;

import com.pequla.flightcache.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.TimerTask;

@Component
@RequiredArgsConstructor
public class UpdateTask extends TimerTask {
    private final FlightService service;

    @Override
    public void run() {
        service.updateFlightData();
    }
}
