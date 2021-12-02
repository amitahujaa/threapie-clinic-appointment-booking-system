package com.tc.appointment.booking.services;

import com.tc.appointment.booking.dao.BookingAppointmentReposiroty;
import com.tc.appointment.booking.exceptions.AppointBookingVerificationException;
import com.tc.appointment.booking.exceptions.AppointmentBookingExceptionCode;
import com.tc.appointment.booking.models.Booking;
import com.tc.appointment.booking.validations.ValidateOverlappingBooking;
import com.tc.appointment.booking.validations.ValidateServiceTimigsForBooking;
import com.tc.appointment.booking.validations.VerifyBooking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingAppointmentServiceImpl implements BookingAppointmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingAppointmentServiceImpl.class);

    @Autowired
    ValidateServiceTimigsForBooking validateServiceTimigsForBooking;

    @Autowired
    ValidateOverlappingBooking validateOverlappingBooking;

    @Autowired
    VerifyBooking verifyBooking;

    @Autowired
    BookingAppointmentReposiroty bookingAppointmentReposiroty;

    public void addNewBooking(Booking booking) throws AppointBookingVerificationException {
        validate(booking);
        bookingAppointmentReposiroty.save(booking);
    }

    private void validate(Booking booking) throws AppointBookingVerificationException {
        validateOverlappingBooking.validateForBookingAlreadyExist(booking);
        verifyBooking.verifyClinicForBooking(booking);
        verifyBooking.verifyCustomerForBooking(booking);
        verifyBooking.verifyServiceForBooking(booking);
        validateServiceTimigsForBooking.ValidateServiceTimingForBooking(booking);
        validateOverlappingBooking.validateForOverlappingBookingTime(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingAppointmentReposiroty.findAll();
    }

    public Booking getBooking(int bookingId) throws AppointBookingVerificationException {
        Optional<Booking> booking = bookingAppointmentReposiroty.findById(bookingId);
        if (booking.isPresent()) {
            return booking.get();
        }
        throw new AppointBookingVerificationException(AppointmentBookingExceptionCode.BOOKING_DOES_NOT_EXIST,
                AppointmentBookingExceptionCode.BOOKING_DOES_NOT_EXIST.getErrorMessage(), new IllegalArgumentException());
    }

    public void deleteBooking(int bookingId) {
        bookingAppointmentReposiroty.deleteById(bookingId);
    }
}
