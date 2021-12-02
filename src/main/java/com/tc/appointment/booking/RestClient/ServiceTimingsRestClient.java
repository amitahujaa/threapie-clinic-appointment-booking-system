package com.tc.appointment.booking.RestClient;

import com.tc.appointment.booking.models.Timings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ServiceTimingsRestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceTimingsRestClient.class);

    @Autowired
    RestClient restClient;

    public Timings[] executeRestQueryToGetAllServices(String clinicId, String serviceId, LocalDate date) {
        return restClient.executeRestQueryToGetAllServicesTimings(clinicId, serviceId, date);

    }
}
