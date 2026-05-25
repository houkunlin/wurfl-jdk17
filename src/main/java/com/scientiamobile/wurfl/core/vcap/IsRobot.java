package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

public class IsRobot extends a {
   private static final long serialVersionUID = 290928780375573277L;

   public String eval(Device var1, WURFLRequest var2) {
      return Boolean.toString(a(var2));
   }

   public String getHandledVirtualCapabilityName() {
      return "is_robot";
   }
}
