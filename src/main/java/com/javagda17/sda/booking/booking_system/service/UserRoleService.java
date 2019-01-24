package com.javagda17.sda.booking.booking_system.service;

import com.javagda17.sda.booking.booking_system.model.UserRole;
import com.javagda17.sda.booking.booking_system.respository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    public UserRole getUserRole() {
        Optional<UserRole> optionalUserRole = userRoleRepository.findByName("ROLE_USER");
        if (optionalUserRole.isPresent()) {
            return optionalUserRole.get();
        }

        throw new DataIntegrityViolationException("USER_ROLE should exist in database.");
    }

    public UserRole getAdminRole() {
        Optional<UserRole> optionalUserRole = userRoleRepository.findByName("ROLE_ADMIN");
        if (optionalUserRole.isPresent()) {
            return optionalUserRole.get();
        }

        throw new DataIntegrityViolationException("ADMIN_ROLE should exist in database.");
    }

    public UserRole getClientRole() {
        Optional<UserRole> optionalUserRole = userRoleRepository.findByName("ROLE_CLIENT");
        if (optionalUserRole.isPresent()) {
            return optionalUserRole.get();
        }

        throw new DataIntegrityViolationException("CLIENT_ROLE should exist in database.");
    }

    public UserRole getEmployeeRole() {
        Optional<UserRole> optionalUserRole = userRoleRepository.findByName("ROLE_EMPLOYEE");
        if (optionalUserRole.isPresent()) {
            return optionalUserRole.get();
        }

        throw new DataIntegrityViolationException("EMPLOYEE_ROLE should exist in database.");
    }

}