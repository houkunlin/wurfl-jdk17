package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

import java.io.Serial;

/**
 * Exception thrown when group consistency occurs.
 */

public abstract class GroupConsistencyException extends DeviceConsistencyException {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String groupId;

    protected GroupConsistencyException(ModelDevice device, String groupId) {
        super(device, "Group: " + groupId + " in device: " + device.getID() + " consistency exception");
        this.groupId = groupId;
    }

    protected GroupConsistencyException(ModelDevice device, String groupId, String message) {
        super(device, message);
        this.groupId = groupId;
    }

    /**
     * Returns the group.
     */

    public String getGroup() {
        return this.groupId;
    }
}
