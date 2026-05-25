package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;

public class IsFullDesktop implements VirtualCapabilityEvaluator, Serializable {
   private static final long serialVersionUID = 4434275176350438714L;

   public String eval(Device var1, WURFLRequest var2) {
      return var1.getCapability("ux_full_desktop");
   }

   public String getHandledVirtualCapabilityName() {
      return "is_full_desktop";
   }
}
