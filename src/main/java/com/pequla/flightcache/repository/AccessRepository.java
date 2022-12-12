package com.pequla.flightcache.repository;

import com.pequla.flightcache.domain.Access;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessRepository extends JpaRepository<Access, Integer> {
    Page<Access> findAllByAddress(String address, Pageable pageable);
}
