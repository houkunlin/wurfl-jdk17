package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.VirtualCapabilityDevice;
import com.scientiamobile.wurfl.core.VirtualCapabilityUserAgentTool;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;

public class OsVersion implements VirtualCapabilityEvaluator, Serializable {
   private static final long serialVersionUID = -1685031657455562093L;

   public String eval(Device var1, WURFLRequest var2) {
      VirtualCapabilityDevice var3 = VirtualCapabilityUserAgentTool.getInstance().assignProperties(var2, var1);
      return VirtualCapabilityHandler.a("advertised_device_os_version", var3.getOsPairVersion(), var1);
   }

   public String getHandledVirtualCapabilityName() {
      return "advertised_device_os_version";
   }
}
