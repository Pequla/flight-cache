package com.pequla.flightcache.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "access")
public class Access {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "access_id")
    private Integer id;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String port;

    @Column(nullable = false)
    private String method;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
