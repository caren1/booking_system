package com.javagda17.sda.booking.booking_system.respository;

import com.javagda17.sda.booking.booking_system.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    Optional<UserRole> findByName(String role_user);

}
