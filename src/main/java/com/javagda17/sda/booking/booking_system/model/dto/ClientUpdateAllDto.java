package com.javagda17.sda.booking.booking_system.model.dto;

import lombok.Data;

@Data
public class ClientUpdateAllDto {

    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String username;
    private String newPassword;
    private String oldPassword;

    private String companyName;
    private String NIP;
    private String address;
}
