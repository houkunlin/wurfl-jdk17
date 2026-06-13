package com.scientiamobile.wurfl.core;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * 虚拟能力信息的类型安全封装。
 * <p>包含设备的所有虚拟能力评估结果，使用确定的数据类型（{@code boolean} / {@link String}）
 * 替代原始的 {@code Map&lt;String, String&gt;} 形式，使调用方无需自行解析字符串即可直接使用。</p>
 *
 * <p>使用方式：</p>
 * <pre>{@code
 * VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
 * if (info.isMobile() && info.isSmartphone()) {
 *     String os = info.getAdvertisedDeviceOs();
 *     String browser = info.getAdvertisedBrowser();
 *     String deviceName = info.getCompleteDeviceName();
 * }
 * }</pre>
 *
 * <p>本类实现了 {@link Serializable}，可在分布式环境中传递。</p>
 */
public final class VirtualCapabilityInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;

    // ========== boolean 字段 ==========

    private final boolean appWebview;
    private final boolean app;
    private final boolean mobile;
    private final boolean phone;
    private final boolean fullDesktop;
    private final boolean smartphone;
    private final boolean robot;
    private final boolean largescreen;
    private final boolean android;
    private final boolean ios;
    private final boolean windowsPhone;
    private final boolean touchscreen;
    private final boolean wmlPreferred;
    private final boolean xhtmlmpPreferred;
    private final boolean htmlPreferred;

    // ========== String 字段 ==========

    private final String advertisedDeviceOs;
    private final String advertisedDeviceOsVersion;
    private final String advertisedBrowser;
    private final String advertisedBrowserVersion;
    private final String advertisedAppName;
    private final String completeDeviceName;
    private final String deviceName;
    private final String formFactor;
    private final String browserCore;
    private final String browserCoreVersion;

    /**
     * 构造虚拟能力信息对象。
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
     * @param formFactor                设备形态因子
     * @param browserCore               浏览器内核名称
     * @param browserCoreVersion        浏览器内核版本号
     */
    public VirtualCapabilityInfo(
            boolean isAppWebview, boolean isApp, boolean isMobile, boolean isPhone,
            boolean isFullDesktop, boolean isSmartphone, boolean isRobot, boolean isLargescreen,
            boolean isAndroid, boolean isIos, boolean isWindowsPhone, boolean isTouchscreen,
            boolean isWmlPreferred, boolean isXhtmlmpPreferred, boolean isHtmlPreferred,
            String advertisedDeviceOs, String advertisedDeviceOsVersion,
            String advertisedBrowser, String advertisedBrowserVersion,
            String advertisedAppName, String completeDeviceName, String deviceName, String formFactor,
            String browserCore, String browserCoreVersion) {
        this.appWebview = isAppWebview;
        this.app = isApp;
        this.mobile = isMobile;
        this.phone = isPhone;
        this.fullDesktop = isFullDesktop;
        this.smartphone = isSmartphone;
        this.robot = isRobot;
        this.largescreen = isLargescreen;
        this.android = isAndroid;
        this.ios = isIos;
        this.windowsPhone = isWindowsPhone;
        this.touchscreen = isTouchscreen;
        this.wmlPreferred = isWmlPreferred;
        this.xhtmlmpPreferred = isXhtmlmpPreferred;
        this.htmlPreferred = isHtmlPreferred;
        this.advertisedDeviceOs = advertisedDeviceOs;
        this.advertisedDeviceOsVersion = advertisedDeviceOsVersion;
        this.advertisedBrowser = advertisedBrowser;
        this.advertisedBrowserVersion = advertisedBrowserVersion;
        this.advertisedAppName = advertisedAppName;
        this.completeDeviceName = completeDeviceName;
        this.deviceName = deviceName;
        this.formFactor = formFactor;
        this.browserCore = browserCore;
        this.browserCoreVersion = browserCoreVersion;
    }

    // ========================================================================
    //  设备类型判定
    // ========================================================================

    public boolean isAppWebview() {
        return appWebview;
    }

    public boolean isApp() {
        return app;
    }

    public boolean isMobile() {
        return mobile;
    }

    public boolean isPhone() {
        return phone;
    }

    public boolean isFullDesktop() {
        return fullDesktop;
    }

    public boolean isSmartphone() {
        return smartphone;
    }

    public boolean isRobot() {
        return robot;
    }

    public boolean isLargescreen() {
        return largescreen;
    }

    // ========================================================================
    //  操作系统判定
    // ========================================================================

    public boolean isAndroid() {
        return android;
    }

    public boolean isIos() {
        return ios;
    }

    public boolean isWindowsPhone() {
        return windowsPhone;
    }

    // ========================================================================
    //  交互与显示
    // ========================================================================

    public boolean isTouchscreen() {
        return touchscreen;
    }

    // ========================================================================
    //  标记语言偏好
    // ========================================================================

    public boolean isWmlPreferred() {
        return wmlPreferred;
    }

    public boolean isXhtmlmpPreferred() {
        return xhtmlmpPreferred;
    }

    public boolean isHtmlPreferred() {
        return htmlPreferred;
    }

    // ========================================================================
    //  字符串信息
    // ========================================================================

    public String getAdvertisedDeviceOs() {
        return advertisedDeviceOs;
    }

    public String getAdvertisedDeviceOsVersion() {
        return advertisedDeviceOsVersion;
    }

    public String getAdvertisedBrowser() {
        return advertisedBrowser;
    }

    public String getAdvertisedBrowserVersion() {
        return advertisedBrowserVersion;
    }

    public String getAdvertisedAppName() {
        return advertisedAppName;
    }

    public String getCompleteDeviceName() {
        return completeDeviceName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getFormFactor() {
        return formFactor;
    }

    public String getBrowserCore() {
        return browserCore;
    }

    public String getBrowserCoreVersion() {
        return browserCoreVersion;
    }

    // ========================================================================
    //  Object 方法
    // ========================================================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VirtualCapabilityInfo that)) return false;
        return appWebview == that.appWebview && app == that.app
                && mobile == that.mobile && phone == that.phone
                && fullDesktop == that.fullDesktop && smartphone == that.smartphone
                && robot == that.robot && largescreen == that.largescreen
                && android == that.android && ios == that.ios
                && windowsPhone == that.windowsPhone && touchscreen == that.touchscreen
                && wmlPreferred == that.wmlPreferred && xhtmlmpPreferred == that.xhtmlmpPreferred
                && htmlPreferred == that.htmlPreferred
                && Objects.equals(advertisedDeviceOs, that.advertisedDeviceOs)
                && Objects.equals(advertisedDeviceOsVersion, that.advertisedDeviceOsVersion)
                && Objects.equals(advertisedBrowser, that.advertisedBrowser)
                && Objects.equals(advertisedBrowserVersion, that.advertisedBrowserVersion)
                && Objects.equals(advertisedAppName, that.advertisedAppName)
                && Objects.equals(completeDeviceName, that.completeDeviceName)
                && Objects.equals(deviceName, that.deviceName)
                && Objects.equals(formFactor, that.formFactor)
                && Objects.equals(browserCore, that.browserCore)
                && Objects.equals(browserCoreVersion, that.browserCoreVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appWebview, app, mobile, phone, fullDesktop, smartphone,
                robot, largescreen, android, ios, windowsPhone, touchscreen,
                wmlPreferred, xhtmlmpPreferred, htmlPreferred,
                advertisedDeviceOs, advertisedDeviceOsVersion, advertisedBrowser,
                advertisedBrowserVersion, advertisedAppName, completeDeviceName,
                deviceName, formFactor, browserCore, browserCoreVersion);
    }

    @Override
    public String toString() {
        return "VirtualCapabilityInfo{"
                + "appWebview=" + appWebview + ", app=" + app
                + ", mobile=" + mobile + ", phone=" + phone
                + ", fullDesktop=" + fullDesktop + ", smartphone=" + smartphone
                + ", robot=" + robot + ", largescreen=" + largescreen
                + ", android=" + android + ", ios=" + ios
                + ", windowsPhone=" + windowsPhone + ", touchscreen=" + touchscreen
                + ", wmlPreferred=" + wmlPreferred + ", xhtmlmpPreferred=" + xhtmlmpPreferred
                + ", htmlPreferred=" + htmlPreferred
                + ", advertisedDeviceOs='" + advertisedDeviceOs + '\''
                + ", advertisedDeviceOsVersion='" + advertisedDeviceOsVersion + '\''
                + ", advertisedBrowser='" + advertisedBrowser + '\''
                + ", advertisedBrowserVersion='" + advertisedBrowserVersion + '\''
                + ", advertisedAppName='" + advertisedAppName + '\''
                + ", completeDeviceName='" + completeDeviceName + '\''
                + ", deviceName='" + deviceName + '\''
                + ", formFactor='" + formFactor + '\''
                + ", browserCore='" + browserCore + '\''
                + ", browserCoreVersion='" + browserCoreVersion + '\''
                + '}';
    }
}
