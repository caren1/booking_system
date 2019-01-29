package com.javagda17.sda.booking.booking_system.controller;

import com.javagda17.sda.booking.booking_system.model.AppUser;
import com.javagda17.sda.booking.booking_system.model.dto.AppUserDto;
import com.javagda17.sda.booking.booking_system.model.dto.EditAllUserDto;
import com.javagda17.sda.booking.booking_system.respository.AppUserRepository;
import com.javagda17.sda.booking.booking_system.service.AppUserService;
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
    private AppUserRepository appUserRepository;


    @GetMapping("/clientUpdateForm/{userid}")
    public String getClientUpdateForm(@PathVariable(name = "userid") Long id, Model model) {
        model.addAttribute("userid", id);
        Optional<AppUser> appUserOptional = appUserService.getUserById(id);

        if(appUserOptional.isPresent()){
            AppUser appUser = appUserOptional.get();
            EditAllUserDto dto = new EditAllUserDto();
            dto.setSurname(appUser.getSurname());
            dto.setPassword(appUser.getPassword());
            dto.setEmail(appUser.getEmail());
            dto.setPhoneNumber(appUser.getPhoneNumber());
            dto.setName(appUser.getName());

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
    public String update(@PathVariable(name = "userid") Long id, Model model, EditAllUserDto appUserDto) {
        appUserService.update(id, appUserDto);
        return "redirect:/users";
    }


}

