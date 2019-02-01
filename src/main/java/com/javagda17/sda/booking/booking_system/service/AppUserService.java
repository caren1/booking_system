package com.javagda17.sda.booking.booking_system.service;

import com.javagda17.sda.booking.booking_system.model.*;
import com.javagda17.sda.booking.booking_system.model.dto.*;
import com.javagda17.sda.booking.booking_system.respository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SuppressWarnings("ALL")
@Service
public class AppUserService {

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    @Autowired
    private ServiceTypeService serviceTypeService;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private WorkingHoursRepository workingHoursRepository;

    @Autowired
    private EmployeeServicesRepository employeeServicesRepository;

    @Autowired
    private HallRepository hallRepository;

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
        employeeServices.setEmployeeServicesName(dto.getName() + " " + dto.getSurname() + " employee services");

        WorkingHours workingHours = new WorkingHours();
        workingHours.setStartingHour(workingHoursDto.getStartingHour());
        workingHours.setHowManyHours(workingHoursDto.getHowManyHours());


        if (serviceType.equals("lawyer")) {
            employeeServices.getServiceTypes().add(serviceTypeService.getLawyerServiceType());

        } else if (serviceType.equals("accountant")) {
            employeeServices.getServiceTypes().add(serviceTypeService.getAccountantServiceType());
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
            employeeServicesRepository.save(employeeServices);
            workingHoursRepository.saveAndFlush(workingHours);

            employeeServices.setWorkingHours(workingHours);
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

    public boolean updateC(Long id, ClientUpdateAllDto updatedClientDto) {

        Optional<AppUser> appUserOptional = appUserRepository.findById(id);
        if (appUserOptional.isPresent()) {
            AppUser clientToUpdate = appUserOptional.get();
            if (bCryptPasswordEncoder.matches(updatedClientDto.getOldPassword(), clientToUpdate.getPassword())) {
                clientToUpdate.setPassword(bCryptPasswordEncoder.encode(updatedClientDto.getNewPassword()));
                clientToUpdate.setName(updatedClientDto.getName());
                clientToUpdate.setSurname(updatedClientDto.getSurname());
                clientToUpdate.setEmail(updatedClientDto.getEmail());
                clientToUpdate.setPhoneNumber(updatedClientDto.getPhoneNumber());

                Optional<Company> companyOptional = companyRepository.findById(clientToUpdate.getCompany().getId());
                if (companyOptional.isPresent()) {
                    Company company = companyOptional.get();
                    company.setCompanyName(updatedClientDto.getCompanyName());
                    company.setNIP(updatedClientDto.getNIP());
                    company.setAddress(updatedClientDto.getAddress());
                    companyRepository.save(company);
                    appUserRepository.save(clientToUpdate);
                }
                return true;
            }
            return false;

        }
        return false;

    }

    public Optional<AppUser> getUserById(Long id) {
        return appUserRepository.findById(id);
    }


    public boolean removeC(Long id) {
        Optional<AppUser> appUserToRemoveOptional = appUserRepository.findById(id);
        Optional<Company> companyToRemoveOptional = companyRepository.findById(appUserToRemoveOptional.get().getCompany().getId());
        if (appUserToRemoveOptional.isPresent()) {
            companyRepository.delete(companyToRemoveOptional.get());
            while (appUserToRemoveOptional.get().getMeetingSet().iterator().hasNext()) {

                Meeting meeting = appUserToRemoveOptional.get().getMeetingSet().iterator().next();
                Set<AppUser> participantList = meeting.getParticipantSet();

                for (AppUser user : participantList) {
                    user.getMeetingSet().remove(meeting);
                }
                Hall hall = meeting.getHall();
                hall.getMeetingSet().remove(meeting);
                meetingRepository.delete(meeting);
                hallRepository.save(hall);

            }
            appUserRepository.delete(appUserToRemoveOptional.get());
            return true;
        }
        return false;
    }

    public boolean removeEm(Long id) {
        Optional<AppUser> appUserToRemoveOptional = appUserRepository.findById(id);
        Optional<EmployeeServices> employeeServicesOptional = employeeServicesRepository.findById(appUserToRemoveOptional.get().getEmployeeServices().getId());
        Optional<WorkingHours> workingHoursOptional = workingHoursRepository.findById(appUserToRemoveOptional.get().getEmployeeServices().getWorkingHours().getId());

        if (appUserToRemoveOptional.isPresent()) {
            workingHoursRepository.delete(workingHoursOptional.get());
            while (employeeServicesOptional.get().getServiceTypes().iterator().hasNext()){
                ServiceType serviceType = employeeServicesOptional.get().getServiceTypes().iterator().next();
                employeeServicesOptional.get().getServiceTypes().remove(serviceType);
            }
            employeeServicesRepository.delete(employeeServicesOptional.get());
            while (appUserToRemoveOptional.get().getMeetingSet().iterator().hasNext()) {

                Meeting meeting = appUserToRemoveOptional.get().getMeetingSet().iterator().next();
                Set<AppUser> participantList = meeting.getParticipantSet();

                for (AppUser user : participantList) {
                    user.getMeetingSet().remove(meeting);
                }
                Hall hall = meeting.getHall();
                hall.getMeetingSet().remove(meeting);
                meetingRepository.delete(meeting);
                hallRepository.save(hall);

            }
            appUserRepository.delete(appUserToRemoveOptional.get());
            return true;
        }
        return false;

    }


    public boolean updateE(Long id, EmployeeUpdateAllDto employeeUpdateAllDto) {
        Optional<AppUser> appUserOptional = appUserRepository.findById(id);
        if (appUserOptional.isPresent()) {
            AppUser clientToUpdate = appUserOptional.get();
            if (bCryptPasswordEncoder.matches(employeeUpdateAllDto.getOldPassword(), clientToUpdate.getPassword())) {
                clientToUpdate.setPassword(bCryptPasswordEncoder.encode(employeeUpdateAllDto.getNewPassword()));
                clientToUpdate.setName(employeeUpdateAllDto.getName());
                clientToUpdate.setSurname(employeeUpdateAllDto.getSurname());
                clientToUpdate.setEmail(employeeUpdateAllDto.getEmail());
                clientToUpdate.setPhoneNumber(employeeUpdateAllDto.getPhoneNumber());

                Optional<WorkingHours> workingHoursOptional = workingHoursRepository.findById(clientToUpdate.getEmployeeServices().getWorkingHours().getId());
                if (workingHoursOptional.isPresent()) {
                    WorkingHours workingHours = workingHoursOptional.get();
                    workingHours.setStartingHour(employeeUpdateAllDto.getStartingHour());
                    workingHours.setHowManyHours(employeeUpdateAllDto.getHowManyHours());
                    workingHoursRepository.save(workingHours);
                    appUserRepository.save(clientToUpdate);
                }
                return true;
            }
            return false;

        }
        return false;

    }


}
