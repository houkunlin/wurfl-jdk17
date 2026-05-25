package com.scientiamobile.wurfl.core.exc;

import org.apache.commons.lang3.text.StrBuilder;

public class VirtualCapabilityNotDefinedException extends WURFLRuntimeException {
   private static final long serialVersionUID = 2L;
   private String a;

   public VirtualCapabilityNotDefinedException(String var1) {
      this(var1, (new StrBuilder("Capability: ")).append(var1).append(" is not defined in WURFL").toString());
   }

   public VirtualCapabilityNotDefinedException(String var1, String var2) {
      super((new StrBuilder("Virtual Capability: ")).append(var1).append(" - ").append(var2).toString());
      this.a = var1;
   }

   public String getCapabilityName() {
      return this.a;
   }
}
