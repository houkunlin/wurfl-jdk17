package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

public class MissingUserAgentException extends UserAgentConsistencyException {
    private static final long serialVersionUID = -2058322925888598583L;

    public MissingUserAgentException(ModelDevice device, String userAgent, String message) {
        super(device, userAgent, message);
    }
}
