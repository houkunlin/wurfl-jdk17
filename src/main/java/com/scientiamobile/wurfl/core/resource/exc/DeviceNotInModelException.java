package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
import com.scientiamobile.wurfl.core.resource.ModelDevice;
import org.apache.commons.lang.text.StrBuilder;

public class DeviceNotInModelException extends WURFLRuntimeException {
  private static final long serialVersionUID = 10L;
  
  private ModelDevice a;
  
  public DeviceNotInModelException(ModelDevice paramModelDevice) {
    super((new StrBuilder("Device: ")).append(paramModelDevice.getID()).append(" is not managed by model").toString());
    this.a = paramModelDevice;
  }
  
  public ModelDevice getModelDevice() {
    return this.a;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\exc\DeviceNotInModelException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */