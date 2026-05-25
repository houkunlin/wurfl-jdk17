package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import org.apache.commons.lang3.text.StrBuilder;

public abstract class CapabilityConsistencyException extends DeviceConsistencyException {
   private static final long serialVersionUID = 10L;
   private String a;

   public CapabilityConsistencyException(ModelDevice var1, String var2) {
      super(var1, (new StrBuilder("Device: ")).append(var1.getID()).append(" Capability: ").append(var2).append(" consistency error").toString());
      this.a = var2;
   }

   public CapabilityConsistencyException(ModelDevice var1, String var2, String var3) {
      super(var1, var3);
      this.a = var2;
   }

   public String getCapability() {
      return this.a;
   }
}
