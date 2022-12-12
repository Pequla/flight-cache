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
    public void run(String... args) throws Exception {
        Timer timer = new Timer();
        log.info("Scheduled data refresh every day");
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 1);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        timer.schedule(task, today.getTime(), TimeUnit.MILLISECONDS.convert(24, TimeUnit.HOURS));
    }
}
