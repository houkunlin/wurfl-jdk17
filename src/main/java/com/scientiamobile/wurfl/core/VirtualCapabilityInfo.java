package com.scientiamobile.wurfl.core;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * 虚拟能力信息的类型安全封装。
 * <p>包含设备的所有虚拟能力评估结果，使用确定的数据类型（{@code boolean} / {@link String}）
 * 替代原始的 {@code Map&lt;String, String&gt;} 形式，使调用方无需自行解析字符串即可直接使用。</p>
 *
 * <p>虚拟能力主要分为以下几类：</p>
 * <ul>
 *   <li><b>设备类型判定</b> — {@link #isMobile()}、{@link #isPhone()}、{@link #isSmartphone()}、
 *       {@link #isFullDesktop()} 等</li>
 *   <li><b>操作系统判定</b> — {@link #isAndroid()}、{@link #isIos()}、{@link #isWindowsPhone()}</li>
 *   <li><b>交互与显示</b> — {@link #isTouchscreen()}、{@link #isLargescreen()}</li>
 *   <li><b>标记语言偏好</b> — {@link #isHtmlPreferred()}、{@link #isWmlPreferred()}、
 *       {@link #isXhtmlmpPreferred()}</li>
 *   <li><b>浏览器与操作系统信息</b> — {@link #getAdvertisedBrowser()}、{@link #getAdvertisedDeviceOs()}、
 *       {@link #getBrowserCore()} 等</li>
 *   <li><b>设备形态</b> — {@link #getFormFactor()}、{@link #getDeviceName()}、
 *       {@link #getCompleteDeviceName()}</li>
 * </ul>
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
 *
 * @see DefaultDevice#getVirtualCapabilityInfo()
 * @see com.scientiamobile.wurfl.core.vcap.VirtualCapabilityHandler
 */
public final class VirtualCapabilityInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;

    // ========== boolean 字段 ==========

    /**
     * 是否应用内 WebView（原生应用内嵌浏览器）。{@code true} 表示请求来自 App 内嵌 WebView。
     */
    private final boolean appWebview;
    /**
     * 是否原生应用（非浏览器）。{@code true} 表示请求来自原生 App。
     */
    private final boolean app;
    /**
     * 是否移动设备（手机、平板等便携设备）。与 {@link #fullDesktop} 互斥。
     */
    private final boolean mobile;
    /** 是否手机（可分配电话号码且非平板）。与 {@link #fullDesktop} 互斥。 */
    private final boolean phone;
    /** 是否完整桌面端（非移动版网站请求）。与 {@link #mobile} 互斥。 */
    private final boolean fullDesktop;
    /** 是否智能手机（触屏 + 智能操作系统 + 分辨率 ≥ 320px）。 */
    private final boolean smartphone;
    /** 是否网络爬虫/机器人程序。 */
    private final boolean robot;
    /** 是否大屏设备（分辨率 ≥ 480x480）。含大屏手机和平板。 */
    private final boolean largescreen;
    /** 是否 Android 操作系统。 */
    private final boolean android;
    /** 是否 iOS 操作系统。 */
    private final boolean ios;
    /** 是否 Windows Phone 操作系统。 */
    private final boolean windowsPhone;
    /** 是否触摸屏设备（支持触摸交互）。 */
    private final boolean touchscreen;
    /** 是否偏好 WML（WAP 标记语言），多见于老旧功能机。 */
    private final boolean wmlPreferred;
    /** 是否偏好 XHTML MP（XHTML Mobile Profile）。与 {@link #htmlPreferred} 互斥。 */
    private final boolean xhtmlmpPreferred;
    /** 是否偏好 HTML Web（桌面级网页渲染）。与 {@link #xhtmlmpPreferred} 互斥。 */
    private final boolean htmlPreferred;

    // ========== String 字段 ==========

    /** 广告投放用操作系统名称，优先从 UA 解析（如 "Android"、"iOS"、"Windows"）。 */
    private final String advertisedDeviceOs;
    /** 广告投放用操作系统版本号，优先从 UA 解析（如 "14"、"16"）。 */
    private final String advertisedDeviceOsVersion;
    /** 广告投放用浏览器名称，从 UA 解析（如 "Chrome"、"UC Browser"、"HeyTap Browser"）。 */
    private final String advertisedBrowser;
    /** 广告投放用浏览器版本号，从 UA 解析（如 "120.0.0.0"、"18.9.0.1516"）。 */
    private final String advertisedBrowserVersion;
    /** 广告投放用应用名称，从 UA 关键词匹配识别（如 "QQ Browser"、"WeChat"、"UCBrowser"）。 */
    private final String advertisedAppName;
    /**
     * 完整设备名称，格式为 "{brand_name} {model_name} ({marketing_name})"。
     * <p>示例：{@code "OnePlus PJD110 (12)"}、{@code "Google Pixel 7"}。</p>
     */
    private final String completeDeviceName;
    /**
     * 设备简称，格式为 "{brand_name} {marketing_name}"（优先）或 "{brand_name} {model_name}"。
     * <p>示例：{@code "OnePlus 12"}、{@code "Google Pixel 7"}。</p>
     */
    private final String deviceName;
    /**
     * 设备形态因子，由 {@link com.scientiamobile.wurfl.core.vcap.FormFactor} 评估。
     * <p>可能值：{@code Desktop}、{@code Smartphone}、{@code Tablet}、{@code Feature Phone}、
     * {@code Smart-TV}、{@code Robot}、{@code Other Non-Mobile}、{@code Other Mobile}。</p>
     */
    private final String formFactor;
    /**
     * 浏览器内核名称。
     * <p>原生浏览器返回自身名称（{@code "Chrome"}、{@code "Firefox"}、{@code "Safari"}），
     * 套壳浏览器返回底层内核（{@code "Chrome"}、{@code "WebKit"}），无内核信息时返回空字符串。</p>
     */
    private final String browserCore;
    /**
     * 浏览器内核版本号。
     * <p>如 {@code "120.0.0.0"}（Chrome 内核）、{@code "605.1.15"}（WebKit 引擎）。
     * 无内核信息时返回空字符串。</p>
     */
    private final String browserCoreVersion;

    /**
     * 构造虚拟能力信息对象。
     * <p>所有参数直接赋值给对应字段，不做额外校验或转换。
     * 调用方（通常为 {@link DefaultDevice#getVirtualCapabilityInfo()}）应确保传入
     * 的值已经过虚拟能力评估器的正确计算。</p>
     *
     * @param isAppWebview              是否应用内 WebView
     * @param isApp                     是否原生应用
     * @param isMobile                  是否移动设备
     * @param isPhone                   是否手机（可分配电话号码且非平板）
     * @param isFullDesktop             是否完整桌面端
     * @param isSmartphone              是否智能手机（触屏 + 智能 OS + 分辨率 ≥ 320px）
     * @param isRobot                   是否爬虫/机器人
     * @param isLargescreen             是否大屏设备（分辨率 ≥ 480x480）
     * @param isAndroid                 是否 Android 系统
     * @param isIos                     是否 iOS 系统
     * @param isWindowsPhone            是否 Windows Phone 系统
     * @param isTouchscreen             是否触摸屏
     * @param isWmlPreferred            是否偏好 WML 标记语言
     * @param isXhtmlmpPreferred        是否偏好 XHTML MP 标记语言
     * @param isHtmlPreferred           是否偏好 HTML 标记语言
     * @param advertisedDeviceOs        广告投放用操作系统名称
     * @param advertisedDeviceOsVersion 广告投放用操作系统版本号
     * @param advertisedBrowser         广告投放用浏览器名称
     * @param advertisedBrowserVersion  广告投放用浏览器版本号
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

    /**
     * 判断请求是否来自应用内 WebView（原生应用内嵌浏览器）。
     *
     * @return 如果是应用内 WebView 返回 {@code true}
     */
    public boolean isAppWebview() {
        return appWebview;
    }

    /**
     * 判断请求是否来自原生应用（非浏览器）。
     * <p>与 {@link #isAppWebview()} 的区别：部分 App 使用系统浏览器打开链接（非内嵌 WebView），
     * 此时 {@code isApp=true} 但 {@code isAppWebview=false}。</p>
     *
     * @return 如果是原生应用返回 {@code true}
     */
    public boolean isApp() {
        return app;
    }

    /**
     * 判断是否移动设备（手机、平板等便携设备）。
     *
     * @return 如果是移动设备返回 {@code true}
     */
    public boolean isMobile() {
        return mobile;
    }

    /**
     * 判断是否手机（可分配电话号码且非平板）。
     *
     * @return 如果是手机返回 {@code true}
     */
    public boolean isPhone() {
        return phone;
    }

    /**
     * 判断是否完整桌面端。
     * <p>桌面端浏览器通常请求桌面版网站（非移动版），
     * 即使运行在触屏设备上。</p>
     *
     * @return 如果是桌面端返回 {@code true}
     */
    public boolean isFullDesktop() {
        return fullDesktop;
    }

    /**
     * 判断是否智能手机。
     * <p>智能手机需满足：触屏、智能操作系统（Android / iOS / 等）、
     * 无线设备、分辨率宽度 ≥ 320px。</p>
     *
     * @return 如果是智能手机返回 {@code true}
     */
    public boolean isSmartphone() {
        return smartphone;
    }

    /**
     * 判断是否网络爬虫/机器人程序。
     *
     * @return 如果是爬虫返回 {@code true}
     */
    public boolean isRobot() {
        return robot;
    }

    /**
     * 判断是否大屏设备（分辨率 ≥ 480x480）。
     * <p>大屏设备可能是大屏手机、平板或桌面端。</p>
     *
     * @return 如果是大屏设备返回 {@code true}
     */
    public boolean isLargescreen() {
        return largescreen;
    }

    // ========================================================================
    //  操作系统判定
    // ========================================================================

    /**
     * 判断是否 Android 操作系统。
     *
     * @return 如果是 Android 返回 {@code true}
     */
    public boolean isAndroid() {
        return android;
    }

    /**
     * 判断是否 iOS 操作系统。
     *
     * @return 如果是 iOS 返回 {@code true}
     */
    public boolean isIos() {
        return ios;
    }

    /**
     * 判断是否 Windows Phone 操作系统。
     *
     * @return 如果是 Windows Phone 返回 {@code true}
     */
    public boolean isWindowsPhone() {
        return windowsPhone;
    }

    // ========================================================================
    //  交互与显示
    // ========================================================================

    /**
     * 判断是否触摸屏设备。
     *
     * @return 如果是触摸屏返回 {@code true}
     */
    public boolean isTouchscreen() {
        return touchscreen;
    }

    // ========================================================================
    //  标记语言偏好
    // ========================================================================

    /**
     * 判断是否偏好 WML（WAP 标记语言）。
     * <p>多见于老旧功能机，WML 是 WAP 1.x 时代的标记语言。</p>
     *
     * @return 如果偏好 WML 返回 {@code true}
     */
    public boolean isWmlPreferred() {
        return wmlPreferred;
    }

    /**
     * 判断是否偏好 XHTML MP（XHTML Mobile Profile）。
     * <p>介于 WML 和 HTML Web 之间的移动端标记语言，常见于中端功能机。
     * 与 {@link #isHtmlPreferred()} 互斥。</p>
     *
     * @return 如果偏好 XHTML MP 返回 {@code true}
     */
    public boolean isXhtmlmpPreferred() {
        return xhtmlmpPreferred;
    }

    /**
     * 判断是否偏好 HTML Web（桌面级网页渲染）。
     * <p>智能设备通常偏好 HTML Web，具备完整的桌面级网页渲染能力。
     * 与 {@link #isXhtmlmpPreferred()} 互斥。</p>
     *
     * @return 如果偏好 HTML Web 返回 {@code true}
     */
    public boolean isHtmlPreferred() {
        return htmlPreferred;
    }

    // ========================================================================
    //  字符串信息
    // ========================================================================

    /**
     * 获取广告投放用操作系统名称。
     * <p>优先从 User-Agent 字符串中解析，而非直接从设备能力值获取，
     * 以反映请求实际携带的 OS 信息。如 {@code "Android"}、{@code "iOS"}、{@code "Windows"}。</p>
     *
     * @return 操作系统名称
     */
    public String getAdvertisedDeviceOs() {
        return advertisedDeviceOs;
    }

    /**
     * 获取广告投放用操作系统版本号。
     * <p>优先从 UA 中解析，如 {@code "14"}（Android 14）、{@code "16"}（Android 16）、
     * {@code "10.15.7"}（macOS Catalina）。</p>
     *
     * @return 操作系统版本号字符串
     */
    public String getAdvertisedDeviceOsVersion() {
        return advertisedDeviceOsVersion;
    }

    /**
     * 获取广告投放用浏览器名称。
     * <p>从 UA 中解析浏览器名称，如 {@code "Chrome"}、{@code "UC Browser"}、
     * {@code "HeyTap Browser"}、{@code "Firefox"}、{@code "Mobile Safari"} 等。</p>
     *
     * @return 浏览器名称
     */
    public String getAdvertisedBrowser() {
        return advertisedBrowser;
    }

    /**
     * 获取广告投放用浏览器版本号。
     * <p>从 UA 中解析浏览器版本，如 {@code "120.0.0.0"}（Chrome）、
     * {@code "18.9.0.1516"}（UC Browser）、{@code "40.10.17.10"}（HeyTap Browser）。</p>
     *
     * @return 浏览器版本号字符串
     */
    public String getAdvertisedBrowserVersion() {
        return advertisedBrowserVersion;
    }

    /**
     * 获取广告投放用应用名称。
     * <p>通过 UA 关键词匹配识别请求来源 App 名称，
     * 如 {@code "QQ Browser"}、{@code "WeChat"}、{@code "UCBrowser"}、
     * {@code "HeyTap Browser"} 等。无法识别时返回 {@code "Stock Browser"}。</p>
     *
     * @return 应用名称
     */
    public String getAdvertisedAppName() {
        return advertisedAppName;
    }

    /**
     * 获取完整设备名称。
     * <p>格式为 "{brand_name} {model_name} ({marketing_name})"。
     * 当 marketing_name 为空时格式为 "{brand_name} {model_name}"。</p>
     * <p>示例：{@code "OnePlus PJD110 (12)"}、{@code "Google Pixel 7"}。</p>
     *
     * @return 完整设备名称
     */
    public String getCompleteDeviceName() {
        return completeDeviceName;
    }

    /**
     * 获取设备简称。
     * <p>优先使用 marketing_name 作为显示名称（如 {@code "OnePlus 12"}），
     * 回退到 model_name（如 {@code "Generic Android 16.0"}）。</p>
     *
     * @return 设备简称
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * 获取设备形态因子。
     * <p>由 {@link com.scientiamobile.wurfl.core.vcap.FormFactor} 评估器综合多项能力值判断。
     * 可能返回值：</p>
     * <ul>
     *   <li>{@code "Desktop"} — 桌面端</li>
     *   <li>{@code "Smartphone"} — 智能手机</li>
     *   <li>{@code "Tablet"} — 平板</li>
     *   <li>{@code "Feature Phone"} — 功能机</li>
     *   <li>{@code "Smart-TV"} — 智能电视</li>
     *   <li>{@code "Robot"} — 爬虫</li>
     *   <li>{@code "Other Non-Mobile"} / {@code "Other Mobile"} — 其他</li>
     * </ul>
     *
     * @return 形态因子名称
     */
    public String getFormFactor() {
        return formFactor;
    }

    /**
     * 获取浏览器内核名称。
     * <p>原生浏览器返回自身名称作为内核标识：</p>
     * <ul>
     *   <li>{@code "Chrome"} — Chrome / Edge / 360 / QQ / 百度 / 欢太 等 Blink 内核浏览器</li>
     *   <li>{@code "Firefox"} — 原生 Firefox（Gecko 引擎）</li>
     *   <li>{@code "Safari"} — 原生 Safari / iOS WebView / iOS QQ/微信（WebKit 引擎）</li>
     * </ul>
     * <p>无内核信息时返回空字符串。</p>
     *
     * @return 浏览器内核名称
     */
    public String getBrowserCore() {
        return browserCore;
    }

    /**
     * 获取浏览器内核版本号。
     * <p>与 {@link #getBrowserCore()} 对应：</p>
     * <ul>
     *   <li>Chrome 内核 → {@code "120.0.0.0"}（Chrome 版本号）</li>
     *   <li>Firefox 内核 → {@code "120.0"}（Firefox 版本号）</li>
     *   <li>Safari 内核 → {@code "605.1.15"}（AppleWebKit 版本号）</li>
     * </ul>
     * <p>无内核信息时返回空字符串。</p>
     *
     * @return 浏览器内核版本号
     */
    public String getBrowserCoreVersion() {
        return browserCoreVersion;
    }

    // ========================================================================
    //  Object 方法
    // ========================================================================

    /**
     * 判断当前对象与另一对象是否相等。
     * <p>所有布尔字段和字符串字段均参与比较，仅当所有字段值完全一致时返回 {@code true}。
     * 可用作测试断言中的预期结果对象。</p>
     *
     * @param o 待比较的对象
     * @return 如果所有字段值相等返回 {@code true}
     */
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

    /**
     * 计算当前对象的哈希码。
     * <p>所有字段参与哈希计算，与 {@link #equals(Object)} 使用的字段集一致，
     * 满足 {@code equals} 与 {@code hashCode} 的通用约定。</p>
     *
     * @return 哈希码
     */
    @Override
    public int hashCode() {
        return Objects.hash(appWebview, app, mobile, phone, fullDesktop, smartphone,
                robot, largescreen, android, ios, windowsPhone, touchscreen,
                wmlPreferred, xhtmlmpPreferred, htmlPreferred,
                advertisedDeviceOs, advertisedDeviceOsVersion, advertisedBrowser,
                advertisedBrowserVersion, advertisedAppName, completeDeviceName,
                deviceName, formFactor, browserCore, browserCoreVersion);
    }

    /**
     * 返回当前对象的字符串表示。
     * <p>格式为 {@code VirtualCapabilityInfo{field1=value1, field2='value2', ...}}，
     * 包含所有布尔字段和字符串字段的当前值，便于调试和日志输出。</p>
     *
     * @return 包含所有字段值的字符串
     */
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
