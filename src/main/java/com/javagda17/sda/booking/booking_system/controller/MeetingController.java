package com.javagda17.sda.booking.booking_system.controller;

import com.javagda17.sda.booking.booking_system.model.AppUser;
import com.javagda17.sda.booking.booking_system.model.Meeting;
import com.javagda17.sda.booking.booking_system.model.dto.MeetingDto;
import com.javagda17.sda.booking.booking_system.model.dto.MeetingUpdateDto;
import com.javagda17.sda.booking.booking_system.respository.MeetingRepository;
import com.javagda17.sda.booking.booking_system.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class MeetingController {

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private MeetingService meetingService;



    @GetMapping("/meetings")
    public String getMeetingsList(Model model) {
        List<Meeting> meetings = meetingService.getAllUsers();

        model.addAttribute("meetings_list", meetings);

//        model.addAttribute("employees", employees.stream()
//                .map(user -> user.getUserRoles().stream().map(role -> role.getName()).collect(Collectors.toList()))
//                .map(String::valueOf)
//                .collect(Collectors.toList()));

        model.addAttribute("participants_list", meetings.stream()
                .map(appUser -> appUser.getParticipantSet().stream().map(guest -> guest.getSurname()).collect(Collectors.toList()))
                .map(String::valueOf).collect(Collectors.toList()));

        return "admin/meetingslist";


    }

    @GetMapping("/meetingForm")
    public String getMeetingForm(){
        return "admin/addMeeting";
    }

    @PostMapping("/addmeeting")
    public String submitMeetingForm(MeetingDto meetingDto, String serviceType, String NIP, String hallName){
        if (meetingDto.getTitle().isEmpty() || meetingDto.getStart().isEmpty() || meetingDto.getEnd().isEmpty() || hallName.isEmpty()) {
            return "redirect:/meetingform?error=Fill all fields";
        }

        boolean result = meetingService.addMeeting(meetingDto, serviceType, NIP, hallName);
        if (!result) {
            return "redirect:/meetingform?error=Error while adding. Try again.";
        }
        return "redirect:/";
    }

    @GetMapping("/meeetingRemove/{meetingid}")
    public String getMeetingRemovePage(@PathVariable(name = "meetingid") Long id, Model model) {
        model.addAttribute("meetingid", id);
        Optional<Meeting> meetingOptional = meetingService.getMeetingById(id);
        if (meetingOptional.isPresent()){
            meetingService.remove(id);
            return "redirect:/admin/meetings";
        }

        return "redirect:/error";
    }

    @GetMapping("/meetingUpdateForm/{meetingid}")
    public String getMeetingUpdateForm(@PathVariable(name = "meetingid") Long id, Model model) {
        model.addAttribute("meetingid", id);
        Optional<Meeting> meetingOptional = meetingService.getMeetingById(id);
        String serviceType = new String();
        if(meetingOptional.isPresent()){
            Meeting meeting = meetingOptional.get();
            Set<AppUser>participantSet=meeting.getParticipantSet();
            AppUser client = new AppUser();
            for (AppUser appUser : participantSet){
                if (appUser.getCompany()!=null){
                    client=appUser;
                }
            }
            if (meeting.getServiceType().getName().equals("Spotkanie z prawnikiem")) {
                serviceType = "lawyer";

            } else if (meeting.getServiceType().getName().equals("Spotkanie z ksiegowa")) {
                serviceType = "accountant";

            } else if (meeting.getServiceType().getName().equals("Wynajem sali")) {
                serviceType = "hallBooking";
            }

            MeetingUpdateDto dto = new MeetingUpdateDto();
            dto.setTitle(meeting.getTitle());
            dto.setStart(meeting.getStart().toString());
            dto.setEnd(meeting.getEnd().toString());
            dto.setGuestsQuantity(meeting.getGuestsQuantity());
            dto.setNIP(client.getCompany().getNIP());
            dto.setServiceType(serviceType);
            dto.setHallName(meeting.getHall().getHallName());

            //todo

            model.addAttribute("editedMeeting", dto);
            return "admin/meetingUpdate";
        }
        return "redirect:/error";
    }


    @PostMapping("/updateMeeting/{meetingid}")
    public String updateMeeting(@PathVariable(name = "meetingid") Long id, Model model, MeetingUpdateDto meetingUpdateDto) {
        meetingService.update(id, meetingUpdateDto );
        return "redirect:/meetings";

    }




}
