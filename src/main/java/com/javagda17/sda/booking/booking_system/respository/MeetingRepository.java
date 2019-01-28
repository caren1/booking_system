package com.javagda17.sda.booking.booking_system.respository;

import com.javagda17.sda.booking.booking_system.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

}
