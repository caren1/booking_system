package com.javagda17.sda.booking.booking_system.configuration;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.format.DateTimeFormatter;

@Configuration
public class BasicConfiguration extends WebMvcAutoConfiguration {

    @Bean
    public DateTimeFormatter formDateTimeFormatter(){
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
