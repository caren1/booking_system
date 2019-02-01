package com.javagda17.sda.booking.booking_system.respository;

import com.javagda17.sda.booking.booking_system.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findById(Long id);
}
