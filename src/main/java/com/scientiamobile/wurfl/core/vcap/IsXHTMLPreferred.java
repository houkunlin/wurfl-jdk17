package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;

public class IsXHTMLPreferred implements VirtualCapabilityEvaluator, Serializable {
  private static final long serialVersionUID = -8161545030691618770L;
  
  public String eval(Device paramDevice, WURFLRequest paramWURFLRequest) {
    try {
      return Boolean.toString((paramDevice.getCapabilityAsInt("xhtml_support_level") > 0 && !paramDevice.getCapability("preferred_markup").startsWith("html_web")));
    } catch (Exception exception) {
      return "false";
    } 
  }
  
  public String getHandledVirtualCapabilityName() {
    return "is_xhtmlmp_preferred";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\vcap\IsXHTMLPreferred.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */