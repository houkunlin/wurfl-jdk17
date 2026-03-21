package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import org.apache.commons.lang3.text.StrBuilder;

public abstract class DeviceConsistencyException extends WURFLConsistencyException {
  private static final long serialVersionUID = 10L;
  
  private ModelDevice a;
  
  public DeviceConsistencyException(ModelDevice paramModelDevice, String paramString) {
    super(paramString);
    this.a = paramModelDevice;
  }
  
  public DeviceConsistencyException(ModelDevice paramModelDevice) {
    super((new StrBuilder("Device: ")).append(paramModelDevice.getID()).append(" consistency error").toString());
    this.a = paramModelDevice;
  }
  
  public ModelDevice getDevice() {
    return this.a;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\exc\DeviceConsistencyException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
