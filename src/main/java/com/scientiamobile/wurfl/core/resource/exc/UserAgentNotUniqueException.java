package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

public class UserAgentNotUniqueException extends UserAgentConsistencyException {
   private static final long serialVersionUID = 10L;
   private ModelDevice definingDevice;

   public UserAgentNotUniqueException(ModelDevice device, String userAgent, ModelDevice definingDevice) {
      super(device, userAgent, (new StringBuilder("Device: ")).append(device).append(" define duplicate user-agent: ").append(userAgent).append(" defined by device: ").append(definingDevice).toString());
      this.definingDevice = definingDevice;
   }

   public ModelDevice getDefiningDevice() {
      return this.definingDevice;
   }
}
