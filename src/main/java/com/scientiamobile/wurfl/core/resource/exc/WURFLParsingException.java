package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;

public class WURFLParsingException extends WURFLRuntimeException {
   private static final long serialVersionUID = 10L;

   public WURFLParsingException() {
   }

   public WURFLParsingException(String var1) {
      super(var1);
   }

   public WURFLParsingException(Throwable var1) {
      super(var1);
   }

   public WURFLParsingException(String var1, Throwable var2) {
      super(var1, var2);
   }
}
