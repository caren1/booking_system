package com.javagda17.sda.booking.booking_system.service;

import com.javagda17.sda.booking.booking_system.model.AppUser;
import com.javagda17.sda.booking.booking_system.model.Hall;
import com.javagda17.sda.booking.booking_system.model.Meeting;
import com.javagda17.sda.booking.booking_system.model.ServiceType;
import com.javagda17.sda.booking.booking_system.model.dto.MeetingDto;
import com.javagda17.sda.booking.booking_system.model.dto.MeetingUpdateDto;
import com.javagda17.sda.booking.booking_system.respository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@SuppressWarnings("ALL")
@Service
public class MeetingService {
    @Autowired
    private DateTimeFormatter formDateTimeFormatter;

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
    private MeetingRepository meetingRepository;

    @Autowired
    private HallRepository hallRepository;


    public boolean addMeeting(MeetingDto meetingDto, String serviceType, String NIP, String hallName) {
        Optional<AppUser> optionalClient = appUserRepository.findAppUserByCompany_NIP(NIP);
        AppUser client;
        if (!optionalClient.isPresent()) {
            return false;
        }

        LocalDateTime dateTimeStart = LocalDateTime.parse(meetingDto.getStart(), formDateTimeFormatter);
        LocalDateTime dateTimeEnd = LocalDateTime.parse(meetingDto.getEnd(), formDateTimeFormatter);

        client = optionalClient.get();
        Hall hall = hallRepository.findByHallName(hallName).get();
        Meeting meeting = new Meeting();
        meeting.setTitle(meetingDto.getTitle());
        meeting.setStart(dateTimeStart);
        meeting.setEnd(dateTimeEnd);
        meeting.setGuestsQuantity(meetingDto.getGuestsQuantity());
        meeting.setHall(hall);

        ServiceType serviceType1 = new ServiceType();


        if (serviceType.equals("lawyer")) {
            serviceType1 = serviceTypeRepository.findByName("Spotkanie z prawnikiem").get();

        } else if (serviceType.equals("accountant")) {
            serviceType1 = serviceTypeRepository.findByName("Spotkanie z ksiegowa").get();

        } else if (serviceType.equals("hallBooking")) {
            serviceType1 = serviceTypeRepository.findByName("Wynajem sali").get();
        }

        AppUser employeeToAdd = new AppUser();
        List<AppUser> employeeList = appUserRepository.getAllByEmployeeServicesIsNotNull();
        if (serviceType1.getName().equals("Spotkanie z prawnikiem")) {

            for (AppUser employee : employeeList) {
                if (employee.getEmployeeServices().getServiceTypes().iterator().next().getName().equals("Spotkanie z prawnikiem")) {
                    employeeToAdd = employee;
                }
            }
        } else if (serviceType1.getName().equals("Spotkanie z ksiegowa")) {
            for (AppUser employee : employeeList) {
                if (employee.getEmployeeServices().getServiceTypes().iterator().next().getName().equals("Spotkanie z ksiegowa")) {
                    employeeToAdd = employee;
                }
            }
        } else if (serviceType1.getName().equals("Wynajem sali")) {
            meeting.getParticipantSet().add(client);

            meetingRepository.save(meeting);
            client.getMeetingSet().add(meeting);
            hall.getMeetingSet().add(meeting);
            hallRepository.save(hall);
            appUserRepository.save(client);
            meeting.setServiceType(serviceType1);
            meetingRepository.save(meeting);
            return true;


        }
        try {
            meeting.getParticipantSet().add(employeeToAdd);
            meeting.getParticipantSet().add(client);
            meetingRepository.save(meeting);

            employeeToAdd.getMeetingSet().add(meeting);
            client.getMeetingSet().add(meeting);
            appUserRepository.save(employeeToAdd);
            appUserRepository.save(client);

            hall.getMeetingSet().add(meeting);
            hallRepository.save(hall);
            meeting.setServiceType(serviceType1);
            meetingRepository.save(meeting);


        } catch (ConstraintViolationException cve) {
            return false;
        }

        return true;
    }


    public boolean update(Long id, MeetingUpdateDto updatedMeetingDto) {

        Meeting meetingToUpdate = meetingRepository.findById(id).get();
        Set<AppUser> participantSet = meetingToUpdate.getParticipantSet();
        AppUser clientFromForm = appUserRepository.findAppUserByCompany_NIP(updatedMeetingDto.getNIP()).get();

        LocalDateTime dateTimeStart = LocalDateTime.parse(updatedMeetingDto.getStart(), formDateTimeFormatter);
        LocalDateTime dateTimeEnd = LocalDateTime.parse(updatedMeetingDto.getEnd(), formDateTimeFormatter);
        AppUser client = new AppUser();
        AppUser employee = new AppUser();
        ServiceType serviceType1 = new ServiceType();
        Hall newHall = hallRepository.findByHallName(updatedMeetingDto.getHallName()).get();
        Hall hall = hallRepository.findByHallName(meetingToUpdate.getHall().getHallName()).get();
        AppUser newEmployee = new AppUser();
        AppUser newClient = new AppUser();

        for (AppUser appUser : participantSet) {
            if (appUser.getCompany() != null) {
                client = appUser;
            } else if (appUser.getEmployeeServices() != null) {
                employee = appUser;
            }
        }
        if (updatedMeetingDto.getServiceType().equals("lawyer")) {
            serviceType1 = serviceTypeRepository.findByName("Spotkanie z prawnikiem").get();

        } else if (updatedMeetingDto.getServiceType().equals("accountant")) {
            serviceType1 = serviceTypeRepository.findByName("Spotkanie z ksiegowa").get();

        } else if (updatedMeetingDto.getServiceType().equals("hallBooking")) {
            serviceType1 = serviceTypeRepository.findByName("Wynajem sali").get();
        }

        List<AppUser> employeeList = appUserRepository.getAllByEmployeeServicesIsNotNull();

        if (meetingToUpdate.getServiceType() != serviceType1) {
            if (!meetingToUpdate.getServiceType().getName().equals("Wynajem sali")) {
                meetingToUpdate.getParticipantSet().remove(employee);
                employee.getMeetingSet().remove(meetingToUpdate);
                appUserRepository.save(employee);
            }

            if (serviceType1.getName().equals("Spotkanie z prawnikiem")) {
                for (AppUser emp : employeeList) {
                    if (emp.getEmployeeServices().getServiceTypes().iterator().next().getName().equals("Spotkanie z prawnikiem")) {
                        newEmployee = emp;
                        meetingToUpdate.getParticipantSet().add(newEmployee);
                        newEmployee.getMeetingSet().add(meetingToUpdate);
                        appUserRepository.save(newEmployee);
                        meetingRepository.save(meetingToUpdate);
                    }
                }
            } else if (serviceType1.getName().equals("Spotkanie z ksiegowa")) {
                for (AppUser emp : employeeList) {
                    if (emp.getEmployeeServices().getServiceTypes().iterator().next().getName().equals("Spotkanie z ksiegowa")) {
                        newEmployee = emp;
                        meetingToUpdate.getParticipantSet().add(newEmployee);
                        newEmployee.getMeetingSet().add(meetingToUpdate);
                        appUserRepository.save(newEmployee);
                        meetingRepository.save(meetingToUpdate);
                    }
                }

            } else if (serviceType1.getName().equals("Wynajem sali")) {

            }

        }
        if (!meetingToUpdate.getParticipantSet().contains(clientFromForm)) {
            meetingToUpdate.getParticipantSet().remove(client);
            client.getMeetingSet().remove(meetingToUpdate);
            meetingToUpdate.getParticipantSet().add(newClient);
            newClient.getMeetingSet().add(meetingToUpdate);
            appUserRepository.save(newClient);
            appUserRepository.save(client);

        }
        if (!hall.equals(newHall.getHallName())) {
            meetingToUpdate.setHall(newHall);
            hall.getMeetingSet().remove(meetingToUpdate);
            newHall.getMeetingSet().add(meetingToUpdate);
            hallRepository.save(newHall);
            hallRepository.save(hall);
        }

        try {
            meetingToUpdate.setTitle(updatedMeetingDto.getTitle());
            meetingToUpdate.setStart(dateTimeStart);
            meetingToUpdate.setEnd(dateTimeEnd);
            meetingToUpdate.setGuestsQuantity(updatedMeetingDto.getGuestsQuantity());
            meetingToUpdate.setServiceType(serviceType1);

            meetingRepository.save(meetingToUpdate);

        } catch (ConstraintViolationException cve) {
            return false;
        }

        return true;
    }


    public List<Meeting> getAllUsers() {
        return meetingRepository.findAll();
    }

    public Optional<Meeting> getMeetingById(Long id) {
        return meetingRepository.findById(id);
    }

    public void remove(Long id) {
    }


}
