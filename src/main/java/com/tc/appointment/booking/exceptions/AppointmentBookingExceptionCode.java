package com.tc.appointment.booking.exceptions;

/**
 *
 */
public enum AppointmentBookingExceptionCode {

    CLINIC_NOT_EXIST_EXCEPTION(1000, "Clinic does not exist "),
    CUSTOMER_NOT_EXIST_EXCEPTION(1001, "Customer does not exist "),
    SERVICE_NOT_EXIST_EXCEPTION(1002, "Service does not exist "),
    TIMINGS_NOT_EXIST_FOR_SERVICE_EXCEPTION(1003, "Timing for Service does not exist "),
    BOOKING_ALREADY_EXIST_EXCEPTION(1004, "Booking already exist in for same request"),
    BOOKING_TIME_OVERLAP_EXCEPTION(1005, "Booking time already booked"),
    BOOKING_DOES_NOT_EXIST(1006, "Booking does not exist for requested id");

    private final int errorCode;
    private final String errorMessage;

    AppointmentBookingExceptionCode(final int errorCode, final String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
