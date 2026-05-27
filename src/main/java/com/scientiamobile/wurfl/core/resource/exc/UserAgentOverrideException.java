package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

public class UserAgentOverrideException extends UserAgentConsistencyException {
   private final String existingUserAgent;

    public UserAgentOverrideException(ModelDevice device, String overridingUserAgent, String existingUserAgent) {
        super(device, overridingUserAgent, (new StringBuilder("Device: ")).append(device).append(" override defined user-agent: ").append(existingUserAgent).append(" with overriding user-agent:").append(overridingUserAgent).toString());
        this.existingUserAgent = existingUserAgent;
    }

    public String getExistUserAgent() {
        return this.existingUserAgent;
    }
}
