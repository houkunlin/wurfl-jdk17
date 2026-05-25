package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;

public class IsAndroidOs implements VirtualCapabilityEvaluator, Serializable {
   private static final long serialVersionUID = 6129742649965950877L;

   public String eval(Device var1, WURFLRequest var2) {
      return Boolean.toString("Android".equals(var1.getCapability("device_os")));
   }

   public String getHandledVirtualCapabilityName() {
      return "is_android";
   }
}
