package com.javagda17.sda.booking.booking_system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private String email;
    private String phoneNumber;

    @OneToOne
    private Company company;

    @OneToMany
    private Set <UserRole> userRoles;

    @OneToMany
    private Set <Meeting> meetingSet;

    @OneToOne
    private EmployeeServices employeeServices;






}
