package com.tc.appointment.booking.models;

import lombok.*;

/**
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Clinic {

    private String clinicId;
    private String city;
    private String country;
    private String streetAddress;
}
