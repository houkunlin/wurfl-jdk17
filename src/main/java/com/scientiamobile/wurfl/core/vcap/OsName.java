package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.VirtualCapabilityDevice;
import com.scientiamobile.wurfl.core.VirtualCapabilityUserAgentTool;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;

public class OsName implements VirtualCapabilityEvaluator, Serializable {
   private static final long serialVersionUID = 2665195735628227650L;

   public String eval(Device device, WURFLRequest request) {
      VirtualCapabilityDevice virtualCapabilityDevice = VirtualCapabilityUserAgentTool.getInstance().assignProperties(request, device);
      return VirtualCapabilityHandler.applyControlCapOverride("advertised_device_os", virtualCapabilityDevice.getOsPairName(), device);
   }

   public String getHandledVirtualCapabilityName() {
      return "advertised_device_os";
   }
}
