package com.tc.appointment.booking.RestClient;

import com.tc.appointment.booking.models.Clinic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ClinicRestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClinicRestClient.class);

    @Autowired
    RestClient restClient;

    public Optional<Clinic> checkIfClinicExist(String clinicId) {
        LOGGER.info("Clinic Id - " + clinicId);
        return Optional.of((Clinic) restClient.executeRestQueryToGetAllClinic("/clinics/" + clinicId, Clinic.class));
    }


}
