package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

public class IsRobot extends AbstractVirtualCapabilityEvaluator {
   private static final long serialVersionUID = 290928780375573277L;

   public String eval(Device device, WURFLRequest request) {
      return Boolean.toString(isRobot(request));
   }

   public String getHandledVirtualCapabilityName() {
      return "is_robot";
   }
}
