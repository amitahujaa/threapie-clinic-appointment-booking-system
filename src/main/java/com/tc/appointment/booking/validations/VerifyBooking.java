package com.tc.appointment.booking.validations;

import com.tc.appointment.booking.RestClient.ClinicRestClient;
import com.tc.appointment.booking.RestClient.CustomerRestClient;
import com.tc.appointment.booking.RestClient.ServiceRestClient;
import com.tc.appointment.booking.RestClient.ServiceTimingsRestClient;
import com.tc.appointment.booking.exceptions.AppointBookingVerificationException;
import com.tc.appointment.booking.exceptions.AppointmentBookingExceptionCode;
import com.tc.appointment.booking.models.Booking;
import com.tc.appointment.booking.models.Clinic;
import com.tc.appointment.booking.models.Customer;
import com.tc.appointment.booking.models.Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 *
 */
@Component
public class VerifyBooking {

    private static final Logger LOGGER = LoggerFactory.getLogger(VerifyBooking.class);

    @Autowired
    ClinicRestClient clinicRestClient;

    @Autowired
    CustomerRestClient customerRestClient;

    @Autowired
    ServiceRestClient serviceRestClient;

    public Clinic verifyClinicForBooking(Booking booking) throws AppointBookingVerificationException {
        Clinic clinic = null;
        try {
            clinic = clinicRestClient.checkIfClinicExist(booking.getClinicId()).orElseGet(Clinic::new);
            if (clinic.getClinicId() == null) {
                throw new AppointBookingVerificationException(AppointmentBookingExceptionCode.CLINIC_NOT_EXIST_EXCEPTION,
                        AppointmentBookingExceptionCode.CLINIC_NOT_EXIST_EXCEPTION.getErrorMessage(), new IllegalArgumentException());
            }
        } catch (Exception e) {
            LOGGER.error("Exception occured {}", e);
            throw new AppointBookingVerificationException(AppointmentBookingExceptionCode.CLINIC_NOT_EXIST_EXCEPTION,
                    AppointmentBookingExceptionCode.CLINIC_NOT_EXIST_EXCEPTION.getErrorMessage(), e);
        }
        return clinic;
    }

    public Customer verifyCustomerForBooking(Booking booking) throws AppointBookingVerificationException {
        Customer customer = null;
        try {
            customer = customerRestClient.checkIfCustomerExist(booking.getCustomerId()).orElseGet(Customer::new);
            if (customer.getCustomerId() == null) {
                LOGGER.error("customer does not exist for customer id{}", booking.getCustomerId());
                throw new AppointBookingVerificationException(AppointmentBookingExceptionCode.CUSTOMER_NOT_EXIST_EXCEPTION,
                        AppointmentBookingExceptionCode.CUSTOMER_NOT_EXIST_EXCEPTION.getErrorMessage(), new IllegalArgumentException());
            }
        } catch (Exception e) {
            LOGGER.error("Exception occured {}", e);
            throw new AppointBookingVerificationException(AppointmentBookingExceptionCode.CUSTOMER_NOT_EXIST_EXCEPTION,
                    AppointmentBookingExceptionCode.CUSTOMER_NOT_EXIST_EXCEPTION.getErrorMessage(), e);
        }
        return customer;
    }

    public Services verifyServiceForBooking(Booking booking) throws AppointBookingVerificationException {
        Services services = null;
        try {
            services = serviceRestClient.checkIfServiceExist(booking.getServiceId()).orElseGet(Services::new);
            if (services.getServiceId() == null) {
                LOGGER.warn("service does not exist for service id", booking.getServiceId());
                throw new AppointBookingVerificationException(AppointmentBookingExceptionCode.SERVICE_NOT_EXIST_EXCEPTION,
                        AppointmentBookingExceptionCode.SERVICE_NOT_EXIST_EXCEPTION.getErrorMessage(), new IllegalArgumentException());
            }
            LocalTime time = LocalTime.parse(booking.getStartTime().replaceAll("T", "")).plus(services.getDurationInMinutes(), ChronoUnit.MINUTES);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            booking.setEndTime("T" + time.format(dtf));
        } catch (Exception e) {
            LOGGER.error("Exception occured {}", e);
            throw new AppointBookingVerificationException(AppointmentBookingExceptionCode.SERVICE_NOT_EXIST_EXCEPTION,
                    AppointmentBookingExceptionCode.SERVICE_NOT_EXIST_EXCEPTION.getErrorMessage(), e);
        }
        return services;
    }


}
