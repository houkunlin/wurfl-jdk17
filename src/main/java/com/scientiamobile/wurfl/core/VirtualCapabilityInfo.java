package com.scientiamobile.wurfl.core;

import java.util.Map;

/**
 * 虚拟能力信息的类型安全封装。
 * <p>包含设备的所有虚拟能力评估结果，使用确定的数据类型（boolean / String）
 * 替代原始的 {@link Map Map&lt;String, String&gt;} 形式，方便调用方直接使用。</p>
 *
 * @param isAppWebview              是否应用内 WebView
 * @param isApp                     是否原生应用
 * @param isMobile                  是否移动设备
 * @param isPhone                   是否手机
 * @param isFullDesktop             是否完整桌面端
 * @param isSmartphone              是否智能手机
 * @param isRobot                   是否爬虫/机器人
 * @param isLargescreen             是否大屏设备
 * @param isAndroid                 是否 Android 系统
 * @param isIos                     是否 iOS 系统
 * @param isWindowsPhone            是否 Windows Phone 系统
 * @param isTouchscreen             是否触摸屏
 * @param isWmlPreferred            是否偏好 WML 标记语言
 * @param isXhtmlmpPreferred        是否偏好 XHTML MP 标记语言
 * @param isHtmlPreferred           是否偏好 HTML 标记语言
 * @param advertisedDeviceOs        广告投放用操作系统名称
 * @param advertisedDeviceOsVersion 广告投放用操作系统版本
 * @param advertisedBrowser         广告投放用浏览器名称
 * @param advertisedBrowserVersion  广告投放用浏览器版本
 * @param advertisedAppName         广告投放用应用名称
 * @param completeDeviceName        完整设备名称（含品牌、型号、营销名）
 * @param deviceName                设备简称
 * @param formFactor                设备形态因子（Desktop / Smartphone / Tablet 等）
 */
public record VirtualCapabilityInfo(
        boolean isAppWebview,
        boolean isApp,
        boolean isMobile,
        boolean isPhone,
        boolean isFullDesktop,
        boolean isSmartphone,
        boolean isRobot,
        boolean isLargescreen,
        boolean isAndroid,
        boolean isIos,
        boolean isWindowsPhone,
        boolean isTouchscreen,
        boolean isWmlPreferred,
        boolean isXhtmlmpPreferred,
        boolean isHtmlPreferred,
        String advertisedDeviceOs,
        String advertisedDeviceOsVersion,
        String advertisedBrowser,
        String advertisedBrowserVersion,
        String advertisedAppName,
        String completeDeviceName,
        String deviceName,
        String formFactor
) {
}
