package com.tc.appointment.booking.RestClient;

import com.tc.appointment.booking.models.Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ServiceRestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRestClient.class);

    @Autowired
    RestClient restClient;

    public Optional<Services> checkIfServiceExist(String serviceId) {
        LOGGER.info("serviceId Id - " + serviceId);
        return Optional.of((Services) restClient.executeRestQueryToGetAllClinic("/services/" + serviceId, Services.class));
    }
}
