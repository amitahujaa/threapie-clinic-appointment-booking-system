package com.tc.appointment.booking.dao;

import com.tc.appointment.booking.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 *
 */
public interface BookingAppointmentReposiroty extends JpaRepository<Booking, Integer> {
    /**
     * @param customerId
     * @param clinicId
     * @param serviceId
     * @param bookingDate
     * @param startTime
     * @return
     */
    Booking findByCustomerIdAndClinicIdAndServiceIdAndDateAndStartTime(String customerId, String clinicId, String serviceId, LocalDate bookingDate, String startTime);

    /**
     * @param clinicId
     * @param serviceId
     * @param bookingDate
     * @return
     */
    List<Booking> findByClinicIdAndServiceIdAndDate(String clinicId, String serviceId, LocalDate bookingDate);
}
