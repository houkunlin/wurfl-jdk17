package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

public class BadCapabilityGroupException extends CapabilityConsistencyException {
    private static final long serialVersionUID = 10L;
    private String actualGroupId;
    private String expectedGroupId;

    public BadCapabilityGroupException(ModelDevice device, String capabilityName, String actualGroupId, String expectedGroupId) {
        super(device, capabilityName, "Capability: " + capabilityName + " is defined in group: " + actualGroupId + " istead in group:" + expectedGroupId + " in Device: " + device.getID());
        this.expectedGroupId = expectedGroupId;
        this.actualGroupId = actualGroupId;
    }

    public String getRightGroup() {
        return this.expectedGroupId;
    }

    public String getBadGroup() {
        return this.actualGroupId;
    }
}
