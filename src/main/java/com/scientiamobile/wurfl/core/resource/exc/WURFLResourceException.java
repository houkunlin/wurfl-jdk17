package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
import com.scientiamobile.wurfl.core.resource.WURFLResource;

public class WURFLResourceException extends WURFLRuntimeException {
   private static final long serialVersionUID = 10L;
   private WURFLResource a;

   public WURFLResourceException(WURFLResource var1) {
      super((new StringBuilder("WURFL resource exception in: ")).append(var1.getInfo()).toString());
      this.a = var1;
   }

   public WURFLResourceException(WURFLResource var1, Throwable var2) {
      super(var2);
      this.a = var1;
   }

   public WURFLResourceException(WURFLResource var1, String var2) {
      super(var2);
      this.a = var1;
   }

   public WURFLResourceException(WURFLResource var1, String var2, Throwable var3) {
      super(var2, var3);
      this.a = var1;
   }

   public WURFLResource getResource() {
      return this.a;
   }
}
