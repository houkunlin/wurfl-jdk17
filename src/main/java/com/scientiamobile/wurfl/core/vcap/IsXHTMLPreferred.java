package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;

public class IsXHTMLPreferred implements VirtualCapabilityEvaluator, Serializable {
   private static final long serialVersionUID = -8161545030691618770L;

   public String eval(Device device, WURFLRequest request) {
      try {
         return Boolean.toString(device.getCapabilityAsInt("xhtml_support_level") > 0 && !device.getCapability("preferred_markup").startsWith("html_web"));
      } catch (RuntimeException e) {
         return "false";
      }
   }

   public String getHandledVirtualCapabilityName() {
      return "is_xhtmlmp_preferred";
   }
}
