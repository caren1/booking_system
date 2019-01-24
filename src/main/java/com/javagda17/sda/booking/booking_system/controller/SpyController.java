package com.javagda17.sda.booking.booking_system.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpyController {

    @GetMapping("/spyOnPage")
    @PreAuthorize("hasRole('ROLE_SPY')")
    public String spyTest() {
        return "admin/spypage";
    }
}
