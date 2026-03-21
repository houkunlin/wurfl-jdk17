package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

public interface VirtualCapabilityEvaluator {
  public static final String TRUE = "true";
  
  public static final String FALSE = "false";
  
  public static final String DEVICE_OS = "device_os";
  
  public static final String UX_FULL_DESKTOP = "ux_full_desktop";
  
  public static final String VIRTUALCAP_DEVICE_OS_NAME = "advertised_device_os";
  
  public static final String VIRTUALCAP_DEVICE_OS_VERSION = "advertised_device_os_version";
  
  public static final String VIRTUALCAP_DEVICE_BROWSER_NAME = "advertised_browser";
  
  public static final String VIRTUALCAP_DEVICE_BROWSER_VERSION = "advertised_browser_version";
  
  public static final String XHTML_SUPPORT_LEVEL = "xhtml_support_level";
  
  public static final String PREFERRED_MARKUP = "preferred_markup";
  
  public static final String IS_SMARTTV = "is_smarttv";
  
  public static final String IS_WIRELESS_DEVICE = "is_wireless_device";
  
  public static final String IS_TABLET = "is_tablet";
  
  public static final String CAN_ASSIGN_PHONE_NUMBER = "can_assign_phone_number";
  
  public static final String BRAND_NAME = "brand_name";
  
  public static final String MODEL_NAME = "model_name";
  
  public static final String MARKETING_NAME = "marketing_name";
  
  public static final String RESOLUTION_WIDTH = "resolution_width";
  
  public static final String RESOLUTION_HEIGHT = "resolution_height";
  
  public static final String POINTING_METHOD = "pointing_method";
  
  public static final String DEVICE_OS_VERSION = "device_os_version";
  
  public static final String MOBILE_BROWSER_VERSION = "mobile_browser_version";
  
  public static final String[] MANDATORY_CAPABILITIES = new String[] { 
      "device_os", "ux_full_desktop", "xhtml_support_level", "preferred_markup", "is_smarttv", "is_wireless_device", "is_tablet", "can_assign_phone_number", "brand_name", "model_name", 
      "marketing_name", "resolution_width", "resolution_height", "pointing_method", "device_os_version", "mobile_browser_version" };
  
  String eval(Device paramDevice, WURFLRequest paramWURFLRequest);
  
  String getHandledVirtualCapabilityName();
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\vcap\VirtualCapabilityEvaluator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */