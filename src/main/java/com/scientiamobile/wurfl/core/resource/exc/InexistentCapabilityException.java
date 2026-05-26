package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

public class InexistentCapabilityException extends CapabilityConsistencyException {
   private static final long serialVersionUID = 10L;

   public InexistentCapabilityException(ModelDevice var1, String var2) {
      super(var1, var2, (new StringBuilder("Device: ")).append(var1.getID()).append(" define unknown capability: ").append(var2).toString());
   }
}
