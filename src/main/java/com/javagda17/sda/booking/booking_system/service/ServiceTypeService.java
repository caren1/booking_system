package com.javagda17.sda.booking.booking_system.service;

import com.javagda17.sda.booking.booking_system.model.ServiceType;
import com.javagda17.sda.booking.booking_system.model.UserRole;
import com.javagda17.sda.booking.booking_system.respository.ServiceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceTypeService {

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    public ServiceType getLawyerServiceType() {
        Optional<ServiceType> optionalServiceType = serviceTypeRepository.findByName("Spotkanie z prawnikiem");
        if (optionalServiceType.isPresent()) {
            return optionalServiceType.get();
        }

        throw new DataIntegrityViolationException("ADMIN_ROLE should exist in database.");
    }

    public ServiceType getAccountantServiceType() {
        Optional<ServiceType> optionalServiceType = serviceTypeRepository.findByName("Spotkanie z ksiegowa");
        if (optionalServiceType.isPresent()) {
            return optionalServiceType.get();
        }

        throw new DataIntegrityViolationException("This Service Type should exist in database.");
    }

    public ServiceType getHallBookingServiceType() {
        Optional<ServiceType> optionalServiceType = serviceTypeRepository.findByName("Wynajem sali");
        if (optionalServiceType.isPresent()) {
            return optionalServiceType.get();
        }

        throw new DataIntegrityViolationException("This Service Type should exist in database.");
    }


    private ServiceType findServiceType(String serviceType) {
        Optional<ServiceType> serviceTypeOptional = serviceTypeRepository.findByName(serviceType);
        if (serviceTypeOptional.isPresent()) {

            return serviceTypeOptional.get();
        }
        throw new DataIntegrityViolationException("Service type does not exist. " +
                "Try fixing service type called: " + serviceType + " in Your data initializer.");
    }
}
