package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

public class FormFactor extends a {
  private static final long serialVersionUID = -3936563826288495198L;
  
  public String eval(Device paramDevice, WURFLRequest paramWURFLRequest) {
    return paramDevice.getVirtualCapabilityAsBool("is_robot") ? "Robot" : (paramDevice.getCapabilityAsBool("ux_full_desktop") ? "Desktop" : (paramDevice.getCapabilityAsBool("is_smarttv") ? "Smart-TV" : (!paramDevice.getCapabilityAsBool("is_wireless_device") ? "Other Non-Mobile" : (paramDevice.getCapabilityAsBool("is_tablet") ? "Tablet" : (paramDevice.getVirtualCapabilityAsBool("is_smartphone") ? "Smartphone" : (paramDevice.getCapabilityAsBool("can_assign_phone_number") ? "Feature Phone" : "Other Mobile"))))));
  }
  
  public String getHandledVirtualCapabilityName() {
    return "form_factor";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\vcap\FormFactor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */