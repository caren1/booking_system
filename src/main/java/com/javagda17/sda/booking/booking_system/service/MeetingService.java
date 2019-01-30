package com.javagda17.sda.booking.booking_system.service;

import com.javagda17.sda.booking.booking_system.model.AppUser;
import com.javagda17.sda.booking.booking_system.model.Hall;
import com.javagda17.sda.booking.booking_system.model.Meeting;
import com.javagda17.sda.booking.booking_system.model.ServiceType;
import com.javagda17.sda.booking.booking_system.model.dto.MeetingDto;
import com.javagda17.sda.booking.booking_system.respository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


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

        if (serviceType1.getName().equals("Spotkanie z prawnikiem")) {
            List<AppUser> employeeList = appUserRepository.getAllByEmployeeServicesIsNotNull();
            for (AppUser employee : employeeList) {
                if (employee.getEmployeeServices().getServiceTypes().iterator().next().getName().equals("Spotkanie z prawnikiem")) {

                    meeting.getParticipantSet().add(employee);
                    meeting.getParticipantSet().add(client);
                    meetingRepository.save(meeting);

                    employee.getMeetingSet().add(meeting);
                    client.getMeetingSet().add(meeting);
                    appUserRepository.save(employee);
                    appUserRepository.save(client);

                    hall.getMeetingSet().add(meeting);
                    hallRepository.save(hall);
                    meeting.setServiceType(serviceType1);
                }
            }
        } else if (serviceType1.getName().equals("Spotkanie z ksiegowa")) {
            List<AppUser> employeeList = appUserRepository.getAllByEmployeeServicesIsNotNull();
            for (AppUser employee : employeeList) {
                if (employee.getEmployeeServices().getServiceTypes().iterator().next().getName().equals("Spotkanie z ksiegowa")) {

                    meeting.getParticipantSet().add(employee);
                    meeting.getParticipantSet().add(client);
                    meetingRepository.save(meeting);

                    employee.getMeetingSet().add(meeting);
                    client.getMeetingSet().add(meeting);
                    appUserRepository.save(employee);
                    appUserRepository.save(client);

                    hall.getMeetingSet().add(meeting);
                    hallRepository.save(hall);
                    meeting.setServiceType(serviceType1);
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
        }


        //todo: dodac Klienta na podstawie NIP

        //todo: do Klienta dodac meeting

        try {
            meetingRepository.save(meeting);


        } catch (ConstraintViolationException cve) {
            return false;
        }

        return true;
    }

    public List<Meeting> getAllUsers() {
        return meetingRepository.findAll();
    }
}
