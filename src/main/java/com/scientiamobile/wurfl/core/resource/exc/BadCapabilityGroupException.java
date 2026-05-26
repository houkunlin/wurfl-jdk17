package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

public class BadCapabilityGroupException extends CapabilityConsistencyException {
   private static final long serialVersionUID = 10L;
   private String a;
   private String b;

   public BadCapabilityGroupException(ModelDevice var1, String var2, String var3, String var4) {
      super(var1, var2, (new StringBuilder("Capability: ")).append(var2).append(" is defined in group: ").append(var3).append(" istead in group:").append(var4).append(" in Device: ").append(var1.getID()).toString());
      this.b = var4;
      this.a = var3;
   }

   public String getRightGroup() {
      return this.b;
   }

   public String getBadGroup() {
      return this.a;
   }
}
