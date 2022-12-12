package com.pequla.flightcache.runner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Timer;

@RequiredArgsConstructor
@Component
@Slf4j
public class UpdateRunner implements CommandLineRunner {

    private final UpdateTask task;

    @Override
    public void run(String... args) throws Exception {
        Timer timer = new Timer();
        log.info("Scheduled data refresh every day");
        timer.schedule(task, 0, 86400000);
    }
}
