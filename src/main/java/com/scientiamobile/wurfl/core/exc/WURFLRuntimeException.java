package com.scientiamobile.wurfl.core.exc;

public class WURFLRuntimeException extends RuntimeException {
   private static final long serialVersionUID = 1L;

   public WURFLRuntimeException() {
      super("WURFL unexpected exception");
   }

   public WURFLRuntimeException(String var1) {
      super(var1);
   }

   public WURFLRuntimeException(String var1, Throwable var2) {
      super(var1, var2);
   }

   public WURFLRuntimeException(Throwable var1) {
      super("WURFL unexpected exception", var1);
   }
}
