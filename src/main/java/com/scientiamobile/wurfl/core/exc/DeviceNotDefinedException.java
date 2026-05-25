package com.scientiamobile.wurfl.core.exc;

import org.apache.commons.lang3.text.StrBuilder;

public class DeviceNotDefinedException extends WURFLRuntimeException {
   private static final long serialVersionUID = 1L;
   private String a;

   public DeviceNotDefinedException(String var1, String var2) {
      super(var2);
      this.a = var1;
   }

   public DeviceNotDefinedException(String var1) {
      this(var1, (new StrBuilder("Device: ")).append(var1).append(" is not defined in WURFL").toString());
   }

   public String getDeviceId() {
      return this.a;
   }
}
