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
public class Customer {
    private String customerId;
    private String firstName;
    private String lastName;
    private int mobile;
}
