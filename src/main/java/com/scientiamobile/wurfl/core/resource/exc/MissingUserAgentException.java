package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

public class MissingUserAgentException extends UserAgentConsistencyException {
  private static final long serialVersionUID = -2058322925888598583L;
  
  public MissingUserAgentException(ModelDevice paramModelDevice, String paramString1, String paramString2) {
    super(paramModelDevice, paramString1, paramString2);
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\exc\MissingUserAgentException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */