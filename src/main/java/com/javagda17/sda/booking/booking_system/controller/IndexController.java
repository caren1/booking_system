package com.javagda17.sda.booking.booking_system.controller;

import com.javagda17.sda.booking.booking_system.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/register")
    public String getRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String submitRegisterForm(String name,
                                     String surname,
                                     String email,
                                     String phoneNumber,
                                     String username,
                                     String password,
                                     String passwordConfirm) {
        // todo: rejestracja (obecnie brakuje serwisu)
        if (!password.equals(passwordConfirm)) {
            return "redirect:/register?error=Passwords do not match";
        }

        boolean result = appUserService.register(username, password);

        if (!result) {
            return "redirect:/register?error=Error while registerying. Probably username exists.";
        }

        return "redirect:/login";
    }
}
