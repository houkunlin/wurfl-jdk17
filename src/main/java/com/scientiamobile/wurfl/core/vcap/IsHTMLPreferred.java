package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;

public class IsHTMLPreferred implements VirtualCapabilityEvaluator, Serializable {
   private static final long serialVersionUID = -4750338441403942375L;

   public String eval(Device var1, WURFLRequest var2) {
      return Boolean.toString(var1.getCapability("preferred_markup").startsWith("html_web"));
   }

   public String getHandledVirtualCapabilityName() {
      return "is_html_preferred";
   }
}
