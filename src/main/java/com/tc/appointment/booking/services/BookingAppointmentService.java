package com.tc.appointment.booking.services;

import com.tc.appointment.booking.exceptions.AppointBookingVerificationException;
import com.tc.appointment.booking.models.Booking;

import java.util.List;

/**
 *
 */
public interface BookingAppointmentService {
    /**
     *
     * @param booking
     */
    void addNewBooking(Booking booking)throws AppointBookingVerificationException;

    /**
     *
     * @return
     */
    List<Booking> getAllBookings();

    /**
     *
     * @param bookingId
     * @return
     */
    Booking getBooking(int bookingId)throws AppointBookingVerificationException;

    /**
     *
     * @param bookingId
     */
    void deleteBooking(int bookingId);
}
