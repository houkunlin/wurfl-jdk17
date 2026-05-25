package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;

public class IsXHTMLPreferred implements VirtualCapabilityEvaluator, Serializable {
   private static final long serialVersionUID = -8161545030691618770L;

   public String eval(Device var1, WURFLRequest var2) {
      try {
         return Boolean.toString(var1.getCapabilityAsInt("xhtml_support_level") > 0 && !var1.getCapability("preferred_markup").startsWith("html_web"));
      } catch (Exception var3) {
         return "false";
      }
   }

   public String getHandledVirtualCapabilityName() {
      return "is_xhtmlmp_preferred";
   }
}
