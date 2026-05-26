package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;

public abstract class WURFLConsistencyException extends WURFLRuntimeException {
   private static final long serialVersionUID = 10L;

   public WURFLConsistencyException() {
      super("WURFL consistency exception");
   }

   public WURFLConsistencyException(Throwable cause) {
      super("WURFL consistency exception", cause);
   }

   public WURFLConsistencyException(String message) {
      super(message);
   }

   public WURFLConsistencyException(String message, Throwable cause) {
      super(message, cause);
   }
}
