package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;

public class IsLargescreen implements VirtualCapabilityEvaluator, Serializable {
   private static final long serialVersionUID = -7518577459129144687L;

   public String eval(Device device, WURFLRequest request) {
      try {
         return Boolean.toString(device.getCapabilityAsInt("resolution_width") >= 480 && device.getCapabilityAsInt("resolution_height") >= 480);
      } catch (Exception e) {
         return "false";
      }
   }

   public String getHandledVirtualCapabilityName() {
      return "is_largescreen";
   }
}
