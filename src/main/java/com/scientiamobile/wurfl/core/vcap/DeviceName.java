package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;

public class DeviceName implements VirtualCapabilityEvaluator, Serializable {
  private static final long serialVersionUID = 6339082037173595673L;
  
  public String eval(Device paramDevice, WURFLRequest paramWURFLRequest) {
    StringBuilder stringBuilder = new StringBuilder(paramDevice.getCapability("brand_name"));
    String str;
    if ((str = paramDevice.getCapability("marketing_name")).length() == 0)
      str = paramDevice.getCapability("model_name"); 
    stringBuilder.append(" ").append(str);
    return stringBuilder.toString().trim();
  }
  
  public String getHandledVirtualCapabilityName() {
    return "device_name";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\vcap\DeviceName.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */