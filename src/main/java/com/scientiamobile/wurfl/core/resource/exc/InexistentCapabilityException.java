package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

public class InexistentCapabilityException extends CapabilityConsistencyException {
   private static final long serialVersionUID = 10L;

   public InexistentCapabilityException(ModelDevice device, String capabilityName) {
      super(device, capabilityName, "Device: " + device.getID() + " define unknown capability: " + capabilityName);
   }
}
