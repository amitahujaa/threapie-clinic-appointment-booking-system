package com.tc.appointment.booking.validations;

import com.tc.appointment.booking.RestClient.ServiceTimingsRestClient;
import com.tc.appointment.booking.exceptions.AppointBookingVerificationException;
import com.tc.appointment.booking.exceptions.AppointmentBookingExceptionCode;
import com.tc.appointment.booking.models.Booking;
import com.tc.appointment.booking.models.Timings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ValidateServiceTimigsForBooking {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateServiceTimigsForBooking.class);

    @Autowired
    ServiceTimingsRestClient serviceTimingsRestClient;

    public void ValidateServiceTimingForBooking(Booking booking) throws AppointBookingVerificationException {
        try {
            Timings[] timingss = serviceTimingsRestClient
                    .executeRestQueryToGetAllServices(booking.getClinicId(), booking.getServiceId(), booking.getDate());
            List<Timings> list = Arrays.stream(timingss).filter(timing -> {
                String startTimes = timing.getStartTime().replaceAll("T", "");
                String endtimes = timing.getEndTime().replaceAll("T", "");
                LOGGER.info("Amit -- " + startTimes + " -- " + endtimes);
                return isInInterval(booking.getStartTime().replaceAll("T", ""), startTimes, endtimes) &&
                        isInInterval(booking.getEndTime().replaceAll("T", ""), startTimes, endtimes);
            }).collect(Collectors.toList());
            if (list.size() == 0) {
                throw new AppointBookingVerificationException(AppointmentBookingExceptionCode.TIMINGS_NOT_EXIST_FOR_SERVICE_EXCEPTION,
                        AppointmentBookingExceptionCode.TIMINGS_NOT_EXIST_FOR_SERVICE_EXCEPTION.getErrorMessage(),
                        new IllegalArgumentException());
            }
        } catch (Exception e) {
            LOGGER.error("Exception occured {}", e);
            throw new AppointBookingVerificationException(AppointmentBookingExceptionCode.TIMINGS_NOT_EXIST_FOR_SERVICE_EXCEPTION,
                    AppointmentBookingExceptionCode.TIMINGS_NOT_EXIST_FOR_SERVICE_EXCEPTION.getErrorMessage(), e);
        }
    }

    private static boolean isInInterval(String target, String start, String end) {
        return ((target.compareTo(start) >= 0)
                && (target.compareTo(end) <= 0));
    }

}
