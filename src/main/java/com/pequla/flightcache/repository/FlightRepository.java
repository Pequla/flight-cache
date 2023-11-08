package com.pequla.flightcache.repository;

import com.pequla.flightcache.domain.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {
    @Query(value = "SELECT DISTINCT destination FROM flight WHERE scheduled_at >= CAST(CURRENT_TIMESTAMP AS DATETIME) ORDER BY destination ASC", nativeQuery = true)
    List<String> findDistinctDestinationForToday();

    @Query(value = "SELECT DISTINCT destination FROM flight ORDER BY destination ASC ", nativeQuery = true)
    List<String> findDistinctDestination();

    @Query(value = "SELECT DISTINCT destination FROM flight WHERE scheduled_at >= CAST(CURRENT_TIMESTAMP AS DATETIME) AND LOWER(destination) LIKE LOWER(:dest) ORDER BY destination ASC", nativeQuery = true)
    List<String> findDistinctDestinationForTodayLike(@Param("dest") String dest);

    @Query(value = "SELECT DISTINCT destination FROM flight WHERE LOWER(destination) LIKE LOWER(:dest) ORDER BY destination ASC", nativeQuery = true)
    List<String> findDistinctDestinationLike(@Param("dest") String dest);

    Optional<Flight> findByFlightKey(String key);

    boolean existsByFlightNumberAndScheduledAt(String number, LocalDateTime scheduledAt);

    Page<Flight> findAllByScheduledAtAfter(LocalDateTime scheduledAt, Pageable pageable);

    List<Flight> findAllByScheduledAtAfter(LocalDateTime scheduledAt);

    Page<Flight> findFlightsByDestinationContainsIgnoreCaseAndScheduledAtAfter(String destination, LocalDateTime scheduledAt, Pageable pageable);
}
