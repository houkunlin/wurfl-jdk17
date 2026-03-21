package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;

public class IsMobile implements VirtualCapabilityEvaluator, Serializable {
  private static final long serialVersionUID = -3052242731391430427L;
  
  public String eval(Device paramDevice, WURFLRequest paramWURFLRequest) {
    return paramDevice.getCapability("is_wireless_device");
  }
  
  public String getHandledVirtualCapabilityName() {
    return "is_mobile";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\vcap\IsMobile.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */