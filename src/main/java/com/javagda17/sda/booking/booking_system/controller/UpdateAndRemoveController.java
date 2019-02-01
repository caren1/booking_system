package com.javagda17.sda.booking.booking_system.controller;

import com.javagda17.sda.booking.booking_system.model.AppUser;
import com.javagda17.sda.booking.booking_system.model.Meeting;
import com.javagda17.sda.booking.booking_system.model.dto.ClientUpdateAllDto;
import com.javagda17.sda.booking.booking_system.model.dto.EmployeeUpdateAllDto;
import com.javagda17.sda.booking.booking_system.model.dto.MeetingUpdateDto;
import com.javagda17.sda.booking.booking_system.respository.AppUserRepository;
import com.javagda17.sda.booking.booking_system.service.AppUserService;
import com.javagda17.sda.booking.booking_system.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class UpdateAndRemoveController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping("/employeeUpdateForm/{employeeid}")
    public String getEmployeeUpdateForm(@PathVariable(name = "employeeid") Long id, Model model) {
        model.addAttribute("employeeid", id);
        Optional<AppUser> appUserOptional = appUserService.getUserById(id);

        if(appUserOptional.isPresent()) {
            AppUser appUser = appUserOptional.get();
            EmployeeUpdateAllDto dto = new EmployeeUpdateAllDto();
            dto.setName(appUser.getName());
            dto.setSurname(appUser.getSurname());
            dto.setEmail(appUser.getEmail());
            dto.setPhoneNumber(appUser.getPhoneNumber());

            dto.setStartingHour(appUser.getEmployeeServices().getWorkingHours().getStartingHour());
            dto.setHowManyHours(appUser.getEmployeeServices().getWorkingHours().getHowManyHours());

            model.addAttribute("editedEmployee", dto);
            return "admin/employeeUpdate";
        }
        return "redirect:/error";
    }

    @PostMapping("/updateEmployee/{employeeid}")
    public String updateEmployee(@PathVariable(name = "employeeid") Long id, Model model, EmployeeUpdateAllDto employeeUpdateAllDto) {
        appUserService.updateE(id, employeeUpdateAllDto);
        return "redirect:/admin/employees";

    }

    @GetMapping("/employeeRemove/{employeeid}")
    public String getEmployeeRemovePage(@PathVariable(name = "employeeid") Long id, Model model) {
        model.addAttribute("employeeid", id);
        Optional<AppUser> appUserOptional = appUserService.getUserById(id);
        if (appUserOptional.isPresent()){
            appUserService.removeEm(id);
            return "redirect:/admin/employees";
        }

        return "redirect:/error";
    }


    @GetMapping("/clientUpdateForm/{userid}")
    public String getClientUpdateForm(@PathVariable(name = "userid") Long id, Model model) {
        model.addAttribute("userid", id);
        Optional<AppUser> appUserOptional = appUserService.getUserById(id);

        if(appUserOptional.isPresent()){
            AppUser appUser = appUserOptional.get();
            ClientUpdateAllDto dto = new ClientUpdateAllDto();
            dto.setName(appUser.getName());
            dto.setSurname(appUser.getSurname());
            dto.setEmail(appUser.getEmail());
            dto.setPhoneNumber(appUser.getPhoneNumber());


            dto.setAddress(appUser.getCompany().getAddress());
            dto.setCompanyName(appUser.getCompany().getCompanyName());
            dto.setNIP(appUser.getCompany().getNIP());

            //todo

            model.addAttribute("editedUser", dto);
            return "admin/clientUpdate";
        }
        return "redirect:/error";
    }

    @PostMapping("/updateClient/{userid}")
    public String updateClient(@PathVariable(name = "userid") Long id, Model model, ClientUpdateAllDto updatedClientDto) {
        appUserService.updateC(id, updatedClientDto);
        return "redirect:/admin/users";

    }

    @GetMapping("/clientRemove/{userid}")
    public String getClientRemovePage(@PathVariable(name = "userid") Long id, Model model) {
        model.addAttribute("userid", id);
        Optional<AppUser> appUserOptional = appUserService.getUserById(id);
        if (appUserOptional.isPresent()){
            appUserService.removeC(id);
            return "redirect:/admin/users";
        }

        return "redirect:/error";
    }






}

