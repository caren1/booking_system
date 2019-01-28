package com.javagda17.sda.booking.booking_system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String start;

    private String end;

    private Integer guestsQuantity;

    @ManyToOne
    private Hall hall;

    @OneToOne
    private ServiceType serviceType;

    private Double totalPrice;

    @ManyToMany
    private Set<AppUser> participantSet = new HashSet<>();

}
