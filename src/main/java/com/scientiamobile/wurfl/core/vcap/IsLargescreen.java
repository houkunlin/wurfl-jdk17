package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;

public class IsLargescreen implements VirtualCapabilityEvaluator, Serializable {
   private static final long serialVersionUID = -7518577459129144687L;

   public String eval(Device var1, WURFLRequest var2) {
      try {
         return Boolean.toString(var1.getCapabilityAsInt("resolution_width") >= 480 && var1.getCapabilityAsInt("resolution_height") >= 480);
      } catch (Exception var3) {
         return "false";
      }
   }

   public String getHandledVirtualCapabilityName() {
      return "is_largescreen";
   }
}
