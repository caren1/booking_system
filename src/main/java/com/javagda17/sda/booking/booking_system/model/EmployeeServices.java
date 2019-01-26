package com.javagda17.sda.booking.booking_system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeServices {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String employeeServicesName;

    @OneToOne
    private WorkingHours workingHours;

    @OneToMany
    private Set<UnusualDays>daysOff = new HashSet<>();

    @OneToMany
    private Set <ServiceType> serviceTypes = new HashSet<>();

}
