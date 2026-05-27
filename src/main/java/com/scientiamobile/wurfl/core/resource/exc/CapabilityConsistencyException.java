package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

public abstract class CapabilityConsistencyException extends DeviceConsistencyException {
   private static final long serialVersionUID = 10L;
   private String capabilityName;

   protected CapabilityConsistencyException(ModelDevice device, String capabilityName) {
      super(device, "Device: " + device.getID() + " Capability: " + capabilityName + " consistency error");
      this.capabilityName = capabilityName;
   }

   protected CapabilityConsistencyException(ModelDevice device, String capabilityName, String message) {
      super(device, message);
      this.capabilityName = capabilityName;
   }

   public String getCapability() {
      return this.capabilityName;
   }
}
