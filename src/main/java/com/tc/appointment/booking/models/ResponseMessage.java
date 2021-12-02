package com.tc.appointment.booking.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tc.appointment.booking.utils.Statuses;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessage {

    Statuses status;
    List<String> statusMessages;
    Booking booking;

}
