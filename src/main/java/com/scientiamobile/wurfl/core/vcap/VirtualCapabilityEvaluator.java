package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

public interface VirtualCapabilityEvaluator {
    String TRUE = "true";
    String FALSE = "false";
    String DEVICE_OS = "device_os";
    String UX_FULL_DESKTOP = "ux_full_desktop";
    String VIRTUALCAP_DEVICE_OS_NAME = "advertised_device_os";
    String VIRTUALCAP_DEVICE_OS_VERSION = "advertised_device_os_version";
    String VIRTUALCAP_DEVICE_BROWSER_NAME = "advertised_browser";
    String VIRTUALCAP_DEVICE_BROWSER_VERSION = "advertised_browser_version";
    String XHTML_SUPPORT_LEVEL = "xhtml_support_level";
    String PREFERRED_MARKUP = "preferred_markup";
    String IS_SMARTTV = "is_smarttv";
    String IS_WIRELESS_DEVICE = "is_wireless_device";
    String IS_TABLET = "is_tablet";
    String CAN_ASSIGN_PHONE_NUMBER = "can_assign_phone_number";
    String BRAND_NAME = "brand_name";
    String MODEL_NAME = "model_name";
    String MARKETING_NAME = "marketing_name";
    String RESOLUTION_WIDTH = "resolution_width";
    String RESOLUTION_HEIGHT = "resolution_height";
    String POINTING_METHOD = "pointing_method";
    String DEVICE_OS_VERSION = "device_os_version";
    String MOBILE_BROWSER_VERSION = "mobile_browser_version";
    String[] MANDATORY_CAPABILITIES = new String[]{"device_os", "ux_full_desktop", "xhtml_support_level", "preferred_markup", "is_smarttv", "is_wireless_device", "is_tablet", "can_assign_phone_number", "brand_name", "model_name", "marketing_name", "resolution_width", "resolution_height", "pointing_method", "device_os_version", "mobile_browser_version"};

    String eval(Device device, WURFLRequest request);

    String getHandledVirtualCapabilityName();
}
