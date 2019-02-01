package com.javagda17.sda.booking.booking_system.respository;

import com.javagda17.sda.booking.booking_system.model.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {

    Optional<ServiceType> findByName(String serviceType);

}
