package com.javagda17.sda.booking.booking_system.model.dto;

import lombok.Data;

@Data
public class EmployeeUpdateAllDto {

    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String newPassword;
    private String oldPassword;

    private Integer startingHour;
    private Integer howManyHours;


}
