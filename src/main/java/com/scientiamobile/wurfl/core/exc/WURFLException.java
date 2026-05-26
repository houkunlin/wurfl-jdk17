package com.scientiamobile.wurfl.core.exc;

public abstract class WURFLException extends Exception {
   private static final long serialVersionUID = 1L;

   public WURFLException() {
      super("Generic Exception in WURFL.");
   }

   public WURFLException(String message) {
      super(message);
   }

   public WURFLException(Throwable cause) {
      super(cause);
   }

   public WURFLException(String message, Throwable cause) {
      super(message, cause);
   }
}
