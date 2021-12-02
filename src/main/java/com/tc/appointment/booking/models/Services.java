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
public class Services {
    private String serviceId;
    private String name;
    private double price;
    private int durationInMinutes;
}
