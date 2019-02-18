package com.javagda17.sda.booking.booking_system.model;

import lombok.*;

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
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private WorkingHours workingHours;

    @OneToMany
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<UnusualDays>daysOff = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set <ServiceType> serviceTypes = new HashSet<>();

}
