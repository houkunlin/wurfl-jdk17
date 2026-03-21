package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;

public class IsLargescreen implements VirtualCapabilityEvaluator, Serializable {
  private static final long serialVersionUID = -7518577459129144687L;
  
  public String eval(Device paramDevice, WURFLRequest paramWURFLRequest) {
    try {
      return Boolean.toString((paramDevice.getCapabilityAsInt("resolution_width") >= 480 && paramDevice.getCapabilityAsInt("resolution_height") >= 480));
    } catch (Exception exception) {
      return "false";
    } 
  }
  
  public String getHandledVirtualCapabilityName() {
    return "is_largescreen";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\vcap\IsLargescreen.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */