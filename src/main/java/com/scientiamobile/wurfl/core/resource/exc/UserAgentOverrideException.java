package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

import java.io.Serial;

public class UserAgentOverrideException extends UserAgentConsistencyException {
    @Serial
    private static final long serialVersionUID = 1L;
   private final String existingUserAgent;

    public UserAgentOverrideException(ModelDevice device, String overridingUserAgent, String existingUserAgent) {
        super(device, overridingUserAgent, (new StringBuilder("Device: ")).append(device).append(" override defined user-agent: ").append(existingUserAgent).append(" with overriding user-agent:").append(overridingUserAgent).toString());
        this.existingUserAgent = existingUserAgent;
    }

    public String getExistUserAgent() {
        return this.existingUserAgent;
    }
}
