package com.tc.appointment.booking.validations;

import com.tc.appointment.booking.dao.BookingAppointmentReposiroty;
import com.tc.appointment.booking.exceptions.AppointBookingVerificationException;
import com.tc.appointment.booking.models.Booking;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TestValidateOverlappingBooking {

    @InjectMocks
    ValidateOverlappingBooking validateOverlappingBooking;

    @Mock
    BookingAppointmentReposiroty bookingAppointmentReposiroty;

    @Test
    public void testValidateForBookingAlreadyExist_Exception() {
        Assertions.assertThrows(AppointBookingVerificationException.class, () -> {
            Booking booking = new Booking(1, "customer1", "service1", "clinic1", LocalDate.now(), "T10:55:00", "T10:55:00");
            when(bookingAppointmentReposiroty
                    .findByCustomerIdAndClinicIdAndServiceIdAndDateAndStartTime(any(), any(), any(), any(), any())).thenReturn(booking);
            validateOverlappingBooking.validateForBookingAlreadyExist(booking);
        });
    }

    @Test
    public void testValidateForBookingAlreadyExist() throws AppointBookingVerificationException {
        Booking booking = new Booking(1, "customer1", "service1", "clinic1", LocalDate.now(), "T10:55:00", "T10:55:00");
        when(bookingAppointmentReposiroty
                .findByCustomerIdAndClinicIdAndServiceIdAndDateAndStartTime(any(), any(), any(), any(), any())).thenReturn(null);
        validateOverlappingBooking.validateForBookingAlreadyExist(booking);
    }

    @Test
    public void testvalidateForOverlappingBookingTime() throws AppointBookingVerificationException {
        Assertions.assertThrows(AppointBookingVerificationException.class, () -> {
            Booking booking1 = new Booking(1, "customer1", "service1", "clinic1", LocalDate.now(), "T09:00:00", "T09:30:00");
            when(bookingAppointmentReposiroty
                    .findByClinicIdAndServiceIdAndDate(any(), any(), any())).thenReturn(Arrays.asList(booking1));
            Booking booking2 = new Booking(1, "customer1", "service1", "clinic1", LocalDate.now(), "T09:15:00", "T09:30:00");
            validateOverlappingBooking.validateForOverlappingBookingTime(booking2);

        });
    }

    @Test
    public void testvalidateForOverlappingBookingTime2() throws AppointBookingVerificationException {
        when(bookingAppointmentReposiroty
                .findByClinicIdAndServiceIdAndDate(any(), any(), any())).thenReturn(null);
        Booking booking2 = new Booking(1, "customer1", "service1", "clinic1", LocalDate.now(), "T09:15:00", "T09:30:00");
        validateOverlappingBooking.validateForOverlappingBookingTime(booking2);
    }

    @Test
    public void testvalidateForOverlappingBookingTime3() throws AppointBookingVerificationException {
        Booking booking1 = new Booking(1, "customer1", "service1", "clinic1", LocalDate.now(), "T10:00:00", "T10:30:00");
        when(bookingAppointmentReposiroty
                .findByClinicIdAndServiceIdAndDate(any(), any(), any())).thenReturn(null);
        Booking booking2 = new Booking(1, "customer1", "service1", "clinic1", LocalDate.now(), "T09:15:00", "T09:30:00");
        validateOverlappingBooking.validateForOverlappingBookingTime(booking2);
    }
}
