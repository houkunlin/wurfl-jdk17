package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import org.apache.commons.lang3.text.StrBuilder;

public class InexistentCapabilityException extends CapabilityConsistencyException {
  private static final long serialVersionUID = 10L;
  
  public InexistentCapabilityException(ModelDevice paramModelDevice, String paramString) {
    super(paramModelDevice, paramString, (new StrBuilder("Device: ")).append(paramModelDevice.getID()).append(" define unknown capability: ").append(paramString).toString());
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\exc\InexistentCapabilityException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
