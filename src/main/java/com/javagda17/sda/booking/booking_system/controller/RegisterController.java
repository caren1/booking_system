package com.javagda17.sda.booking.booking_system.controller;

import com.javagda17.sda.booking.booking_system.model.dto.AppUserDto;
import com.javagda17.sda.booking.booking_system.model.dto.CompanyDto;
import com.javagda17.sda.booking.booking_system.model.dto.ServiceTypeDto;
import com.javagda17.sda.booking.booking_system.model.dto.WorkingHoursDto;
import com.javagda17.sda.booking.booking_system.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@SuppressWarnings("ALL")
@Controller
public class RegisterController {

    @Autowired
    private AppUserService appUserService;

    @GetMapping("/register")
    public String getRegisterPage() {
        return "admin/register";
    }

    @PostMapping("/registerClient")
    public String submitRegisterForm(AppUserDto dto, CompanyDto companyDto) {
        // todo: rejestracja (obecnie brakuje serwisu)
        if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
            return "redirect:/admin/register?error=Passwords do not match";
        }
        boolean result = appUserService.register(dto, companyDto);

        if (!result) {
            return "redirect:/admin/register?error=Error while registering. Probably username exists.";
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/clientRegisterForm")
    public String getClientRegisterPage() {
        return "admin/clientRegister";
    }


    @GetMapping("/employeeRegisterForm")
    public String getEmployeeRegisterPage() {
        return "admin/employeeRegister";
    }


    @PostMapping("/registerEmployee")
    public String submitEmployeeRegisterForm(AppUserDto dto, WorkingHoursDto workingHoursDto, String serviceType) {
        // todo: rejestracja (obecnie brakuje serwisu)
        if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
            return "redirect:/admin/register?error=Passwords do not match";
        }
        boolean result = appUserService.register(dto, workingHoursDto, serviceType);

        if (!result) {
            return "redirect:/admin/register?error=Error while registering. Probably username exists.";
        }
        return "redirect:/admin/users";
    }



}
