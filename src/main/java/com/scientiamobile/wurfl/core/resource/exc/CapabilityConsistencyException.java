package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

public abstract class CapabilityConsistencyException extends DeviceConsistencyException {
   private static final long serialVersionUID = 10L;
   private String capabilityName;

   public CapabilityConsistencyException(ModelDevice device, String capabilityName) {
      super(device, (new StringBuilder("Device: ")).append(device.getID()).append(" Capability: ").append(capabilityName).append(" consistency error").toString());
      this.capabilityName = capabilityName;
   }

   public CapabilityConsistencyException(ModelDevice device, String capabilityName, String message) {
      super(device, message);
      this.capabilityName = capabilityName;
   }

   public String getCapability() {
      return this.capabilityName;
   }
}
