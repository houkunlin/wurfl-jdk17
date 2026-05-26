package com.scientiamobile.wurfl.core.exc;

public class CapabilityNotDefinedException extends WURFLRuntimeException {
   private static final long serialVersionUID = 1L;
   private String a;

   public CapabilityNotDefinedException(String var1) {
      this(var1, (new StringBuilder("Capability: ")).append(var1).append(" is not defined in WURFL").toString());
   }

   public CapabilityNotDefinedException(String var1, String var2) {
      super((new StringBuilder("Capability: ")).append(var1).append(" - ").append(var2).toString());
      this.a = var1;
   }

   public String getCapabilityName() {
      return this.a;
   }
}
