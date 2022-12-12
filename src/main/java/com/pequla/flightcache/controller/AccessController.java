package com.pequla.flightcache.controller;

import com.pequla.flightcache.domain.Access;
import com.pequla.flightcache.service.AccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping(path = "/api/access")
public class AccessController {

    private final AccessService service;

    @GetMapping
    public Page<Access> getAll(Pageable pageable) {
        return service.getAccess(pageable);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Access> getById(@PathVariable Integer id) {
        return ResponseEntity.of(service.getAccessById(id));
    }

    @GetMapping(path = "/address/{address}")
    public Page<Access> getByAddress(@PathVariable String address, Pageable pageable) {
        return service.getAccessByAddress(address, pageable);
    }
}
