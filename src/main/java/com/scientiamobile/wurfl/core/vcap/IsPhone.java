package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;

public class IsPhone implements VirtualCapabilityEvaluator, Serializable {
   private static final long serialVersionUID = -8329753363071363291L;

   public String eval(Device var1, WURFLRequest var2) {
      return Boolean.toString(var1.getCapabilityAsBool("can_assign_phone_number") && !var1.getCapabilityAsBool("is_tablet"));
   }

   public String getHandledVirtualCapabilityName() {
      return "is_phone";
   }
}
