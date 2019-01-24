package com.javagda17.sda.booking.booking_system.controller;

import com.javagda17.sda.booking.booking_system.model.dto.AppUserDto;
import com.javagda17.sda.booking.booking_system.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class IndexController {

    @Autowired
    private AppUserService appUserService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }
    // UWAGA! NIE ROBIMY POSTMAPPING NA /LOGIN
    // SPRING SAM OBSŁUGUJE METODĘ POST

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/clientRegister")
    public String getClientRegisterPage(){
        return "admin/clientRegister";
    }
    @GetMapping("/employeeRegister")
    public String getEmployeeRegisterPage(){
        return "admin/employeeRegister";
    }

}
