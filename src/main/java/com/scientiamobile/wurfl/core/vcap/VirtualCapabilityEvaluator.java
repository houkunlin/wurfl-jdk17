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

    /**
     * 布尔值 "true" 的字符串常量
     */
    String TRUE = "true";
    /**
     * 布尔值 "false" 的字符串常量
     */
    String FALSE = "false";
    /**
     * 设备操作系统名称的能力键
     */
    String DEVICE_OS = "device_os";
    /**
     * 完整桌面用户体验的能力键
     */
    String UX_FULL_DESKTOP = "ux_full_desktop";
    /**
     * 广告投放所需的设备操作系统名称虚拟能力键
     */
    String VIRTUALCAP_DEVICE_OS_NAME = "advertised_device_os";
    /**
     * 广告投放所需的设备操作系统版本号虚拟能力键
     */
    String VIRTUALCAP_DEVICE_OS_VERSION = "advertised_device_os_version";
    /**
     * 广告投放所需的浏览器名称虚拟能力键
     */
    String VIRTUALCAP_DEVICE_BROWSER_NAME = "advertised_browser";
    /**
     * 广告投放所需的浏览器版本号虚拟能力键
     */
    String VIRTUALCAP_DEVICE_BROWSER_VERSION = "advertised_browser_version";
    /**
     * XHTML 支持级别能力键
     */
    String XHTML_SUPPORT_LEVEL = "xhtml_support_level";
    /**
     * 首选标记语言能力键
     */
    String PREFERRED_MARKUP = "preferred_markup";
    /**
     * 智能电视设备标识能力键
     */
    String IS_SMARTTV = "is_smarttv";
    /**
     * 无线设备标识能力键
     */
    String IS_WIRELESS_DEVICE = "is_wireless_device";
    /**
     * 平板设备标识能力键
     */
    String IS_TABLET = "is_tablet";
    /**
     * 可分配电话号码标识能力键
     */
    String CAN_ASSIGN_PHONE_NUMBER = "can_assign_phone_number";
    /**
     * 设备品牌名称能力键
     */
    String BRAND_NAME = "brand_name";
    /**
     * 设备型号名称能力键
     */
    String MODEL_NAME = "model_name";
    /**
     * 设备营销名称能力键
     */
    String MARKETING_NAME = "marketing_name";
    /**
     * 屏幕分辨率宽度能力键
     */
    String RESOLUTION_WIDTH = "resolution_width";
    /**
     * 屏幕分辨率高度能力键
     */
    String RESOLUTION_HEIGHT = "resolution_height";
    /**
     * 指向方式（输入方式）能力键
     */
    String POINTING_METHOD = "pointing_method";
    /**
     * 设备操作系统版本号能力键
     */
    String DEVICE_OS_VERSION = "device_os_version";
    /**
     * 移动浏览器版本号能力键
     */
    String MOBILE_BROWSER_VERSION = "mobile_browser_version";

    /**
     * 所有虚拟能力评估所必需的基础 WURFL 能力名称列表。
     * <p>在评估任何虚拟能力之前，必须确保设备对象中包含这些能力值。</p>
     */
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
