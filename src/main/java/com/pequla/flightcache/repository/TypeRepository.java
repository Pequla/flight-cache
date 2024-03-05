package com.pequla.flightcache.repository;

import com.pequla.flightcache.domain.FlightType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeRepository extends JpaRepository<FlightType, Integer> {

    Optional<FlightType> findByName(String name);
}
