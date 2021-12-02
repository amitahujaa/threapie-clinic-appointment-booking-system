package com.tc.appointment.booking.validations;

import com.tc.appointment.booking.RestClient.ClinicRestClient;
import com.tc.appointment.booking.RestClient.CustomerRestClient;
import com.tc.appointment.booking.RestClient.ServiceRestClient;
import com.tc.appointment.booking.exceptions.AppointBookingVerificationException;
import com.tc.appointment.booking.exceptions.AppointmentBookingExceptionCode;
import com.tc.appointment.booking.models.Booking;
import com.tc.appointment.booking.models.Clinic;
import com.tc.appointment.booking.models.Customer;
import com.tc.appointment.booking.models.Services;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TestVerifyBooking {

    @InjectMocks
    VerifyBooking verifyBooking;

    @Mock
    ClinicRestClient clinicRestClient;

    @Mock
    CustomerRestClient customerRestClient;

    @Mock
    ServiceRestClient serviceRestClient;

    @Test
    public void testVerifyClinicForBooking() throws AppointBookingVerificationException {
        when(clinicRestClient.checkIfClinicExist(any())).thenReturn(Optional.of(new Clinic("clinicid", "city", "country", "street address")));
        Clinic c = verifyBooking.verifyClinicForBooking(new Booking());
        Assertions.assertEquals("clinicid", c.getClinicId());
    }

    @Test
    public void testVerifyClinicForBooking2() throws AppointBookingVerificationException {
        AppointBookingVerificationException thrown = Assertions.assertThrows(AppointBookingVerificationException.class, () -> {
            when(clinicRestClient.checkIfClinicExist(any())).thenReturn(Optional.of(new Clinic()));
            verifyBooking.verifyClinicForBooking(new Booking());
        });
        assertTrue(thrown.getErrorCode() == AppointmentBookingExceptionCode.CLINIC_NOT_EXIST_EXCEPTION.getErrorCode());
    }

    @Test
    public void testVerifyCustomerForBooking() throws AppointBookingVerificationException {
        when(customerRestClient.checkIfCustomerExist(any())).thenReturn(Optional.of(new Customer("customerId", "A", "A", 1234)));
        Customer customer = verifyBooking.verifyCustomerForBooking(new Booking());
        Assertions.assertEquals("customerId", customer.getCustomerId());
    }

    @Test
    public void testVerifyCustomerForBooking2() throws AppointBookingVerificationException {
        AppointBookingVerificationException thrown = Assertions.assertThrows(AppointBookingVerificationException.class, () -> {
            when(customerRestClient.checkIfCustomerExist(any())).thenReturn(Optional.of(new Customer()));
            verifyBooking.verifyCustomerForBooking(new Booking());
        });
        assertTrue(thrown.getErrorCode() == AppointmentBookingExceptionCode.CUSTOMER_NOT_EXIST_EXCEPTION.getErrorCode());
    }

    @Test
    public void testVerifyServiceForBooking() throws AppointBookingVerificationException {
        Booking booking1 = new Booking(1, "customer1", "service1", "clinic1", LocalDate.now(), "T09:00:00", "T09:30:00");
        when(serviceRestClient.checkIfServiceExist(any())).thenReturn(Optional.of(new Services("serviceId", "A", 12.20, 30)));
        Services services = verifyBooking.verifyServiceForBooking(booking1);
        Assertions.assertEquals("serviceId", services.getServiceId());
    }

    @Test
    public void testVerifyServiceForBooking2() throws AppointBookingVerificationException {
        AppointBookingVerificationException thrown = Assertions.assertThrows(AppointBookingVerificationException.class, () -> {
            when(serviceRestClient.checkIfServiceExist(any())).thenReturn(Optional.of(new Services()));
            verifyBooking.verifyServiceForBooking(new Booking());
        });
        assertTrue(thrown.getErrorCode() == AppointmentBookingExceptionCode.SERVICE_NOT_EXIST_EXCEPTION.getErrorCode());
    }
}
