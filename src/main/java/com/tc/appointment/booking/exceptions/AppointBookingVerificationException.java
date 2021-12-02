package com.tc.appointment.booking.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class AppointBookingVerificationException extends Exception {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppointBookingVerificationException.class);

    private final int errorCode;
    private final List<String> errorMessage;

    public AppointBookingVerificationException(final AppointmentBookingExceptionCode errorCode,
                                               final List<String> errorMessage, final Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode.getErrorCode();
        this.errorMessage = errorMessage;
    }

    public AppointBookingVerificationException(final AppointmentBookingExceptionCode errorCode,
                                               String errorMessage, final Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode.getErrorCode();
        this.errorMessage = Arrays.asList(errorMessage);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public List<String> getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String getMessage() {
        return String.format("errorCode: '%s', errorMessage: '%s'", errorCode, errorMessage);
    }

    @Override
    public String toString() {
        return "AppointBookingVerificationException{" +
                "errorCode=" + errorCode +
                ", errorMessage=" + errorMessage +
                '}';
    }
}
