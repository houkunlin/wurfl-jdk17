package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

public abstract class UserAgentConsistencyException extends DeviceConsistencyException {
   private static final long serialVersionUID = 10L;
   private String userAgent;

   protected UserAgentConsistencyException(ModelDevice device, String userAgent, String message) {
      super(device, message);
      this.userAgent = userAgent;
   }

   protected UserAgentConsistencyException(ModelDevice device, String userAgent) {
      super(device, "Device: " + device.getID() + " user-agent: " + userAgent + " consistency exception");
      this.userAgent = userAgent;
   }

   public String getUserAgent() {
      return this.userAgent;
   }
}
