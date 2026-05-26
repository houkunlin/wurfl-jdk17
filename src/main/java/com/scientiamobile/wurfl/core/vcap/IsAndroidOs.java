package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;

public class IsAndroidOs implements VirtualCapabilityEvaluator, Serializable {
   private static final long serialVersionUID = 6129742649965950877L;

   @Override
   public String eval(Device device, WURFLRequest request) {
      return Boolean.toString("Android".equals(device.getCapability("device_os")));
   }

   @Override
   public String getHandledVirtualCapabilityName() {
      return "is_android";
   }
}
