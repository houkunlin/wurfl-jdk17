package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

public class MissingUserAgentException extends UserAgentConsistencyException {

    public MissingUserAgentException(ModelDevice device, String userAgent, String message) {
        super(device, userAgent, message);
    }
}
