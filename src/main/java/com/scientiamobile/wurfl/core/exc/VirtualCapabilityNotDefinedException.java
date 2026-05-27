package com.scientiamobile.wurfl.core.exc;

public class VirtualCapabilityNotDefinedException extends WURFLRuntimeException {
   private static final long serialVersionUID = 2L;
   private String capabilityName;

   public VirtualCapabilityNotDefinedException(String capabilityName) {
      this(capabilityName, (new StringBuilder("Capability: ")).append(capabilityName).append(" is not defined in WURFL").toString());
   }

   public VirtualCapabilityNotDefinedException(String capabilityName, String message) {
      super("Virtual Capability: " + capabilityName + " - " + message);
      this.capabilityName = capabilityName;
   }

   public String getCapabilityName() {
      return this.capabilityName;
   }
}
