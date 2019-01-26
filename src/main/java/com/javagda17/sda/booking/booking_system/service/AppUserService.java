package com.javagda17.sda.booking.booking_system.service;

import com.javagda17.sda.booking.booking_system.model.*;
import com.javagda17.sda.booking.booking_system.model.dto.AppUserDto;
import com.javagda17.sda.booking.booking_system.model.dto.CompanyDto;
import com.javagda17.sda.booking.booking_system.model.dto.ServiceTypeDto;
import com.javagda17.sda.booking.booking_system.model.dto.WorkingHoursDto;
import com.javagda17.sda.booking.booking_system.respository.*;
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
    private ServiceTypeRepository serviceTypeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private WorkingHoursRepository workingHoursRepository;

    @Autowired
    private EmployeeServicesRepository employeeServicesRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public boolean register(AppUserDto dto, CompanyDto companyDto) {

        Company company = new Company();
        company.setCompanyName(companyDto.getCompanyName());
        company.setAddress(companyDto.getAddress());
        company.setNIP(companyDto.getNIP());


        AppUser appUser = new AppUser();
        appUser.setUsername(dto.getUsername());
        appUser.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        appUser.getUserRoles().add(userRoleService.getClientRole());
        appUser.setName(dto.getName());
        appUser.setSurname(dto.getSurname());
        appUser.setEmail(dto.getEmail());
        appUser.setPhoneNumber(dto.getPhoneNumber());

        try {
            appUserRepository.saveAndFlush(appUser);
            companyRepository.saveAndFlush(company);

            appUser.setCompany(company);

            appUserRepository.save(appUser);

        } catch (ConstraintViolationException cve) {
            return false;
        }

        return true;
    }

    public boolean register(AppUserDto dto, WorkingHoursDto workingHoursDto, String serviceType) {

        EmployeeServices employeeServices = new EmployeeServices();

        WorkingHours workingHours = new WorkingHours();
        workingHours.setStartingHour(workingHoursDto.getStartingHour());
        workingHours.setHowManyHours(workingHoursDto.getHowManyHours());

        ServiceType serviceType1 = new ServiceType();

        if (serviceType.equals("lawyer")) {
            serviceType1 = serviceTypeRepository.findByName("Spotkanie z prawnikiem").get();

        } else if (serviceType.equals("accountant")) {
            serviceType1 = serviceTypeRepository.findByName("Spotkanie z ksiegowa").get();
        }

        AppUser appUser = new AppUser();
        appUser.setUsername(dto.getUsername());
        appUser.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        appUser.getUserRoles().add(userRoleService.getEmployeeRole());
        appUser.setName(dto.getName());
        appUser.setSurname(dto.getSurname());
        appUser.setEmail(dto.getEmail());
        appUser.setPhoneNumber(dto.getPhoneNumber());

        try {
            appUserRepository.saveAndFlush(appUser);
            workingHoursRepository.saveAndFlush(workingHours);

            employeeServices.setWorkingHours(workingHours);
            employeeServices.getServiceTypes().add(serviceType1);
            employeeServicesRepository.save(employeeServices);

            appUser.setEmployeeServices(employeeServices);
            appUserRepository.save(appUser);

        } catch (ConstraintViolationException cve) {
            return false;
        }

        return true;
    }

    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }
}