package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.resource.exc.WURFLConsistencyException;

final class MissingDeviceIdConsistencyException extends WURFLConsistencyException {
   private static final long serialVersionUID = 1L;

   MissingDeviceIdConsistencyException(String message) {
      super(message);
   }
}

