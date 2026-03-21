package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import org.apache.commons.lang.text.StrBuilder;

public abstract class CapabilityConsistencyException extends DeviceConsistencyException {
  private static final long serialVersionUID = 10L;
  
  private String a;
  
  public CapabilityConsistencyException(ModelDevice paramModelDevice, String paramString) {
    super(paramModelDevice, (new StrBuilder("Device: ")).append(paramModelDevice.getID()).append(" Capability: ").append(paramString).append(" consistency error").toString());
    this.a = paramString;
  }
  
  public CapabilityConsistencyException(ModelDevice paramModelDevice, String paramString1, String paramString2) {
    super(paramModelDevice, paramString2);
    this.a = paramString1;
  }
  
  public String getCapability() {
    return this.a;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\exc\CapabilityConsistencyException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */