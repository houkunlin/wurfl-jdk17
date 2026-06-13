package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.VirtualCapabilityDevice;
import com.scientiamobile.wurfl.core.VirtualCapabilityUserAgentTool;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 虚拟能力评估器的抽象基类。
 * <p>提供多个子类共享的静态工具方法，包括 Android/iOS User-Agent 模式匹配、
 * App 检测、机器人检测、智能手机判定等。子类继承该类后可直接使用
 * 这些受保护的静态方法，无需重复实现。
 * 该类实现了 {@link Serializable} 以支持分布式环境下的序列化需求。</p>
 */

abstract class AbstractVirtualCapabilityEvaluator implements VirtualCapabilityEvaluator, Serializable {

    /**
     * 匹配 Android WebKit 内核浏览器的基础 User-Agent 模式
     */
    protected static final Pattern ANDROID_WEBKIT_KHTML_PATTERN = Pattern.compile("Mozilla/5.0 \\(Linux;( U;)? Android.*AppleWebKit.*\\(KHTML, like Gecko\\)");

    /**
     * 匹配 Android 旧版 Safari 浏览器的完整 User-Agent 模式（Android 1-4）
     */
    protected static final Pattern ANDROID_LEGACY_SAFARI_UA_PATTERN;

    /**
     * 匹配 Chrome 浏览器主版本号
     */
    protected static final Pattern CHROME_MAJOR_VERSION_PATTERN;

    /**
     * 匹配 Android 1.x-4.x 的旧版本号模式
     */
    protected static final Pattern ANDROID_LEGACY_VERSION_PATTERN;

    /**
     * 用于识别 App 请求的关键词模式列表（支持前缀匹配和包含匹配）
     */
    protected static final List<String> APP_INDICATOR_PATTERNS;

    /**
     * Android 系统内置浏览器的包名集合
     */
    static final Set<String> ANDROID_BROWSER_PACKAGE_NAMES;

    /**
     * 明确标记为非 App 浏览器的关键词集合
     */
    static final Set<String> NON_APP_BROWSER_KEYWORDS;

    @Serial
    private static final long serialVersionUID = 8192401578396133213L;

    /**
     * 提取主版本号和次版本号的正则模式（如 "4.3" 或 "10"）
     */
    private static final Pattern MAJOR_MINOR_VERSION_PATTERN;

    /**
     * Android 应用中 X-Requested-With 头值为 App 包的集合
     */
    private static final Set<String> ANDROID_REQUESTED_WITH_APP_PACKAGES;

    /**
     * 在机器人检测中需要排除的误判关键词
     */
    private static final List<String> BOT_EXCLUSION_KEYWORDS;

    /**
     * 初始化所有虚拟能力评估所需的静态常量和模式。
     * <p>初始化内容包括：
     * <ul>
     *   <li>Android 旧版 Safari 浏览器 User-Agent 匹配模式</li>
     *   <li>Chrome 主版本号和 Android 旧版本号提取模式</li>
     *   <li>系统内置浏览器包名集合</li>
     *   <li>已知 App 的 X-Requested-With 包名集合</li>
     *   <li>非 App 浏览器关键词排除集合</li>
     *   <li>App 特征指示符关键词列表</li>
     *   <li>机器人检测排除关键词列表</li>
     *   <li>主次版本号提取模式</li>
     * </ul></p>
     */
    static {
        Pattern.compile("^Mozilla/5.0 \\(Linux; Android [45]\\.[\\d\\.]+; .+ Build/.+\\) AppleWebKit/[\\d\\.+]+ \\(KHTML, like Gecko\\) Version/[\\d\\.]+ Chrome/([\\d]+)\\.[\\d\\.]+? (?:Mobile )?Safari/[\\d\\.+]+$");
        ANDROID_LEGACY_SAFARI_UA_PATTERN = Pattern.compile("^Mozilla/5.0 \\(Linux;( U;)? Android [1234]\\.[\\d\\.]+(-update1)?; [a-zA-Z]+-[a-zA-Z]+; .+ Build/.+\\) AppleWebKit/[\\d\\.+]+ \\(KHTML, like Gecko\\) Version/[\\d\\.]+ (Mobile )?Safari/[\\d\\.+]+$");
        CHROME_MAJOR_VERSION_PATTERN = Pattern.compile("Chrome/(\\d+)\\.");
        ANDROID_LEGACY_VERSION_PATTERN = Pattern.compile("Android [1234]\\.[123]");
        ANDROID_BROWSER_PACKAGE_NAMES = new HashSet<>();
        ANDROID_REQUESTED_WITH_APP_PACKAGES = new HashSet<>();
        NON_APP_BROWSER_KEYWORDS = new HashSet<>();
        APP_INDICATOR_PATTERNS = new ArrayList<>();
        BOT_EXCLUSION_KEYWORDS = new ArrayList<>(3);
        MAJOR_MINOR_VERSION_PATTERN = Pattern.compile("^(\\d+(?:\\.\\d+)?).*");
        BOT_EXCLUSION_KEYWORDS.add("CUBOT");
        BOT_EXCLUSION_KEYWORDS.add("Cubot");
        BOT_EXCLUSION_KEYWORDS.add("Botswana");
        APP_INDICATOR_PATTERNS.add("^Dalvik");
        APP_INDICATOR_PATTERNS.add("Darwin/");
        APP_INDICATOR_PATTERNS.add("CFNetwork");
        APP_INDICATOR_PATTERNS.add("^Windows Phone Ad Client");
        APP_INDICATOR_PATTERNS.add("^NativeHost");
        APP_INDICATOR_PATTERNS.add("^AndroidDownloadManager");
        APP_INDICATOR_PATTERNS.add("-HttpClient");
        APP_INDICATOR_PATTERNS.add("^AppCake");
        APP_INDICATOR_PATTERNS.add("AppEngine-Google");
        APP_INDICATOR_PATTERNS.add("AppleCoreMedia");
        APP_INDICATOR_PATTERNS.add("^AppTrailers");
        APP_INDICATOR_PATTERNS.add("^ChoiceFM");
        APP_INDICATOR_PATTERNS.add("^ClassicFM");
        APP_INDICATOR_PATTERNS.add("^Clipfish");
        APP_INDICATOR_PATTERNS.add("^FaceFighter");
        APP_INDICATOR_PATTERNS.add("^Flixster");
        APP_INDICATOR_PATTERNS.add("^Gold/");
        APP_INDICATOR_PATTERNS.add("^GoogleAnalytics/");
        APP_INDICATOR_PATTERNS.add("^Heart/");
        APP_INDICATOR_PATTERNS.add("^iBrowser/");
        APP_INDICATOR_PATTERNS.add("iTunes-");
        APP_INDICATOR_PATTERNS.add("^Java/");
        APP_INDICATOR_PATTERNS.add("^LBC/3.");
        APP_INDICATOR_PATTERNS.add("Twitter");
        APP_INDICATOR_PATTERNS.add("Pinterest");
        APP_INDICATOR_PATTERNS.add("^Instagram");
        APP_INDICATOR_PATTERNS.add("FBAN");
        APP_INDICATOR_PATTERNS.add("#iP(hone|od|ad)[\\d],[\\d]");
        APP_INDICATOR_PATTERNS.add("#com(?:\\.[a-z]+){2,}");
        APP_INDICATOR_PATTERNS.add("#net(?:\\.[a-z]+){2,}");
        APP_INDICATOR_PATTERNS.add("WebView");
        APP_INDICATOR_PATTERNS.add("FB_IAB");
        APP_INDICATOR_PATTERNS.add("FB4A");
        APP_INDICATOR_PATTERNS.add("MobileApp");
        APP_INDICATOR_PATTERNS.add("DesktopApp");
        APP_INDICATOR_PATTERNS.add("^mShop:::");
        APP_INDICATOR_PATTERNS.add(" GSA/");
        ANDROID_BROWSER_PACKAGE_NAMES.add("com.android.browser");
        ANDROID_BROWSER_PACKAGE_NAMES.add("com.htc.sense.browser");
        ANDROID_BROWSER_PACKAGE_NAMES.add("com.asus.browser");
        ANDROID_BROWSER_PACKAGE_NAMES.add("com.google.android.browser");
        ANDROID_BROWSER_PACKAGE_NAMES.add("com.lenovo.browser");
        ANDROID_BROWSER_PACKAGE_NAMES.add("com.huawei.android.browser");
        ANDROID_REQUESTED_WITH_APP_PACKAGES.add("com.facebook.katana");
        ANDROID_REQUESTED_WITH_APP_PACKAGES.add("com.ksmobile.cb");
        ANDROID_REQUESTED_WITH_APP_PACKAGES.add("com.nhn.android.search");
        ANDROID_REQUESTED_WITH_APP_PACKAGES.add("app.staples");
        ANDROID_REQUESTED_WITH_APP_PACKAGES.add("flipboard.app");
        ANDROID_REQUESTED_WITH_APP_PACKAGES.add("com.google.android.apps.magazines");
        ANDROID_REQUESTED_WITH_APP_PACKAGES.add("com.pandora.android");
        ANDROID_REQUESTED_WITH_APP_PACKAGES.add("com.stumbleupon.android.app");
        NON_APP_BROWSER_KEYWORDS.add("UCBrowser");
        NON_APP_BROWSER_KEYWORDS.add("Opera");
        NON_APP_BROWSER_KEYWORDS.add(" OPR/");
        NON_APP_BROWSER_KEYWORDS.add("YaBrowser");
        NON_APP_BROWSER_KEYWORDS.add("MiuiBrowser");
        NON_APP_BROWSER_KEYWORDS.add("MQQBrowser");
        NON_APP_BROWSER_KEYWORDS.add("Quark");
        NON_APP_BROWSER_KEYWORDS.add("CriOS");
        NON_APP_BROWSER_KEYWORDS.add("Firefox");
    }

    /**
     * 判断设备是否为 iOS 且非 Safari 浏览器。
     * <p>iOS 设备如果 User-Agent 中不包含 "Safari" 标识，
     * 通常意味着请求来自 App 内嵌 WebView 或非 Safari 浏览器。</p>
     *
     * @param deviceOs  设备操作系统名称
     * @param userAgent User-Agent 字符串
     * @return 如果是 iOS 且非 Safari 则返回 {@code true}
     */
    protected static boolean isIosNonSafari(String deviceOs, String userAgent) {
        return "iOS".equals(deviceOs) && !userAgent.contains("Safari");
    }

    /**
     * 判断设备是否为 macOS 且非 Safari 浏览器。
     *
     * @param device    设备对象
     * @param userAgent User-Agent 字符串
     * @param request   请求对象
     * @return 如果是 macOS 且非 Safari 则返回 {@code true}
     */
    protected static boolean isMacOsNonSafari(Device device, String userAgent, WURFLRequest request) {
        VirtualCapabilityDevice virtualCapabilityDevice = VirtualCapabilityUserAgentTool.getInstance().assignProperties(request, device);
        return "Mac OS X".equals(virtualCapabilityDevice.getOsPairName()) && !userAgent.contains("Safari");
    }

    /**
     * 检查请求的 X-Requested-With 头是否匹配已知的 App 包名。
     *
     * @param expectedOs 期望的操作系统
     * @param deviceOs   实际设备操作系统
     * @param request    请求对象
     * @return 如果匹配已知 App 包名则返回 {@code true}
     */
    protected static boolean isRequestedWithAppPackage(String expectedOs, String deviceOs, WURFLRequest request) {
        return isRequestedWithAppPackage(expectedOs, deviceOs, request.getHeader("X-Requested-With"));
    }

    /**
     * 检查 X-Requested-With 头的具体值是否匹配已知的 App 包名。
     * <p>Android App 在发起 WebView 请求时通常会设置该头为自身的包名。
     * 通过预先维护的已知 App 包名集合，可以判断请求是否来自特定应用。</p>
     *
     * @param expectedOs    期望的操作系统
     * @param deviceOs      实际设备操作系统
     * @param requestedWith X-Requested-With 头的值
     * @return 如果匹配已知 App 包名则返回 {@code true}
     */
    protected static boolean isRequestedWithAppPackage(String expectedOs, String deviceOs, String requestedWith) {
        return expectedOs.equals(deviceOs) && StringUtils.isNotEmpty(requestedWith) && ANDROID_REQUESTED_WITH_APP_PACKAGES.contains(requestedWith);
    }

    /**
     * 判断当前请求是否来自网络爬虫或机器人程序。
     * <p>检测逻辑包括：
     * <ul>
     *   <li>检查 Accept-Encoding 头中是否缺少 deflate（IE 的 Trident 引擎特征之一）</li>
     *   <li>排除 User-Agent 中包含 CUBOT/Cubot/Botswana 等误报关键词的设备</li>
     *   <li>委托 {@link WURFLRequest#_internalIsBot()} 进行进一步检测</li>
     * </ul></p>
     *
     * @param request 请求对象
     * @return 如果是机器人则返回 {@code true}
     */
    protected static boolean isRobot(WURFLRequest request) {
        Map<String, String> headers = request.getHeaders();
        String userAgent = request.isUrlEncoded() ? request.getCleanedDeviceUserAgent() : request.getOriginalUserAgent();
        String acceptEncoding;
        acceptEncoding = headers.get("Accept-Encoding");
        if (headers.containsKey("Accept-Encoding") && userAgent.contains("Trident/") && acceptEncoding != null && !acceptEncoding.contains("deflate")) {
            return true;
        } else {
            for (String keyword : BOT_EXCLUSION_KEYWORDS) {
                if (userAgent.contains(keyword)) {
                    return false;
                }
            }

            return request._internalIsBot();
        }
    }

    /**
     * 综合判断设备是否为智能手机。
     * <p>检查维度包括：
     * <ul>
     *   <li>分辨率宽度（>= 320px）</li>
     *   <li>是否为无线设备</li>
     *   <li>非平板设备</li>
     *   <li>可以分配电话号码</li>
     *   <li>触屏输入方式</li>
     *   <li>操作系统及版本号要求：iOS >= 3.0, Android >= 2.2,
     *       Windows Phone / webOS / MeeGo 任意版本,
     *       RIM OS >= 7.0, Bada OS >= 2.0</li>
     * </ul></p>
     *
     * @param device 设备对象
     * @return 如果是智能手机则返回 {@code true}
     */
    protected static boolean isSmartphone(Device device) {
        int resolutionWidth = parseResolutionWidth(device);
        if (resolutionWidth < 0) {
            return false;
        }

        if ("false".equals(device.getCapability("is_wireless_device"))
                || "true".equals(device.getCapability("is_tablet"))
                || "false".equals(device.getCapability("can_assign_phone_number"))
                || !"touchscreen".equals(device.getCapability("pointing_method"))
                || resolutionWidth < 320) {
            return false;
        }

        String deviceOs = device.getCapability("device_os");
        String deviceOsVersion = device.getCapability("device_os_version");
        float version = parseMajorMinorVersion(deviceOsVersion);

        return isSmartphoneOs(deviceOs, version);
    }

    /**
     * 从设备中解析分辨率宽度。
     *
     * @return 分辨率宽度，解析失败返回 -1
     */
    private static int parseResolutionWidth(Device device) {
        try {
            return Integer.parseInt(device.getCapability("resolution_width"));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * 从版本字符串中解析主次版本号（如 "4.3" → 4.3f, "10" → 10.0f）。
     *
     * @return 解析后的浮点版本号，解析失败返回 -1
     */
    private static float parseMajorMinorVersion(String versionStr) {
        Matcher matcher = MAJOR_MINOR_VERSION_PATTERN.matcher(versionStr);
        if (!matcher.matches()) {
            return -1f;
        }
        try {
            return Float.parseFloat(matcher.group(1));
        } catch (NumberFormatException e) {
            return -1f;
        }
    }

    /**
     * 根据操作系统名称和版本号判断是否为智能手机。
     */
    private static boolean isSmartphoneOs(String deviceOs, float version) {
        if ("iOS".equals(deviceOs)) {
            return version >= 3.0f;
        }
        if ("Android".equals(deviceOs)) {
            return version >= 2.2f;
        }
        if ("Windows Phone OS".equals(deviceOs)) {
            return true;
        }
        if ("RIM OS".equals(deviceOs)) {
            return version >= 7.0f;
        }
        if ("webOS".equals(deviceOs) || "MeeGo".equals(deviceOs)) {
            return true;
        }
        if ("Bada OS".equals(deviceOs)) {
            return version >= 2.0f;
        }
        return false;
    }
}
