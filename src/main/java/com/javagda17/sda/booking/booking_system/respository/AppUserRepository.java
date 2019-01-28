package com.javagda17.sda.booking.booking_system.respository;

import com.javagda17.sda.booking.booking_system.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findById(Long id); //

    Optional<AppUser> findAllByUserRolesIs(String role);
    List<AppUser> getAllByEmployeeServicesIsNotNull();


}
