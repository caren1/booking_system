package com.javagda17.sda.booking.booking_system.controller;

import com.javagda17.sda.booking.booking_system.model.dto.MeetingDto;
import com.javagda17.sda.booking.booking_system.respository.MeetingRepository;
import com.javagda17.sda.booking.booking_system.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MeetingController {

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private MeetingService meetingService;



    @GetMapping("/meetingForm")
    public String getMeetingForm(){
        return "admin/addMeeting";
    }

    @PostMapping("/addmeeting")
    public String submitMeetingForm(MeetingDto meetingDto, String serviceType, String NIP, String hallName){
        if (meetingDto.getTitle().isEmpty() || meetingDto.getStart().isEmpty() || meetingDto.getEnd().isEmpty() || hallName.isEmpty()) {
            return "redirect:/admin/meetingform?error=Fill all fields";
        }

        boolean result = meetingService.addMeeting(meetingDto, serviceType, NIP, hallName);
        if (!result) {
            return "redirect:/admin/meetingform?error=Error while adding. Try again.";
        }
        return "redirect:/index";
    }
}
