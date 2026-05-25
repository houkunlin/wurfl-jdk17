package com.scientiamobile.wurfl.core.exc;

public abstract class WURFLException extends Exception {
   private static final long serialVersionUID = 1L;

   public WURFLException() {
      super("Generic Exception in WURFL.");
   }

   public WURFLException(String var1) {
      super(var1);
   }

   public WURFLException(Throwable var1) {
      super(var1);
   }

   public WURFLException(String var1, Throwable var2) {
      super(var1, var2);
   }
}
