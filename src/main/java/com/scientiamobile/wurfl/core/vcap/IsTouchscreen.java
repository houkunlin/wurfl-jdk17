package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.io.Serializable;

public class IsTouchscreen implements VirtualCapabilityEvaluator, Serializable {
   private static final long serialVersionUID = 3516513258503645772L;

   public String eval(Device var1, WURFLRequest var2) {
      String var3 = var2.isUrlEncoded() ? var2.getCleanedDeviceUserAgent() : var2.getOriginalUserAgent();
      return Boolean.toString("touchscreen".equals(var1.getCapability("pointing_method")) || StringMatchUtils.containsAllOf(var3, "Trident", "Touch"));
   }

   public String getHandledVirtualCapabilityName() {
      return "is_touchscreen";
   }
}
