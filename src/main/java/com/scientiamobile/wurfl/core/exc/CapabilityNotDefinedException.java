package com.scientiamobile.wurfl.core.exc;

public class CapabilityNotDefinedException extends WURFLRuntimeException {
   private static final long serialVersionUID = 1L;
   private String capabilityName;

   public CapabilityNotDefinedException(String capabilityName) {
      this(capabilityName, (new StringBuilder("Capability: ")).append(capabilityName).append(" is not defined in WURFL").toString());
   }

   public CapabilityNotDefinedException(String capabilityName, String message) {
      super("Capability: " + capabilityName + " - " + message);
      this.capabilityName = capabilityName;
   }

   public String getCapabilityName() {
      return this.capabilityName;
   }
}
