package com.pequla.flightcache.repository;

import com.pequla.flightcache.domain.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {
    @Query(value = "SELECT DISTINCT destination FROM flight", nativeQuery = true)
    List<String> findDistinctDestination();

    boolean existsByFlightKey(String key);

    Page<Flight> findAllByScheduledAtAfter(LocalDateTime scheduledAt, Pageable pageable);

    Page<Flight> findFlightsByDestinationContainsIgnoreCaseAndScheduledAtAfter(String destination, LocalDateTime scheduledAt, Pageable pageable);
}
