package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;

public class IsWMLPreferred implements VirtualCapabilityEvaluator, Serializable {
  private static final long serialVersionUID = 4429460118740181952L;
  
  public String eval(Device paramDevice, WURFLRequest paramWURFLRequest) {
    try {
      return Boolean.toString((paramDevice.getCapabilityAsInt("xhtml_support_level") <= 0));
    } catch (Exception exception) {
      return "false";
    } 
  }
  
  public String getHandledVirtualCapabilityName() {
    return "is_wml_preferred";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\vcap\IsWMLPreferred.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */