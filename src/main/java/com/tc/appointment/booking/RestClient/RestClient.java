package com.tc.appointment.booking.RestClient;

import com.tc.appointment.booking.models.Timings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;

@Component
public class RestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);
    @Autowired
    RestTemplate restTemplate;
    @Value("${rest.client.key}")
    private String headerKey;
    @Value("${rest.client.value}")
    private String headerValue;
    @Value("${base.uri}")
    private String baseUri;

    public Object executeRestQueryToGetAllClinic(String uriAndId, Class c) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.set(headerKey, headerValue);
            HttpEntity<String> entity = new HttpEntity<String>(headers);
            return restTemplate.exchange(baseUri + uriAndId, HttpMethod.GET, entity, c).getBody();
        } catch (Exception e) {
            LOGGER.error("Error Occured while making rest call to uri {}, Error {}", baseUri + uriAndId, e.getMessage());
            return null;
        }
    }

    public Timings[] executeRestQueryToGetAllServicesTimings(String clinic, String service, LocalDate date) {
        String uri = baseUri + "/clinics/" + clinic + "/services/" + service + "/timeslots/" + date.toString();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.set(headerKey, headerValue);
            HttpEntity<String> entity = new HttpEntity<String>(headers);
            ResponseEntity<Timings[]> response = restTemplate.exchange(uri, HttpMethod.GET, entity, Timings[].class);
            return response.getBody();
        } catch (Exception e) {
            LOGGER.error("Error Occured while making rest call to uri {}, Error {}", uri, e);
            return null;
        }
    }

}
