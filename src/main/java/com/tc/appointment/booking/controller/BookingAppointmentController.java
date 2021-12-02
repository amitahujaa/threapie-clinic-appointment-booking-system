package com.tc.appointment.booking.controller;

import com.tc.appointment.booking.exceptions.AppointBookingVerificationException;
import com.tc.appointment.booking.models.Booking;
import com.tc.appointment.booking.models.ResponseMessage;
import com.tc.appointment.booking.services.BookingAppointmentService;
import com.tc.appointment.booking.utils.Statuses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
@RestController
public class BookingAppointmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingAppointmentController.class);

    @Autowired
    private BookingAppointmentService bookingAppointmentService;

    @PostMapping(value = "/bookings")
    @ResponseBody
    public ResponseEntity<ResponseMessage> addNewBooking(@RequestBody Booking booking) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            bookingAppointmentService.addNewBooking(booking);
            responseMessage.setStatus(Statuses.PASSED);
            responseMessage.setBooking(booking);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        } catch (AppointBookingVerificationException e) {
            LOGGER.error("Validation error code {}, error message {}, for booking {}", e.getErrorCode(), e.getErrorMessage(), booking.toString());
            responseMessage.setStatus(Statuses.FAILED);
            responseMessage.setStatusMessages(Arrays.asList(e.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        } catch (Exception e) {
            LOGGER.error("Error occured {}", e);
            responseMessage.setStatus(Statuses.FAILED);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        }
    }

    @GetMapping(value = "/bookings")
    public ResponseEntity<List<Booking>> getMyAllBookings() {
        List<Booking> optionalBooking = bookingAppointmentService.getAllBookings();
        if (optionalBooking.size() > 0) {
            return ResponseEntity.ok(optionalBooking);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/bookings/{id}")
    public ResponseEntity<ResponseMessage> getBooking(@PathVariable int id) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            responseMessage.setBooking(bookingAppointmentService.getBooking(id));
            return ResponseEntity.ok(responseMessage);
        } catch (AppointBookingVerificationException e) {
            LOGGER.error("Error, id not found {}, exception details {}", id, e);
            responseMessage.setStatus(Statuses.FAILED);
            responseMessage.setStatusMessages(Arrays.asList(e.getMessage()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        } catch (Exception e) {
            LOGGER.error("Error occured {}", e);
            responseMessage.setStatus(Statuses.FAILED);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }
    }

    @DeleteMapping(value = "/bookings/{id}")
    public ResponseEntity<ResponseMessage> getMyAllBookings(@PathVariable int id) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            bookingAppointmentService.deleteBooking(id);
            responseMessage.setStatus(Statuses.PASSED);
            return ResponseEntity.ok().body(responseMessage);
        } catch (Exception e) {
            LOGGER.error("Delete error, id not found {}, exception details {}", id, e);
            responseMessage.setStatus(Statuses.FAILED);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }
    }


}
