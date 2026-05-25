package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

public class MissingUserAgentException extends UserAgentConsistencyException {
   private static final long serialVersionUID = -2058322925888598583L;

   public MissingUserAgentException(ModelDevice var1, String var2, String var3) {
      super(var1, var2, var3);
   }
}
