package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

import java.io.Serial;

/**
 * Exception thrown when missing user agent occurs.
 */

public class MissingUserAgentException extends UserAgentConsistencyException {
    @Serial
    private static final long serialVersionUID = 1L;

    public MissingUserAgentException(ModelDevice device, String userAgent, String message) {
        super(device, userAgent, message);
    }
}
