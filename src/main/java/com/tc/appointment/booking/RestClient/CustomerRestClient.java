package com.tc.appointment.booking.RestClient;

import com.tc.appointment.booking.models.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomerRestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerRestClient.class);

    @Autowired
    RestClient restClient;

    public Optional<Customer> checkIfCustomerExist(String customerId){
        LOGGER.info("customerId Id - " + customerId);
        return Optional.of((Customer) restClient.executeRestQueryToGetAllClinic("/customers/"+customerId, Customer.class));
    }
}
