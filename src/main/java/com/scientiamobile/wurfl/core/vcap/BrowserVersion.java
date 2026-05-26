package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.VirtualCapabilityDevice;
import com.scientiamobile.wurfl.core.VirtualCapabilityUserAgentTool;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;

public class BrowserVersion implements VirtualCapabilityEvaluator, Serializable {
   private static final long serialVersionUID = -9221496177103104547L;

   public String eval(Device device, WURFLRequest request) {
      VirtualCapabilityDevice virtualCapabilityDevice = VirtualCapabilityUserAgentTool.getInstance().assignProperties(request, device);
      return VirtualCapabilityHandler.applyControlCapOverride("advertised_browser_version", virtualCapabilityDevice.getBrowserPairVersion(), device);
   }

   public String getHandledVirtualCapabilityName() {
      return "advertised_browser_version";
   }
}
