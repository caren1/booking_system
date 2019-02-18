package com.javagda17.sda.booking.booking_system.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MeetingUpdateDto {

    private String title;

    private String start;

    private String end;

    private Integer guestsQuantity;

    private String hallName;

    private String serviceType;

    private String NIP;

}
