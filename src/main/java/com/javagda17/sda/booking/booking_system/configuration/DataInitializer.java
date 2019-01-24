package com.javagda17.sda.booking.booking_system.configuration;

import com.javagda17.sda.booking.booking_system.model.AppUser;
import com.javagda17.sda.booking.booking_system.model.UserRole;
import com.javagda17.sda.booking.booking_system.respository.AppUserRepository;
import com.javagda17.sda.booking.booking_system.respository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class DataInitializer implements
        ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        checkAndLoadRoles();
        checkAndLoadUsers();
    }

    private void checkAndLoadUsers() {
        if (!checkUser("admin")) {
            createUser("admin", "admin", "ROLE_USER", "ROLE_ADMIN");
        }
        // dodaj user'a!
        if (!checkUser("user")) {
            createUser("user", "user", "ROLE_USER");
        }
        if (!checkUser("ispy")) {
            createUser("ispy", "ispy", "ROLE_USER", "ROLE_SPY");
        }
    }

    private void createUser(String username, String password, String... roles) {
//        AppUser appUser = new AppUser();
//        appUser.setUsername(username);
//        appUser.setPassword(password);

        // odnajdujemy w bazie danych wszystkie uprawnienia które należy nadać użytkownikowi
        Set<UserRole> userRoles2 = new HashSet<>();
        for (String role : roles) {
            userRoles2.add(findRole(role));
            // zbieramy uprawnienia do setu
        }

        // tworzymy instancję użytkownika
        AppUser appUser = AppUser.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .userRoles(userRoles2)
                .build();

        // zapisujemy instancję w bazie
        appUserRepository.saveAndFlush(appUser);
    }

    private UserRole findRole(String role) {
        Optional<UserRole> userRoleOptional = userRoleRepository.findByName(role);
        if (userRoleOptional.isPresent()) {

            return userRoleOptional.get();
        }
        throw new DataIntegrityViolationException("User role does not exist. " +
                "Try fixing user role called: " + role + " in Your data initializer.");
    }

    private boolean checkUser(String username) {
        return appUserRepository.findByUsername(username).isPresent();
    }

    private void checkAndLoadRoles() {
        if (!checkRole("ROLE_USER")) {
            createRole("ROLE_USER");
        }
        if (!checkRole("ROLE_ADMIN")) {
            createRole("ROLE_ADMIN");
        }
        if (!checkRole("ROLE_SPY")) {
            createRole("ROLE_SPY");
        }
    }

    /**
     * Tworzenie roli użytkownika.
     *
     * @param role - nazwa roli która ma być stworzona.
     */
    private void createRole(String role) {
        UserRole createdRole = new UserRole(null, role);
        userRoleRepository.save(createdRole);
    }

    /**
     * Sprawdzenie czy rola istnieje.
     *
     * @param role - nazwa roli która ma być sprawdzona.
     * @return - wynik true - jeśli rola istnieje, false jeśli nie.
     */
    private boolean checkRole(String role) {
        return userRoleRepository.findByName(role).isPresent();
    }
}

