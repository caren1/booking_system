package com.javagda17.sda.booking.booking_system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeServices {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String category;
    @OneToOne
    private WorkingHours workingHours;

    @OneToMany
    private Set<UnusualDays>daysOff;

    @OneToMany
    private Set <Duties> duties;

}
