package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;

public class IsFullDesktop implements VirtualCapabilityEvaluator, Serializable {
  private static final long serialVersionUID = 4434275176350438714L;
  
  public String eval(Device paramDevice, WURFLRequest paramWURFLRequest) {
    return paramDevice.getCapability("ux_full_desktop");
  }
  
  public String getHandledVirtualCapabilityName() {
    return "is_full_desktop";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\vcap\IsFullDesktop.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */