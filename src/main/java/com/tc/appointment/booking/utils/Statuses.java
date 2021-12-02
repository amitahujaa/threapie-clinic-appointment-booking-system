package com.tc.appointment.booking.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public enum Statuses {

    PASSED(Boolean.TRUE),
    FAILED(Boolean.FALSE);

    private static final Logger LOGGER = LoggerFactory.getLogger(Statuses.class);
    private final boolean status;

    Statuses(final boolean status) {
        this.status = status;
    }
}
