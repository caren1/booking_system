package com.javagda17.sda.booking.booking_system.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private LocalDateTime start;

    private LocalDateTime end;

    private Integer guestsQuantity;

    @ManyToOne
    private Hall hall;

    @OneToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ServiceType serviceType;

    private Double totalPrice;

    @ManyToMany
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<AppUser> participantSet = new HashSet<>();

}
