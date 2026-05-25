package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;

public class IsWindowsPhone implements VirtualCapabilityEvaluator, Serializable {
   private static final long serialVersionUID = 7780353517392752318L;

   public String eval(Device var1, WURFLRequest var2) {
      return Boolean.toString("Windows Phone OS".equals(var1.getCapability("device_os")));
   }

   public String getHandledVirtualCapabilityName() {
      return "is_windows_phone";
   }
}
