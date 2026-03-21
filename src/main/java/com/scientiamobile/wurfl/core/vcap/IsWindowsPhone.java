package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;

public class IsWindowsPhone implements VirtualCapabilityEvaluator, Serializable {
  private static final long serialVersionUID = 7780353517392752318L;
  
  public String eval(Device paramDevice, WURFLRequest paramWURFLRequest) {
    return Boolean.toString("Windows Phone OS".equals(paramDevice.getCapability("device_os")));
  }
  
  public String getHandledVirtualCapabilityName() {
    return "is_windows_phone";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\vcap\IsWindowsPhone.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */