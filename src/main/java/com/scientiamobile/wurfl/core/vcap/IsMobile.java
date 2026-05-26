package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;

public class IsMobile implements VirtualCapabilityEvaluator, Serializable {
   private static final long serialVersionUID = -3052242731391430427L;

   @Override
   public String eval(Device device, WURFLRequest request) {
      return device.getCapability("is_wireless_device");
   }

   @Override
   public String getHandledVirtualCapabilityName() {
      return "is_mobile";
   }
}
