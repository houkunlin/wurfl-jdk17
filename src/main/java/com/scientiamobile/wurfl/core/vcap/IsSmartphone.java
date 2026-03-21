package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

public class IsSmartphone extends a {
  private static final long serialVersionUID = 1131972797981270952L;
  
  public String eval(Device paramDevice, WURFLRequest paramWURFLRequest) {
    return Boolean.toString(a(paramDevice));
  }
  
  public String getHandledVirtualCapabilityName() {
    return "is_smartphone";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\vcap\IsSmartphone.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */