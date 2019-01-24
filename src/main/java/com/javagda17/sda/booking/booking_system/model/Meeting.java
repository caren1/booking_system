package com.javagda17.sda.booking.booking_system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.Period;
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

    private LocalDateTime start;

    private LocalDateTime end;

    private Integer guestsQuantity;

    @OneToOne
    private Duties duties;

    private Double totalPrice;

    @ManyToMany
    private Set<AppUser> participantSet;








}
