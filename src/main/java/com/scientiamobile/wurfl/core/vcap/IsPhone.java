package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;

public class IsPhone implements VirtualCapabilityEvaluator, Serializable {
  private static final long serialVersionUID = -8329753363071363291L;
  
  public String eval(Device paramDevice, WURFLRequest paramWURFLRequest) {
    return Boolean.toString((paramDevice.getCapabilityAsBool("can_assign_phone_number") && !paramDevice.getCapabilityAsBool("is_tablet")));
  }
  
  public String getHandledVirtualCapabilityName() {
    return "is_phone";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\vcap\IsPhone.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */