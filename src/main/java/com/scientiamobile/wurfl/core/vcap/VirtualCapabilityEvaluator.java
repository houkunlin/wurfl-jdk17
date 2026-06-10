package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

/**
 * 虚拟能力评估器接口。
 * <p>定义了 WURFL 虚拟能力（Virtual Capability）的评估契约。
 * 每个实现类负责计算一项特定的虚拟能力值，如设备是否为 Android、
 * 操作系统版本号、浏览器名称等。
 * 该接口中同时定义了实现类所需的常量能力名称和必备能力列表。</p>
 */

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

    /**
     * 根据设备和请求信息评估虚拟能力的值。
     *
     * @param device  经过 WURFL 匹配的设备对象
     * @param request 当前 HTTP 请求信息
     * @return 虚拟能力的值（通常为 "true"、"false" 或具体的字符串值）
     */
    String eval(Device device, WURFLRequest request);

    /**
     * 获取当前评估器负责的虚拟能力名称。
     *
     * @return 虚拟能力名称，如 "is_android"、"advertised_device_os"
     */
    String getHandledVirtualCapabilityName();
}
