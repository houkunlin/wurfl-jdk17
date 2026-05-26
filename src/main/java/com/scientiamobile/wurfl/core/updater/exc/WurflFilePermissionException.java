package com.scientiamobile.wurfl.core.updater.exc;

import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;

public class WurflFilePermissionException extends WURFLRuntimeException {
   public WurflFilePermissionException(String message) {
      super(message);
   }

   public WurflFilePermissionException(String message, Throwable cause) {
      super(message, cause);
   }
}
