package com.pequla.flightcache.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "flight")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id")
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "type_id", nullable = false)
    private FlightType type;

    @Column(unique = true, nullable = false, name = "flight_key")
    private String flightKey;

    @Column(nullable = false, name = "flight_number")
    private String flightNumber;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false, name = "scheduled_at")
    private LocalDateTime scheduledAt;

    @Column(name = "estimated_at")
    private LocalDateTime estimatedAt;

    @Column(name = "connected_type")
    private String connectedType;

    @Column(name = "connected_flight")
    private String connectedFlight;
    private String plane;
    private String gate;
    private String terminal;
}
