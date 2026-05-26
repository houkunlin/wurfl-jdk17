package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

public abstract class UserAgentConsistencyException extends DeviceConsistencyException {
   private static final long serialVersionUID = 10L;
   private String userAgent;

   public UserAgentConsistencyException(ModelDevice device, String userAgent, String message) {
      super(device, message);
      this.userAgent = userAgent;
   }

   public UserAgentConsistencyException(ModelDevice device, String userAgent) {
      super(device, (new StringBuilder("Device: ")).append(device.getID()).append(" user-agent: ").append(userAgent).append(" consistency exception").toString());
      this.userAgent = userAgent;
   }

   public String getUserAgent() {
      return this.userAgent;
   }
}
