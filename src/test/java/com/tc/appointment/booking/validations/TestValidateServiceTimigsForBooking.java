package com.tc.appointment.booking.validations;

import com.tc.appointment.booking.RestClient.ServiceTimingsRestClient;
import com.tc.appointment.booking.exceptions.AppointBookingVerificationException;
import com.tc.appointment.booking.exceptions.AppointmentBookingExceptionCode;
import com.tc.appointment.booking.models.Booking;
import com.tc.appointment.booking.models.Customer;
import com.tc.appointment.booking.models.Timings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TestValidateServiceTimigsForBooking {

    @Mock
    ServiceTimingsRestClient serviceTimingsRestClient;

    @InjectMocks
    ValidateServiceTimigsForBooking validateServiceTimigsForBooking;

    @Test
    public void testValidateServiceTimingForBooking() throws AppointBookingVerificationException {
        Assertions.assertDoesNotThrow(() -> {
            Booking booking1 = new Booking(1, "customer1", "service1", "clinic1", LocalDate.now(), "T09:00:00", "T09:30:00");
            Timings[] timingss = new Timings[1];
            timingss[0] = new Timings("T08:00:00", "T12:30:00");
            when(serviceTimingsRestClient
                    .executeRestQueryToGetAllServices(any(), any(), any())).thenReturn(timingss);
            validateServiceTimigsForBooking.ValidateServiceTimingForBooking(booking1);
        });
    }

    @Test
    public void testValidateServiceTimingForBooking2() throws AppointBookingVerificationException {
        AppointBookingVerificationException thrown = Assertions.assertThrows(AppointBookingVerificationException.class, () -> {
            Booking booking1 = new Booking(1, "customer1", "service1", "clinic1", LocalDate.now(), "T07:00:00", "T07:30:00");
            Timings[] timingss = new Timings[1];
            timingss[0] = new Timings("T08:00:00","T12:30:00");
            when(serviceTimingsRestClient
                    .executeRestQueryToGetAllServices(any(),any(),any())).thenReturn(timingss);
            validateServiceTimigsForBooking.ValidateServiceTimingForBooking(booking1);
        });
        assertTrue(thrown.getErrorCode() == AppointmentBookingExceptionCode.TIMINGS_NOT_EXIST_FOR_SERVICE_EXCEPTION.getErrorCode());
    }

}
