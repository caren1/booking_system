package com.javagda17.sda.booking.booking_system.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;

    private String name;
    private String surname;

    @Email
    private String email;

    private String phoneNumber;

    @OneToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Company company;

    @ManyToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set <UserRole> userRoles = new HashSet<>();

    @ManyToMany(mappedBy = "participantSet")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set <Meeting> meetingSet = new HashSet<>();

    @OneToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private EmployeeServices employeeServices;






}
