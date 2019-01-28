package com.javagda17.sda.booking.booking_system.model.dto;

import lombok.Data;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class MeetingDto {

    private String title;

    private String start;

    private String end;

    private Integer guestsQuantity;

}
