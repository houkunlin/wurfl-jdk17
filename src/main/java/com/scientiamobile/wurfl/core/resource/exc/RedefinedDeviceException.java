package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

public class RedefinedDeviceException extends WURFLConsistencyException {
  private static final long serialVersionUID = -7571571247607816104L;
  
  public RedefinedDeviceException(String paramString) {
    super(paramString);
  }
  
  public RedefinedDeviceException(ModelDevice paramModelDevice1, ModelDevice paramModelDevice2, String paramString) {
    this("New device " + paramModelDevice1.getID() + "with user-agent " + paramModelDevice1.getUserAgent() + " cannot redefine already loaded device with the same WURFL ID and " + paramString + " " + paramModelDevice2.getUserAgent());
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\exc\RedefinedDeviceException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */