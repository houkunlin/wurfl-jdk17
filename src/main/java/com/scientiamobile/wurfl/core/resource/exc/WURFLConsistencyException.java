package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;

public abstract class WURFLConsistencyException extends WURFLRuntimeException {
   private static final long serialVersionUID = 10L;

   public WURFLConsistencyException() {
      super("WURFL consistency exception");
   }

   public WURFLConsistencyException(Throwable var1) {
      super("WURFL consistency exception", var1);
   }

   public WURFLConsistencyException(String var1) {
      super(var1);
   }

   public WURFLConsistencyException(String var1, Throwable var2) {
      super(var1, var2);
   }
}
