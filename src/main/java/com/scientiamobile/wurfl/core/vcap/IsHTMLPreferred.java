package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;

public class IsHTMLPreferred implements VirtualCapabilityEvaluator, Serializable {
  private static final long serialVersionUID = -4750338441403942375L;
  
  public String eval(Device paramDevice, WURFLRequest paramWURFLRequest) {
    return Boolean.toString(paramDevice.getCapability("preferred_markup").startsWith("html_web"));
  }
  
  public String getHandledVirtualCapabilityName() {
    return "is_html_preferred";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\vcap\IsHTMLPreferred.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */