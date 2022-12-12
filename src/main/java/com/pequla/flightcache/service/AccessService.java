package com.pequla.flightcache.service;

import com.pequla.flightcache.domain.Access;
import com.pequla.flightcache.repository.AccessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccessService {

    private final AccessRepository repository;

    public Page<Access> getAccess(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Access> getAccessById(Integer id) {
        return repository.findById(id);
    }

    public Page<Access> getAccessByAddress(String address, Pageable pageable) {
        return repository.findAllByAddress(address, pageable);
    }

    public void saveAccess(HttpServletRequest request) {
        String address = request.getRemoteAddr();
        String xff = request.getHeader("X-Forwarded-For");
        if (xff != null && !xff.equals(""))
            address = xff;

        repository.save(Access.builder()
                .address(address)
                .port(String.valueOf(request.getRemotePort()))
                .method(request.getMethod())
                .path(request.getServletPath())
                .createdAt(LocalDateTime.now())
                .build());
    }
}
