package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.io.Serializable;

public class IsTouchscreen implements VirtualCapabilityEvaluator, Serializable {
  private static final long serialVersionUID = 3516513258503645772L;
  
  public String eval(Device paramDevice, WURFLRequest paramWURFLRequest) {
    String str = paramWURFLRequest.isUrlEncoded() ? paramWURFLRequest.getCleanedDeviceUserAgent() : paramWURFLRequest.getOriginalUserAgent();
    return Boolean.toString(("touchscreen".equals(paramDevice.getCapability("pointing_method")) || StringMatchUtils.containsAllOf(str, new String[] { "Trident", "Touch" })));
  }
  
  public String getHandledVirtualCapabilityName() {
    return "is_touchscreen";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\vcap\IsTouchscreen.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */