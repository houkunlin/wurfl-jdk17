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
    private static final long serialVersionUID = 1L;

    /**
     * 是否应用内 WebView（非独立浏览器）
     */
    private final boolean appWebview;
    /**
     * 是否原生应用（非浏览器）
     */
    private final boolean app;
    /**
     * 是否移动设备
     */
    private final boolean mobile;
    /**
     * 是否手机
     */
    private final boolean phone;
    /**
     * 是否完整桌面端
     */
    private final boolean fullDesktop;
    /**
     * 是否智能手机
     */
    private final boolean smartphone;
    /**
     * 是否爬虫/机器人
     */
    private final boolean robot;
    /**
     * 是否大屏设备
     */
    private final boolean largescreen;
    /**
     * 是否 Android 系统
     */
    private final boolean android;
    /**
     * 是否 iOS 系统
     */
    private final boolean ios;
    /**
     * 是否 Windows Phone 系统
     */
    private final boolean windowsPhone;
    /**
     * 是否触摸屏
     */
    private final boolean touchscreen;
    /**
     * 是否偏好 WML 标记语言
     */
    private final boolean wmlPreferred;
    /**
     * 是否偏好 XHTML MP 标记语言
     */
    private final boolean xhtmlmpPreferred;
    /**
     * 是否偏好 HTML 标记语言
     */
    private final boolean htmlPreferred;
    /**
     * 广告投放用操作系统名称
     */
    private final String advertisedDeviceOs;
    /**
     * 广告投放用操作系统版本
     */
    private final String advertisedDeviceOsVersion;
    /**
     * 广告投放用浏览器名称
     */
    private final String advertisedBrowser;
    /**
     * 广告投放用浏览器版本
     */
    private final String advertisedBrowserVersion;
    /**
     * 广告投放用应用名称
     */
    private final String advertisedAppName;
    /**
     * 完整设备名称（品牌 型号 (营销名)）
     */
    private final String completeDeviceName;
    /**
     * 设备简称（品牌 营销名/型号）
     */
    private final String deviceName;
    /**
     * 设备形态因子
     */
    private final String formFactor;

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
     */
    public VirtualCapabilityInfo(
            boolean isAppWebview, boolean isApp, boolean isMobile, boolean isPhone,
            boolean isFullDesktop, boolean isSmartphone, boolean isRobot, boolean isLargescreen,
            boolean isAndroid, boolean isIos, boolean isWindowsPhone, boolean isTouchscreen,
            boolean isWmlPreferred, boolean isXhtmlmpPreferred, boolean isHtmlPreferred,
            String advertisedDeviceOs, String advertisedDeviceOsVersion,
            String advertisedBrowser, String advertisedBrowserVersion,
            String advertisedAppName, String completeDeviceName, String deviceName, String formFactor) {
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
    }

    // ========================================================================
    //  设备类型判定
    // ========================================================================

    /**
     * 请求是否来自应用内 WebView（而非独立浏览器）。
     */
    public boolean isAppWebview() {
        return appWebview;
    }

    /**
     * 请求是否来自原生应用（而非浏览器）。
     */
    public boolean isApp() {
        return app;
    }

    /**
     * 设备是否为移动设备（手机、平板等，与桌面电脑相对）。
     */
    public boolean isMobile() {
        return mobile;
    }

    /**
     * 设备是否为手机（与平板、桌面设备相对）。
     */
    public boolean isPhone() {
        return phone;
    }

    /**
     * 设备是否为完整桌面端。
     */
    public boolean isFullDesktop() {
        return fullDesktop;
    }

    /**
     * 设备是否为智能手机。
     */
    public boolean isSmartphone() {
        return smartphone;
    }

    /**
     * 请求是否来自网络爬虫或机器人程序。
     */
    public boolean isRobot() {
        return robot;
    }

    /**
     * 设备是否具有大屏幕。
     */
    public boolean isLargescreen() {
        return largescreen;
    }

    // ========================================================================
    //  操作系统判定
    // ========================================================================

    /**
     * 设备操作系统是否为 Android。
     */
    public boolean isAndroid() {
        return android;
    }

    /**
     * 设备操作系统是否为 iOS。
     */
    public boolean isIos() {
        return ios;
    }

    /**
     * 设备操作系统是否为 Windows Phone。
     */
    public boolean isWindowsPhone() {
        return windowsPhone;
    }

    // ========================================================================
    //  交互与显示
    // ========================================================================

    /**
     * 设备是否支持触摸屏。
     */
    public boolean isTouchscreen() {
        return touchscreen;
    }

    // ========================================================================
    //  标记语言偏好
    // ========================================================================

    /**
     * 设备是否偏好 WML 标记语言。
     */
    public boolean isWmlPreferred() {
        return wmlPreferred;
    }

    /**
     * 设备是否偏好 XHTML MP 标记语言。
     */
    public boolean isXhtmlmpPreferred() {
        return xhtmlmpPreferred;
    }

    /**
     * 设备是否偏好 HTML 标记语言。
     */
    public boolean isHtmlPreferred() {
        return htmlPreferred;
    }

    // ========================================================================
    //  字符串信息
    // ========================================================================

    /**
     * @return 广告投放用操作系统名称，如 {@code "Android"}、{@code "iOS"}、{@code "Windows"}
     */
    public String getAdvertisedDeviceOs() {
        return advertisedDeviceOs;
    }

    /**
     * @return 广告投放用操作系统版本，如 {@code "14"}、{@code "15.1.1"}
     */
    public String getAdvertisedDeviceOsVersion() {
        return advertisedDeviceOsVersion;
    }

    /**
     * @return 广告投放用浏览器名称，如 {@code "Chrome Mobile"}、{@code "QQ Browser"}
     */
    public String getAdvertisedBrowser() {
        return advertisedBrowser;
    }

    /**
     * @return 广告投放用浏览器版本
     */
    public String getAdvertisedBrowserVersion() {
        return advertisedBrowserVersion;
    }

    /**
     * @return 广告投放用应用名称，如 {@code "Chrome browser"}、{@code "WeChat"}
     */
    public String getAdvertisedAppName() {
        return advertisedAppName;
    }

    /**
     * @return 完整设备名称，如 {@code "Google Pixel 7"}、{@code "Huawei MRX-W09 (MatePad Pro)"}
     */
    public String getCompleteDeviceName() {
        return completeDeviceName;
    }

    /**
     * @return 设备简称，如 {@code "Google Pixel 7"}、{@code "Xiaomi 14 Pro"}
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * @return 设备形态因子，如 {@code "Desktop"}、{@code "Smartphone"}、{@code "Tablet"}
     */
    public String getFormFactor() {
        return formFactor;
    }

    // ========================================================================
    //  Object 方法
    // ========================================================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VirtualCapabilityInfo that)) return false;
        return appWebview == that.appWebview
                && app == that.app
                && mobile == that.mobile
                && phone == that.phone
                && fullDesktop == that.fullDesktop
                && smartphone == that.smartphone
                && robot == that.robot
                && largescreen == that.largescreen
                && android == that.android
                && ios == that.ios
                && windowsPhone == that.windowsPhone
                && touchscreen == that.touchscreen
                && wmlPreferred == that.wmlPreferred
                && xhtmlmpPreferred == that.xhtmlmpPreferred
                && htmlPreferred == that.htmlPreferred
                && Objects.equals(advertisedDeviceOs, that.advertisedDeviceOs)
                && Objects.equals(advertisedDeviceOsVersion, that.advertisedDeviceOsVersion)
                && Objects.equals(advertisedBrowser, that.advertisedBrowser)
                && Objects.equals(advertisedBrowserVersion, that.advertisedBrowserVersion)
                && Objects.equals(advertisedAppName, that.advertisedAppName)
                && Objects.equals(completeDeviceName, that.completeDeviceName)
                && Objects.equals(deviceName, that.deviceName)
                && Objects.equals(formFactor, that.formFactor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appWebview, app, mobile, phone, fullDesktop, smartphone,
                robot, largescreen, android, ios, windowsPhone, touchscreen,
                wmlPreferred, xhtmlmpPreferred, htmlPreferred,
                advertisedDeviceOs, advertisedDeviceOsVersion, advertisedBrowser,
                advertisedBrowserVersion, advertisedAppName, completeDeviceName,
                deviceName, formFactor);
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
                + '}';
    }
}
