package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

public class RedefinedDeviceException extends WURFLConsistencyException {
   private static final long serialVersionUID = -7571571247607816104L;

   public RedefinedDeviceException(String var1) {
      super(var1);
   }

   public RedefinedDeviceException(ModelDevice var1, ModelDevice var2, String var3) {
      this("New device " + var1.getID() + " with user-agent " + var1.getUserAgent() + " cannot redefine already loaded device with the same WURFL ID and " + var3 + " " + (var3.equals("fall_back") ? var2.getFallBack() : var2.getUserAgent()));
   }
}
