package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;

public class IsIOs implements VirtualCapabilityEvaluator, Serializable {
   private static final long serialVersionUID = 5384820129703085119L;

   @Override
   public String eval(Device device, WURFLRequest request) {
      return Boolean.toString("iOS".equals(device.getCapability("device_os")) || "iPhoneOS".equals(device.getCapability("device_os")));
   }

   @Override
   public String getHandledVirtualCapabilityName() {
      return "is_ios";
   }
}
