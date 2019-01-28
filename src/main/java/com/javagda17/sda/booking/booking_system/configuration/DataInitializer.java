package com.javagda17.sda.booking.booking_system.configuration;

import com.javagda17.sda.booking.booking_system.model.AppUser;
import com.javagda17.sda.booking.booking_system.model.Hall;
import com.javagda17.sda.booking.booking_system.model.ServiceType;
import com.javagda17.sda.booking.booking_system.model.UserRole;
import com.javagda17.sda.booking.booking_system.respository.AppUserRepository;
import com.javagda17.sda.booking.booking_system.respository.HallRepository;
import com.javagda17.sda.booking.booking_system.respository.ServiceTypeRepository;
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
    private ServiceTypeRepository serviceTypeRepository;

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        checkAndLoadRoles();
        checkAndLoadUsers();
        checkAndLoadServicesType();
        checkAndLoadHalls();

    }

    private void checkAndLoadUsers() {
        if (!checkUser("admin")) {
            createUser("admin", "admin", "ROLE_CLIENT", "ROLE_ADMIN");
        }
    }

    private void createUser(String username, String password, String... roles) {

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

    private ServiceType findServiceType(String serviceType) {
        Optional<ServiceType> serviceTypeOptional = serviceTypeRepository.findByName(serviceType);
        if (serviceTypeOptional.isPresent()) {

            return serviceTypeOptional.get();
        }
        throw new DataIntegrityViolationException("Service type does not exist. " +
                "Try fixing service type called: " + serviceType + " in Your data initializer.");
    }


    private boolean checkUser(String username) {
        return appUserRepository.findByUsername(username).isPresent();
    }

    private void checkAndLoadRoles() {
        if (!checkRole("ROLE_CLIENT")) {
            createRole("ROLE_CLIENT");
        }
        if (!checkRole("ROLE_ADMIN")) {
            createRole("ROLE_ADMIN");
        }
        if (!checkRole("ROLE_EMPLOYEE")) {
            createRole("ROLE_EMPLOYEE");
        }
    }
    private void checkAndLoadServicesType() {
        if (!checkServiceType("Wynajem sali")) {
            createServiceType("Wynajem sali", 25.00D);
        }
        if (!checkServiceType("Spotkanie z prawnikiem")) {
            createServiceType("Spotkanie z prawnikiem", 50.00D);
        }
        if (!checkServiceType("Spotkanie z ksiegowa")) {
            createServiceType("Spotkanie z ksiegowa", 40.00D);
        }
    }

    private boolean checkServiceType(String serviceType){
        return serviceTypeRepository.findByName(serviceType).isPresent();
    }

    private void createServiceType(String serviceType, Double pricePer15min){
        ServiceType createdService = new ServiceType(null, serviceType, pricePer15min);
        serviceTypeRepository.save(createdService);
    }

    private void checkAndLoadHalls() {
        if (!checkHall("Gold")) {
            createHall("Gold");
        }
        if (!checkHall("Silver")) {
            createHall("Silver");
        }
    }

        private void createHall(String hallName){
            Hall createdHall = new Hall(null, hallName, new HashSet<>());
            hallRepository.save(createdHall);
        }

    private boolean checkHall(String hallName){
        return hallRepository.findByHallName(hallName).isPresent();
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

