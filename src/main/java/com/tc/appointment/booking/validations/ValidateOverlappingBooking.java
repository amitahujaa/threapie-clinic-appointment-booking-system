package com.tc.appointment.booking.validations;

import com.tc.appointment.booking.dao.BookingAppointmentReposiroty;
import com.tc.appointment.booking.exceptions.AppointBookingVerificationException;
import com.tc.appointment.booking.exceptions.AppointmentBookingExceptionCode;
import com.tc.appointment.booking.models.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ValidateOverlappingBooking {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateOverlappingBooking.class);

    @Autowired
    BookingAppointmentReposiroty bookingAppointmentReposiroty;

    private static boolean isInInterval(String target, String start, String end) {
        return ((target.compareTo(start) >= 0)
                && (target.compareTo(end) <= 0));
    }

    public void validateForBookingAlreadyExist(Booking booking) throws AppointBookingVerificationException {
        Booking b1 = bookingAppointmentReposiroty
                .findByCustomerIdAndClinicIdAndServiceIdAndDateAndStartTime(booking.getCustomerId(),
                        booking.getClinicId(), booking.getServiceId(), booking.getDate(), booking.getStartTime());
        if (b1 != null) {
            throw new AppointBookingVerificationException(AppointmentBookingExceptionCode.BOOKING_ALREADY_EXIST_EXCEPTION,
                    AppointmentBookingExceptionCode.BOOKING_ALREADY_EXIST_EXCEPTION.getErrorMessage(), new IllegalArgumentException());
        }
    }

    public void validateForOverlappingBookingTime(Booking booking) throws AppointBookingVerificationException {
        List<Booking> bookings = bookingAppointmentReposiroty
                .findByClinicIdAndServiceIdAndDate(booking.getClinicId(), booking.getServiceId(), booking.getDate());
        if(bookings !=null) {
            List<Booking> list = bookings.stream().filter(booking1 -> {
                String startTimefromdb = booking1.getStartTime().replaceAll("T", "");
                String endTimefromdb = booking1.getEndTime().replaceAll("T", "");
                return isInInterval(booking.getStartTime().replaceAll("T", ""), startTimefromdb, endTimefromdb) ||
                        isInInterval(booking.getEndTime().replaceAll("T", ""), startTimefromdb, endTimefromdb);
            }).collect(Collectors.toList());
            if (list.size() > 0) {
                throw new AppointBookingVerificationException(AppointmentBookingExceptionCode.BOOKING_TIME_OVERLAP_EXCEPTION,
                        AppointmentBookingExceptionCode.BOOKING_TIME_OVERLAP_EXCEPTION.getErrorMessage(), new IllegalArgumentException());
            }
        }
    }

}
