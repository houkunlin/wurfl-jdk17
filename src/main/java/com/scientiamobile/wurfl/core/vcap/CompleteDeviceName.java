package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;

public class CompleteDeviceName implements VirtualCapabilityEvaluator, Serializable {
  private static final long serialVersionUID = -65030764132400949L;
  
  public String eval(Device paramDevice, WURFLRequest paramWURFLRequest) {
    StringBuilder stringBuilder = new StringBuilder(paramDevice.getCapability("brand_name"));
    String str;
    if ((str = paramDevice.getCapability("model_name")).length() > 0)
      stringBuilder.append(" ").append(str); 
    if ((str = paramDevice.getCapability("marketing_name")).length() > 0)
      stringBuilder.append(" (").append(str).append(")"); 
    return stringBuilder.toString();
  }
  
  public String getHandledVirtualCapabilityName() {
    return "complete_device_name";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\vcap\CompleteDeviceName.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */