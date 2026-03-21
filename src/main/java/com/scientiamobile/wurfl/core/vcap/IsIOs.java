package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;
import java.util.HashSet;

public class IsIOs implements VirtualCapabilityEvaluator, Serializable {
  private static final long serialVersionUID = 5384820129703085119L;
  
  public String eval(Device paramDevice, WURFLRequest paramWURFLRequest) {
    return Boolean.toString(("iOS".equals(paramDevice.getCapability("device_os")) || "iPhoneOS".equals(paramDevice.getCapability("device_os"))));
  }
  
  public String getHandledVirtualCapabilityName() {
    return "is_ios";
  }
  
  static {
    (new HashSet<String>()).add("device_os");
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\vcap\IsIOs.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */