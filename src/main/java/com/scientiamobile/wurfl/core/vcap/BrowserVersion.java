package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.InternalDevice;
import com.scientiamobile.wurfl.core.VirtualCapabilityDevice;
import com.scientiamobile.wurfl.core.VirtualCapabilityUserAgentTool;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;

public class BrowserVersion implements VirtualCapabilityEvaluator, Serializable {
  private static final long serialVersionUID = -9221496177103104547L;
  
  public String eval(Device paramDevice, WURFLRequest paramWURFLRequest) {
    VirtualCapabilityDevice virtualCapabilityDevice = VirtualCapabilityUserAgentTool.getInstance().assignProperties(paramWURFLRequest, (InternalDevice)paramDevice);
    return VirtualCapabilityHandler.a("advertised_browser_version", virtualCapabilityDevice.getBrowserPairVersion(), (InternalDevice)paramDevice);
  }
  
  public String getHandledVirtualCapabilityName() {
    return "advertised_browser_version";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\vcap\BrowserVersion.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */