package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

import java.io.Serial;

public class InexistentGroupException extends GroupConsistencyException {
    @Serial
    private static final long serialVersionUID = 1L;

    public InexistentGroupException(ModelDevice device, String groupId) {
        super(device, groupId, "Device: " + device.getID() + " define unknow group: " + groupId);
    }
}
