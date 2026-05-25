package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.resource.exc.WURFLConsistencyException;

final class MissingDeviceIdConsistencyException extends WURFLConsistencyException {
   MissingDeviceIdConsistencyException(String message) {
      super(message);
   }
}

