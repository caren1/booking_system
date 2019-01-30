package com.javagda17.sda.booking.booking_system.controller;

import com.javagda17.sda.booking.booking_system.model.AppUser;
import com.javagda17.sda.booking.booking_system.model.Meeting;
import com.javagda17.sda.booking.booking_system.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/")
public class AdminUserController {

    @Autowired
    private AppUserService appUserService;


    // adres na który trzeba wejść:
    // /admin/users
    @GetMapping("/users")
    public String getUserList(Model model) {
        List<AppUser> users = appUserService.getAllUsers();

        model.addAttribute("user_list", users);

        model.addAttribute("user_roles", users.stream()
                .map(user -> user.getUserRoles().stream().map(role -> role.getName()).collect(Collectors.toList()))
                // w wyniku powyższej instrukcji otrzymaliśmy stream list (ze stringami) uprawnień użytkowników
                .map(String::valueOf)
                // zamieniam każdą listę na tekst
                .collect(Collectors.toList()));

        // zwracamy admin (bo plik html znajduje się w katalogu admin)
        // a po nim /userlist (bo plik html nazywa się userlist)
        return "admin/userlist";
    }

    @GetMapping("/employees")
    public String getEmployeesList(Model model) {
        List<AppUser> employees = appUserService.getAllUsers();

        model.addAttribute("employees_list", employees);

//        model.addAttribute("employees", employees.stream()
//                .map(user -> user.getUserRoles().stream().map(role -> role.getName()).collect(Collectors.toList()))
//                .map(String::valueOf)
//                .collect(Collectors.toList()));

        model.addAttribute("employees_services", employees.stream()
                .map(appUser -> appUser.getEmployeeServices().getServiceTypes().iterator().next().getName()));


        return "admin/employeeslist";


    }


}
