package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;

public class IsAndroidOs implements VirtualCapabilityEvaluator, Serializable {
  private static final long serialVersionUID = 6129742649965950877L;
  
  public String eval(Device paramDevice, WURFLRequest paramWURFLRequest) {
    return Boolean.toString("Android".equals(paramDevice.getCapability("device_os")));
  }
  
  public String getHandledVirtualCapabilityName() {
    return "is_android";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\vcap\IsAndroidOs.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */