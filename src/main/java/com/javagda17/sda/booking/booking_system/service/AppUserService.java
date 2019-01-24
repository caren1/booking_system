package com.javagda17.sda.booking.booking_system.service;

import com.javagda17.sda.booking.booking_system.model.AppUser;
import com.javagda17.sda.booking.booking_system.model.dto.AppUserDto;
import com.javagda17.sda.booking.booking_system.respository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean register(AppUserDto dto) {
        AppUser appUser = new AppUser();
        appUser.setUsername(dto.getUsername());
        appUser.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        appUser.getUserRoles().add(userRoleService.getUserRole());
        appUser.setName(dto.getName());
        appUser.setSurname(dto.getSurname());
        appUser.setEmail(dto.getEmail());
        appUser.setPhoneNumber(dto.getPhoneNumber());

        try {
            appUserRepository.saveAndFlush(appUser);
        } catch (ConstraintViolationException cve) {
            return false;
        }

        return true;
    }

    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }
}