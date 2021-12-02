package com.tc.appointment.booking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * Main class to start the spring application
 */
@SpringBootApplication
public class TherapieClinicAppointmentsBookingSystem {

    private static final Logger LOGGER = LoggerFactory.getLogger(TherapieClinicAppointmentsBookingSystem.class);

    public static void main(String[] args) {

        SpringApplication.run(TherapieClinicAppointmentsBookingSystem.class);

    }

}
