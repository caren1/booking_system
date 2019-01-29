package com.javagda17.sda.booking.booking_system.model.dto;

import lombok.Data;

@Data
public class EditAllUserDto {

    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String username;
    private String password;
    private String passwordConfirm;

    private String companyName;
    private String NIP;
    private String address;
}
