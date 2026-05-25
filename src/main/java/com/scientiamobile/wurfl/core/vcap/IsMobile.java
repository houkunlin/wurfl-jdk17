package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;

public class IsMobile implements VirtualCapabilityEvaluator, Serializable {
   private static final long serialVersionUID = -3052242731391430427L;

   public String eval(Device var1, WURFLRequest var2) {
      return var1.getCapability("is_wireless_device");
   }

   public String getHandledVirtualCapabilityName() {
      return "is_mobile";
   }
}
