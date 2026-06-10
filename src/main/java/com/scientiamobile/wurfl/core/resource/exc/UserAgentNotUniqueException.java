package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

import java.io.Serial;

/**
 * Exception thrown when user agent not unique occurs.
 */

public class UserAgentNotUniqueException extends UserAgentConsistencyException {
    @Serial
    private static final long serialVersionUID = 1L;
   private final ModelDevice definingDevice;

    public UserAgentNotUniqueException(ModelDevice device, String userAgent, ModelDevice definingDevice) {
        super(device, userAgent, (new StringBuilder("Device: ")).append(device).append(" define duplicate user-agent: ").append(userAgent).append(" defined by device: ").append(definingDevice).toString());
        this.definingDevice = definingDevice;
    }

    /**
     * Returns the definin gevice.
     */

    public ModelDevice getDefiningDevice() {
        return this.definingDevice;
    }
}
