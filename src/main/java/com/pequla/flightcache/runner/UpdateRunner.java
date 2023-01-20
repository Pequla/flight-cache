package com.pequla.flightcache.runner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
@Slf4j
public class UpdateRunner implements CommandLineRunner {

    private final UpdateTask task;

    @Override
    public void run(String... args) {
        Timer timer = new Timer();
        log.info("Scheduled data refresh every hour");
        timer.schedule(task, 0L, 1000 * 60 * 60);
    }
}
