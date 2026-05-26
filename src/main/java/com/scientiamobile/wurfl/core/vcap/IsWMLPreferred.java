package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;

public class IsWMLPreferred implements VirtualCapabilityEvaluator, Serializable {
   private static final long serialVersionUID = 4429460118740181952L;

   public String eval(Device device, WURFLRequest request) {
      try {
         return Boolean.toString(device.getCapabilityAsInt("xhtml_support_level") <= 0);
      } catch (RuntimeException e) {
         return "false";
      }
   }

   public String getHandledVirtualCapabilityName() {
      return "is_wml_preferred";
   }
}
