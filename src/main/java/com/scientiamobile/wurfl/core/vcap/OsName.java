package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.InternalDevice;
import com.scientiamobile.wurfl.core.VirtualCapabilityDevice;
import com.scientiamobile.wurfl.core.VirtualCapabilityUserAgentTool;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;

public class OsName implements VirtualCapabilityEvaluator, Serializable {
  private static final long serialVersionUID = 2665195735628227650L;
  
  public String eval(Device paramDevice, WURFLRequest paramWURFLRequest) {
    VirtualCapabilityDevice virtualCapabilityDevice = VirtualCapabilityUserAgentTool.getInstance().assignProperties(paramWURFLRequest, (InternalDevice)paramDevice);
    return VirtualCapabilityHandler.a("advertised_device_os", virtualCapabilityDevice.getOsPairName(), (InternalDevice)paramDevice);
  }
  
  public String getHandledVirtualCapabilityName() {
    return "advertised_device_os";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\vcap\OsName.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */