package com.tc.appointment.booking.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

/**
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonProperty(access = WRITE_ONLY)
    private int id;
    @Column(name = "customer_id")
    private String customerId;
    @Column(name = "service_id")
    private String serviceId;
    @Column(name = "clinic_id")
    @JsonProperty(access = WRITE_ONLY)
    private String clinicId;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "start_time")
    private String startTime;
    @Column(name = "end_time")
    private String endTime;
}
