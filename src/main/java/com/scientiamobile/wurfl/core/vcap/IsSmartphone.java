package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

public class IsSmartphone extends a {
   private static final long serialVersionUID = 1131972797981270952L;

   public String eval(Device var1, WURFLRequest var2) {
      return Boolean.toString(a(var1));
   }

   public String getHandledVirtualCapabilityName() {
      return "is_smartphone";
   }
}
