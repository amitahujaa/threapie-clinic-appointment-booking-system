package com.tc.appointment.booking.configuration;

import com.tc.appointment.booking.RestClient.*;
import com.tc.appointment.booking.validations.ValidateServiceTimigsForBooking;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 *
 */
@Configuration
public class AppConfiguration {

    @Bean
    public RestClient restClientConfiguration() {
        return new RestClient();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ValidateServiceTimigsForBooking validate() {
        return new ValidateServiceTimigsForBooking();
    }

    @Bean
    public ClinicRestClient clinicRest() {
        return new ClinicRestClient();
    }

    @Bean
    public CustomerRestClient customerRest() {
        return new CustomerRestClient();
    }

    @Bean
    public ServiceRestClient serviceRest() {
        return new ServiceRestClient();
    }

    @Bean
    public ServiceTimingsRestClient serviceTimingsRest() {
        return new ServiceTimingsRestClient();
    }

}
