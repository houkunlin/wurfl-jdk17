package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.regex.Pattern;

/**
 * 虚拟能力 User-Agent 工具类，负责从 User-Agent 中解析设备属性和浏览器信息。
 * <p>单例类，维护了大量正则表达式模式，用于识别各种操作系统（Android、iOS、Windows Phone、
 * BlackBerry、Symbian、Linux、macOS 等）和浏览器（Chrome、Firefox、Safari、Opera、IE、Edge、
 * UC Browser、Samsung Browser 等）。输出的 {@link VirtualCapabilityDevice} 包含了
 * 归一化后的操作系统名称/版本和浏览器名称/版本信息。</p>
 */

public final class VirtualCapabilityUserAgentTool {
    private static final Pattern DESKTOP_APP_MAC_PATTERN = Pattern.compile("^Mozilla/[0-9]\\.0 \\(Macintosh;(?: U;)?([a-zA-Z_ \\.0-9]+)(?:;)?.+? DesktopApp ([A-Za-z0-9]+)/([\\d\\.]+)\\.?");
    private static final Pattern DESKTOP_APP_WINDOWS_PATTERN = Pattern.compile("^Mozilla/[0-9]\\.0 \\((?:Windows;|X11;)?(?: U; )?([a-zA-Z_ \\.0-9]+)(?:;)?.+? DesktopApp ([A-Za-z0-9]+)/([\\d\\.]+)\\.?");
    private static final Pattern ANDROID_VERSION_PATTERN = Pattern.compile("Android(?: |/)([\\d\\.]+).+");
    private static final Pattern ADR_ANDROID_VERSION_PATTERN = Pattern.compile(" Adr(?: |/)([\\d\\.]+).+");
    private static final Pattern ANDROID_APP_VERSION_PATTERN = Pattern.compile("Android ([\\d\\.]+)");
    private static final Pattern FACEBOOK_ANDROID_PATTERN = Pattern.compile("^Mozilla/[45]\\.0.+?Android.+?AppleWebKit.+FB(?:AN/|_IAB/|4A)");
    private static final Pattern OPERA_OPR_VERSION_PATTERN = Pattern.compile("OPR/([\\d\\.]+)");
    private static final Pattern BROWSER_360_OS_PATTERN = Pattern.compile("^Mozilla\\/[0-9]\\.0 .+?((?:Windows|Linux|PPC|Intel) [a-zA-Z0-9 _\\.\\-]+).+(?:360Browser|360SE)");
    private static final Pattern CHROMIUM_VERSION_PATTERN = Pattern.compile("Version\\/.+?Chrome\\/([\\d\\.]+)");
    private static final Pattern ANDROID_WEBKIT_VERSION_MARKER_PATTERN = Pattern.compile("Version\\/\\d");
    private static final Pattern CHROME_VERSION_PATTERN = Pattern.compile("Chrome\\/([\\d\\.]+)");
    private static final Pattern FIREFOX_VERSION_PATTERN = Pattern.compile("(?:Firefox|Fennec)\\/([\\d\\.]+)");
    private static final Pattern OPERA_MOBILE_VERSION_PATTERN = Pattern.compile("Opera Mobi\\/.*Version\\/([\\d\\.]+)");
    private static final Pattern OPERA_MINI_VERSION_PATTERN = Pattern.compile("Opera Mini\\/([\\d\\.]+)");
    private static final Pattern OPERA_TABLET_VERSION_PATTERN = Pattern.compile("Opera Tablet\\/.*Version\\/([\\d\\.]+)");
    private static final Pattern UC_BROWSER_VERSION_PATTERN = Pattern.compile("UCBrowser\\/([\\d\\.]+)");
    private static final Pattern UC_BROWSER_JUC_VERSION_PATTERN = Pattern.compile("^JUC.*UCWEB([0-9])");
    private static final Pattern AMAZON_SILK_VERSION_PATTERN = Pattern.compile(" Silk/([\\d\\.]+)");
    private static final Pattern BAIDU_BROWSER_VERSION_PATTERN = Pattern.compile("bdbrowser(?:_i18n)?\\/(\\d+)");
    private static final Pattern SAMSUNG_BROWSER_CHROME_VERSION_PATTERN = Pattern.compile("SamsungBrowser/([\\d\\.]+) Chrome/[\\d\\.]+");
    private static final Pattern SAMSUNG_BROWSER_VERSION_PATTERN = Pattern.compile("SamsungBrowser/([\\d\\.]+)");
    private static final Pattern BAIDU_BROWSER_OS_VERSION_PATTERN = Pattern.compile("^Mozilla\\/[0-9]\\.0 .+?((?:Windows|Linux|PPC|Intel) [a-zA-Z0-9 _\\.\\-]+).+bdbrowser(?:_i18n)?\\/([\\d\\.]+)");
    private static final Pattern IOS_CPU_OS_PATTERN = Pattern.compile("Mozilla\\/[45]\\.0 \\((iPhone|iPod|iPod touch|iPad);(?: U;)? CPU(?: iPhone|) OS ([\\d_]+) like Mac OS X");
    private static final Pattern IOS_SCALE_PATTERN = Pattern.compile("^[^/]+?/[\\d\\.]+? \\(i[A-Za-z]+; iOS ([\\d\\.]+); Scale/[\\d\\.]+\\)");
    private static final Pattern IOS_SERVER_BAG_PATTERN = Pattern.compile("^server-bag \\[iPhone OS,([\\d\\.]+),");
    private static final Pattern IOS_DEVICE_PREFIX_VERSION_PATTERN = Pattern.compile("^i(?:Phone|Pad|Pod|Pod touch)\\d+?,\\d+?/([\\d\\.]+)");
    private static final Pattern IOS_DEVICE_IOS_VERSION_PATTERN = Pattern.compile("i(?:Phone|Pad|Pod)\\d+?,\\d+? iOS/([\\d_]+)");
    private static final Pattern IOS_CHROME_CRIOS_VERSION_PATTERN = Pattern.compile("^Mozilla\\/[45]\\.0.+?like Mac OS X.+?CriOS\\/([\\d\\.]+).+?Mobile\\/[0-9A-Za-z]+ Safari\\/[0-9A-Za-z]+\\.");
    private static final Pattern IOS_FIREFOX_FXIOS_VERSION_PATTERN = Pattern.compile("^Mozilla\\/[45]\\.0.+?like Mac OS X.+?FxiOS\\/([\\d\\.]+).+?Mobile\\/[0-9A-Za-z]+ Safari\\/[0-9A-Za-z]+\\.");
    private static final Pattern IOS_UC_BROWSER_VERSION_PATTERN = Pattern.compile("^Mozilla\\/[45]\\.0.+?OS \\d_\\d.+?like Mac OS X.+?AppleWebKit.+?.+UCBrowser\\/([\\d\\.]+)");
    private static final Pattern IOS_UCWEB_OS_VERSION_PATTERN = Pattern.compile("UCWEB/[\\d\\.]+ \\(iOS;.+?OS ([\\d_]+);.+UCBrowser/");
    private static final Pattern IOS_UCWEB_UCBROWSER_VERSION_PATTERN = Pattern.compile("UCWEB/\\d\\.\\d \\(iOS;.+?OS [\\d_]+;.+UCBrowser/([\\d\\.]+)");
    private static final Pattern IOS_FACEBOOK_FBAN_PATTERN = Pattern.compile("^Mozilla\\/[45]\\.0.+?like Mac OS X.+?AppleWebKit.+?Mobile\\/[0-9A-Za-z]+.*FBAN");
    private static final Pattern IOS_SAFARI_VERSION_PATTERN = Pattern.compile("^Mozilla.+like Mac OS X.+Version/([\\d\\.]+)");
    private static final Pattern WINDOWS_PHONE_VERSION_PATTERN = Pattern.compile("Windows Phone(?: OS)? ([\\d\\.]+)");
    private static final Pattern WINDOWS_UCWEB_WDS_VERSION_PATTERN = Pattern.compile("UCWEB/\\d\\.\\d \\(Windows;.+?; wds ?([\\d\\.]+?);.+UCBrowser");
    private static final Pattern WINDOWS_PHONE_VERSION_SLASH_PATTERN = Pattern.compile("Windows Phone(?: OS)?/([\\d\\.]+)");
    private static final Pattern NOKIA_S40_OVI_BROWSER_VERSION_PATTERN = Pattern.compile("\\bS40OviBrowser\\/([\\d\\.]+)");
    private static final Pattern SYMBIAN_VERSION_PATTERN = Pattern.compile("(?:SymbianOS|Series60|S60)/([\\d\\.]+)");
    private static final Pattern SYMBIAN_UCWEB_S60_MAJOR_VERSION_PATTERN = Pattern.compile("UCWEB/\\d\\.\\d \\(Symbian;.+?S60 V(\\d+)");
    private static final Pattern SYMBIAN3_PATTERN = Pattern.compile("^Mozilla\\/[45]\\.0 \\(Symbian\\/3");
    private static final Pattern NOKIA_BROWSER_VERSION_PATTERN = Pattern.compile("NokiaBrowser\\/([\\d\\.]+)");
    private static final Pattern OPERA_MOBI_VERSION_PATTERN = Pattern.compile("Opera Mobi.+Version\\/([\\d\\.]+)");
    private static final Pattern SYMBIAN_UCWEB_UCBROWSER_VERSION_PATTERN = Pattern.compile("UCWEB/[\\d\\.]+ \\(Symbian;.+?UCBrowser/([\\d\\.]+)");
    private static final Pattern BLACKBERRY_PATTERN = Pattern.compile("(?:BlackBerry)|(?:^Mozilla\\/5.0 \\(BB10; )");
    private static final Pattern BLACKBERRY_UC_BROWSER_PATTERN = Pattern.compile("^BlackBerry[0-9A-Za-z]+?\\/([\\d\\.]+).+?UC Browser\\/?([\\d\\.]+)");
    private static final Pattern UCWEB_UCBROWSER_COMBINED_VERSION_PATTERN = Pattern.compile("^UCWEB\\/[0-9]\\.0.+?; [a-zA-Z][a-zA-Z]?\\-[a-zA-Z]?[a-zA-Z]; [0-9]+?\\/([\\d\\.]+).+?UCBrowser\\/?([\\d\\.]+)");
    private static final Pattern BLACKBERRY_VERSION_PATTERN = Pattern.compile("^BlackBerry[0-9A-Za-z]+?\\/([\\d\\.]+)");
    private static final Pattern BLACKBERRY_BROWSER_VERSION_PATTERN = Pattern.compile("^Mozilla\\/[45]\\.0 \\(BlackBerry;(?: U;)? BlackBerry.+?Version\\/([\\d\\.]+)");
    private static final Pattern BB10_BROWSER_VERSION_PATTERN = Pattern.compile("^Mozilla/[45]\\.0 \\(BB10; .+?Version/([\\d\\.]+)");
    private static final Pattern RIM_TABLET_OS_VERSION_PATTERN = Pattern.compile("RIM Tablet OS ([\\d\\.]+).+?Version\\/([\\d\\.]+)");
    private static final Pattern NETFRONT_VERSION_PATTERN = Pattern.compile("NetFront\\/([\\d\\.]+)");
    private static final Pattern OBIGO_Q_VERSION_PATTERN = Pattern.compile("Obig[a-zA-Z]+?\\/(Q[0-9\\.ABC]+)");
    private static final Pattern BADA_DOLFIN_PATTERN = Pattern.compile("SAMSUNG.+?\\bBada\\/([\\d\\.]+);?.+Dolfin\\/([\\d\\.]+)");
    private static final Pattern OPENWAVE_BROWSER_VERSION_PATTERN = Pattern.compile("UP\\.(?:Browser|Link)\\/([\\d\\.]+)");
    private static final Pattern WEBOS_VERSION_PATTERN = Pattern.compile("^Mozilla\\/[45]\\.0 \\((?:Linux; )?webOS\\/([\\d\\.]+)");
    private static final Pattern OPERA_MINI_PATTERN = Pattern.compile("Opera Mini\\/([\\d\\.]+)");
    private static final Pattern OPERA_LINK_SYNC_VERSION_PATTERN = Pattern.compile("Browser\\/Opera Sync\\/SyncClient.+?Version\\/([\\d\\.]+)");
    private static final Pattern IOS_OPERA_OPIOS_VERSION_PATTERN = Pattern.compile("^Mozilla\\/[45]\\.0.+?like Mac OS X.+?OPiOS\\/([\\d\\.]+).+?Mobile\\/[0-9A-Za-z]+ Safari\\/[0-9A-Za-z]+\\.");
    private static final Pattern MAEMO_FIREFOX_VERSION_PATTERN = Pattern.compile("Maemo.+?Firefox\\/([0-9a\\.]+) ");
    private static final Pattern JAVA_APPLET_PATTERN = Pattern.compile("(?:MIDP.+?CLDC)|(?:UNTRUSTED)|(?:MIDP-2.0)");
    private static final Pattern MSIE_WINDOWS_VERSION_PATTERN = Pattern.compile("^Mozilla\\/[0-9]\\.0 \\(compatible; MSIE ([\\d\\.a-z]+); ((?:Windows NT [0-9]+\\.[0-9])|(?:Windows [0-9]\\.[0-9])|(?:Windows [0-9]+)|(?:Mac_PowerPC))");
    private static final Pattern EDGE_WINDOWS_VERSION_PATTERN = Pattern.compile("^Mozilla/[45]\\.0 \\((Windows NT [\\d\\.]+).+? Edge/([\\d\\.]+)");
    private static final Pattern TRIDENT_WINDOWS_RV_VERSION_PATTERN = Pattern.compile("^Mozilla/[45]\\.0 \\((Windows NT [\\d\\.]+);.+Trident.+; rv:([\\d\\.]+)");
    private static final Pattern YANDEX_BROWSER_VERSION_PATTERN = Pattern.compile("^Mozilla\\/[45]\\.[0-9] \\((?:Macintosh; )?([a-zA-Z0-9\\._ ]+).+\\) AppleWebKit.+YaBrowser\\/([\\d\\.]+)");
    private static final Pattern CHROME_MAC_VERSION_PATTERN = Pattern.compile("^Mozilla\\/[0-9]\\.0 \\(Macintosh;(?: U;)?([a-zA-Z_ \\.0-9]+)(?:;)?.+? Chrome\\/([\\d\\.]+)\\.?");
    private static final Pattern CHROME_WINDOWS_VERSION_PATTERN = Pattern.compile("^Mozilla\\/[0-9]\\.0 \\((?:Windows;|X11;)?(?: U; )?([a-zA-Z_ \\.0-9]+)(?:;)?.+? Chrome\\/([\\d\\.]+)\\.?");
    private static final Pattern SAFARI_DESKTOP_VERSION_PATTERN = Pattern.compile("Mozilla\\/[0-9]\\.0 \\((?:Windows|Macintosh); (?:U; |WOW64; )?([a-zA-Z_ \\.0-9]+)(?:;)?[^)]+ Version\\/([\\d\\.]+)\\.?");
    private static final Pattern FIREFOX_WINDOWS_VERSION_PATTERN = Pattern.compile("^Mozilla\\/[0-9]\\.0 .+(Windows [0-9A-Za-z \\.]+;).+?rv:.+?Firefox\\/([\\d\\.]+)");
    private static final Pattern FIREFOX_LINUX_MAC_VERSION_PATTERN = Pattern.compile("^Mozilla\\/[0-9]\\.0 \\((?:X11|Macintosh); (?:U; |Ubuntu; |)((?:Intel|PPC|Linux) [a-zA-Z0-9\\- \\._\\(\\)]+);.+?rv:.+?Firefox\\/([\\d\\.]+)");
    private static final Pattern OPERA_OPR_DESKTOP_VERSION_PATTERN = Pattern.compile("^Mozilla\\/[0-9]\\.0 .+?((?:Windows|Linux|PPC|Intel) [a-zA-Z0-9 _\\.\\-]+).+Chrome\\/.+OPR\\/([\\d\\.]+)");
    private static final Pattern OPERA_CLASSIC_VERSION_PATTERN = Pattern.compile("^Opera\\/([\\d\\.]+) .+?((?:Windows|Linux|PPC|Intel) [a-zA-Z0-9 _\\.\\-]+) ?;");
    private static final Pattern OPERA_VERSION_PATTERN = Pattern.compile("^Opera\\/.+? Version\\/([\\d\\.]+)");
    private static final Pattern IE_MOBILE_VERSION_PATTERN = Pattern.compile("IEMobile\\/([\\d\\.]+)");
    private static final Pattern EDGE_VERSION_PATTERN = Pattern.compile("Edge\\/([\\d\\.]+)");
    private static final Pattern UCWEB_JAVA_UCBROWSER_VERSION_PATTERN = Pattern.compile("UCWEB/\\d\\.\\d \\(Java;.+?UCBrowser/([\\d\\.]+)");
    private static final Pattern PALEMOON_WINDOWS_VERSION_PATTERN = Pattern.compile("^Mozilla\\/[0-9]\\.0 .+(Windows [0-9A-Za-z \\.]+;).+?rv:.+?PaleMoon\\/([\\d\\.]+)");
    private static final Pattern PALEMOON_LINUX_MAC_VERSION_PATTERN = Pattern.compile("^Mozilla\\/[0-9]\\.0 \\((?:X11|Macintosh); (?:U; |Ubuntu; |)((?:Intel|PPC|Linux) [a-zA-Z0-9\\- \\._\\(\\)]+);.+?rv:.+?PaleMoon\\/([\\d\\.]+)");
    private static final Pattern MOBILE_APP_NAME_VERSION_PATTERN = Pattern.compile("MobileApp ([A-Za-z0-9 ]+)/([\\d\\.]+)");
    private static final Pattern ANDROID_MOBILE_APP_NAME_VERSION_PATTERN = Pattern.compile("^Mozilla/[45]\\.0.+?Android.+?AppleWebKit.+MobileApp ([A-Za-z0-9 ]+)/([\\d\\.]+)");
    private static final Pattern IOS_MOBILE_APP_NAME_VERSION_PATTERN = Pattern.compile("^Mozilla/[45]\\.0.+?like Mac OS X.+?AppleWebKit.+?Mobile/[0-9A-Za-z]+.*MobileApp ([A-Za-z0-9 ]+)/([\\d\\.]+)");
    private static final Pattern AMAZON_ANDROID_VERSION_PATTERN = Pattern.compile(":::Android_(\\d\\.\\d)");
    private static final Pattern AMAZON_SHOPPING_ANDROID_VERSION_PATTERN = Pattern.compile("^mShop:::Amazon_Android_([\\d\\.]+):::");
    private static final Pattern FIREFOX_SIMPLE_VERSION_PATTERN = Pattern.compile("Firefox/([\\d\\.]+)");
    private static final Pattern IOS_GOOGLE_SEARCH_APP_GSA_VERSION_PATTERN = Pattern.compile("^Mozilla/[45]\\.0.+?like Mac OS X.+?AppleWebKit.+?GSA/([\\d\\.]+) Mobile/");
    private static final Pattern NINTENDO_NETFRONT_NX_VERSION_PATTERN = Pattern.compile("Version/([\\d\\.A-Z]+)");
    private static final Pattern NINTENDO_NETFRONT_NX_VERSION_PATTERN_2 = Pattern.compile(" NX/([\\d\\.]+)");
    private static final Pattern NINTENDO_BROWSER_VERSION_PATTERN = Pattern.compile("NintendoBrowser/([\\d\\.A-Z]+)");
    private static final Pattern EPIPHANY_VERSION_PATTERN = Pattern.compile("Epiphany/([\\d\\.]+)");
    private static final Pattern TIZEN_VERSION_PATTERN = Pattern.compile("Tizen ([\\d\\.]+)");
    private static final Pattern EDGE_ANDROID_VERSION_PATTERN = Pattern.compile("EdgA/([\\d\\.]+)");
    private static final Pattern EDGE_IOS_VERSION_PATTERN = Pattern.compile("EdgiOS/([\\d\\.]+)");
    private static final Pattern QQ_BROWSER_PATTERN = Pattern.compile("MQQBrowser/([\\d\\.]+)");
    private static final Pattern WECHAT_PATTERN = Pattern.compile("MicroMessenger/([\\d\\.]+)");
    private static final Pattern SOGOU_MOBILE_PATTERN = Pattern.compile("SogouMobileBrowser/([\\d\\.]+)");
    private static final Pattern BRAVE_PATTERN = Pattern.compile("Brave Chrome/([\\d\\.]+)");
    private static final Pattern ALIPAY_PATTERN = Pattern.compile("AliApp\\(AP/([\\d\\.]+)");
    private static final Pattern FIREFOX_FOCUS_VERSION_PATTERN = Pattern.compile("Focus/([\\d\\.]+)");
    private static final Pattern IOS_FIREFOX_FOCUS_VERSION_PATTERN = Pattern.compile("^Mozilla/[45]\\.0.+?like Mac OS X.+?Focus/([\\d\\.]+) Mobile\\/[0-9A-Za-z]+");
    private static final Pattern WINDOWS_OS_NAME_PATTERN = Pattern.compile("(Windows [0-9A-Za-z \\.]+)");
    private static final Pattern MAC_OS_NAME_PATTERN = Pattern.compile("Macintosh;(?: U;)?([a-zA-Z_ \\.0-9]+)(?:;)?");
    private static final VirtualCapabilityUserAgentTool INSTANCE = new VirtualCapabilityUserAgentTool();

    private VirtualCapabilityUserAgentTool() {
    }

    /**
     * 获取单例实例。
     *
     * @return 单例实例
     */

    public static VirtualCapabilityUserAgentTool getInstance() {
        return INSTANCE;
    }

    /**
     * 为指定的 User-Agent 请求分配设备和浏览器的属性。
     * <p>通过解析 User-Agent 字符串，识别操作系统和浏览器的名称及版本，
     * 并构建 {@link VirtualCapabilityDevice} 返回。</p>
     *
     * @param request        WURFL 请求
     * @param internalDevice 内部设备实例（用于获取设备能力值）
     * @return 包含识别结果的虚拟能力设备
     */

    public VirtualCapabilityDevice assignProperties(WURFLRequest request, InternalDevice internalDevice) {
        VirtualCapabilityDevice vcd = new VirtualCapabilityDevice(request);
        NameVersionPair osPair = vcd.getOsPair();
        NameVersionPair browserPair = vcd.getBrowserPair();
        String deviceUA = vcd.getDeviceUserAgent();
        String browserUA = vcd.getBrowserUserAgent();

        // Windows CE / Windows Mobile → IE Mobile
        if (osPair.containsAndSetName(deviceUA, "Windows CE", "Windows Mobile")) {
            browserPair.setName("IE Mobile");
        }
        // 非 Windows Phone，或 Windows Phone 版本未匹配 → 进入平台识别分支
        else if (!StringMatchUtils.containsAnyOf(deviceUA, "Windows Phone", "; wds")
                || (!osPair.matchAndSetGroup(WINDOWS_PHONE_VERSION_PATTERN, deviceUA, "Windows Phone", 1)
                && !osPair.matchAndSetGroup(WINDOWS_UCWEB_WDS_VERSION_PATTERN, deviceUA, "Windows Phone", 1)
                && !osPair.matchAndSetGroup(WINDOWS_PHONE_VERSION_SLASH_PATTERN, deviceUA, "Windows Phone", 1))) {
            detectOsAndBrowser(vcd, internalDevice);
        }
        // Windows Phone 版本已匹配 → 尝试 Mobile App / 兜底浏览器
        else if (browserPair.matchAndSetNameAndGroup(MOBILE_APP_NAME_VERSION_PATTERN, browserUA, 2)) {
            browserPair.setName(browserPair.getName() + " Mobile Application");
        } else {
            browserPair.matchAndSetGroup(UC_BROWSER_VERSION_PATTERN, browserUA, "UC Browser", 1);
            browserPair.matchAndSetGroup(IE_MOBILE_VERSION_PATTERN, browserUA, "IE Mobile", 1);
            browserPair.matchAndSetGroup(EDGE_VERSION_PATTERN, browserUA, "Edge Mobile", 1);
        }

        vcd.normalizeOS();
        vcd.normalizeBrowser();
        return vcd;
    }

    // ========================================================================
    // 平台识别分发 (原 label422 块)
    // ========================================================================

    /**
     * 检测非 Windows Phone 设备的操作系统和浏览器。
     * <p>按优先级依次匹配 Nintendo / Android / iOS / Tizen / Symbian / BlackBerry 等平台，
     * 通过 {@code return} 退出（替代原 label break）。</p>
     */
    private void detectOsAndBrowser(VirtualCapabilityDevice vcd, InternalDevice device) {
        NameVersionPair osPair = vcd.getOsPair();
        NameVersionPair browserPair = vcd.getBrowserPair();
        String deviceUA = vcd.getDeviceUserAgent();
        String browserUA = vcd.getBrowserUserAgent();

        // --- Nintendo ---
        if (deviceUA.contains("Nintendo")) {
            osPair.setName("Nintendo");
            if (browserPair.matchAndSetGroup(NINTENDO_NETFRONT_NX_VERSION_PATTERN, browserUA, "Netfront NX", 1)
                    || browserPair.matchAndSetGroup(NINTENDO_NETFRONT_NX_VERSION_PATTERN_2, browserUA, "Netfront NX", 1)
                    || browserPair.matchAndSetGroup(NINTENDO_BROWSER_VERSION_PATTERN, browserUA, "Nintendo Browser", 1)) {
                return;
            }
            browserPair.setName("Nintendo Browser");
            // 浏览器未匹配时继续向下尝试其他平台
        }

        // --- Android ---
        if (StringMatchUtils.containsAnyOf(deviceUA, "Android", "android", " Adr ", "HarmonyOS")) {
            handleAndroidDevice(vcd, device);
            return;
        }

        // --- Silk → Android (无版本号) ---
        if (StringMatchUtils.indexOf(deviceUA, "Silk") >= 0
                && browserPair.matchAndSetGroup(AMAZON_SILK_VERSION_PATTERN, browserUA, "Amazon Silk Browser", 1)) {
            osPair.setName("Android");
            osPair.setVersion("");
            return;
        }

        // --- iOS ---
        if (StringMatchUtils.containsAnyOf(deviceUA, "iPhone", "iPad", "iPod", "iPod touch", "(iOS;")) {
            handleIOSDevice(vcd);
            return;
        }

        // --- Tizen ---
        if (deviceUA.contains("Tizen")) {
            osPair.matchAndSetGroup(TIZEN_VERSION_PATTERN, deviceUA, "Tizen", 1);
            if (!browserPair.matchAndSetGroup(SAMSUNG_BROWSER_VERSION_PATTERN, browserUA, "Samsung Browser", 1)) {
                browserPair.setName("Tizen Browser");
                browserPair.setVersion(osPair.getVersion());
            }
            return;
        }

        // --- OviBrowser → Nokia Series 40 ---
        if (StringMatchUtils.indexOf(deviceUA, "OviBrowser") >= 0
                && browserPair.matchAndSetGroup(NOKIA_S40_OVI_BROWSER_VERSION_PATTERN, browserUA, "S40 Ovi Browser", 1)) {
            osPair.setName("Nokia Series 40");
            return;
        }

        // --- Symbian S60 ---
        if (osPair.matchAndSetGroup(SYMBIAN_VERSION_PATTERN, deviceUA, "Symbian S60", 1)
                || osPair.matchAndSetGroup(SYMBIAN_UCWEB_S60_MAJOR_VERSION_PATTERN, deviceUA, "Symbian S60", 1)) {
            handleSymbianDevice(vcd);
            return;
        }

        // --- BlackBerry ---
        if (StringMatchUtils.indexOf(deviceUA, "BlackBerry") >= 0
                || StringMatchUtils.indexOf(deviceUA, "(BB10; ") >= 0) {
            handleBlackBerryDevice(vcd);
            return;
        }

        // --- 其他设备 / 桌面浏览器检测 ---
        handleDesktopOrOtherDevice(vcd, device);
    }

    // ========================================================================
    // Android (原 label403 块)
    // ========================================================================

    /**
     * 处理 Android 设备的浏览器匹配。
     */
    private void handleAndroidDevice(VirtualCapabilityDevice vcd, InternalDevice device) {
        NameVersionPair osPair = vcd.getOsPair();
        NameVersionPair browserPair = vcd.getBrowserPair();
        String deviceUA = vcd.getDeviceUserAgent();
        String browserUA = vcd.getBrowserUserAgent();

        osPair.setName("Android");
        osPair.matchAndSetGroup(ANDROID_VERSION_PATTERN, deviceUA, "Android", 1);
        osPair.matchAndSetGroup(AMAZON_ANDROID_VERSION_PATTERN, deviceUA, "Android", 1);
        osPair.matchAndSetGroup(ADR_ANDROID_VERSION_PATTERN, deviceUA, "Android", 1);

        // Fire OS 覆盖
        String deviceOs = device.getCapability("device_os");
        if ("Fire OS".equals(deviceOs)) {
            osPair.setName(deviceOs);
            osPair.setVersion(device.getCapability("device_os_version"));
        }

        // Dalvik → Android App
        if (StringMatchUtils.indexOf(browserUA, "Dalvik") >= 0) {
            browserPair.setName("Android App");
            browserPair.matchAndSetGroup(ANDROID_APP_VERSION_PATTERN, browserUA, null, 1);
            return;
        }

        // Facebook / Amazon Shopping
        if (browserPair.matchAndSet(FACEBOOK_ANDROID_PATTERN, browserUA, "Facebook on Android", osPair.getVersion())
                || browserPair.matchAndSetGroup(AMAZON_SHOPPING_ANDROID_VERSION_PATTERN, browserUA, "Amazon Shopping App", 1)) {
            return;
        }

        // Mobile App (Android)
        if (browserPair.matchAndSetNameAndGroup(ANDROID_MOBILE_APP_NAME_VERSION_PATTERN, browserUA, 2)) {
            browserPair.setName(browserPair.getName() + " Mobile Application");
            return;
        }

        // Opera (OPR/)
        if (browserPair.matchAndSetGroup(OPERA_OPR_VERSION_PATTERN, browserUA, "Opera", 1)) {
            return;
        }

        // 360 Browser
        if (StringMatchUtils.containsAnyOf(browserUA, "Aphone Browser", "360browser")) {
            browserPair.setName("360 Browser");
            return;
        }

        // 在具体浏览器覆盖之前，先捕获上游（Chrome/Chromium）版本信息
        // 后续特定浏览器（QQ/WeChat/Edge 等）会在其版本号之上保留此上游信息
        captureUpstreamBrowser(vcd, browserUA);

        // 各种桌面/移动浏览器
        if (browserPair.matchAndSetGroup(FIREFOX_VERSION_PATTERN, browserUA, "Firefox Mobile", 1)) return;
        if (browserPair.matchAndSetGroup(FIREFOX_FOCUS_VERSION_PATTERN, browserUA, "Firefox Focus", 1)) return;
        if (browserPair.matchAndSetGroup(OPERA_MOBILE_VERSION_PATTERN, browserUA, "Opera Mobile", 1)) return;
        if (browserPair.matchAndSetGroup(OPERA_MINI_VERSION_PATTERN, browserUA, "Opera Mini", 1)) return;
        if (browserPair.matchAndSetGroup(OPERA_TABLET_VERSION_PATTERN, browserUA, "Opera Tablet", 1)) return;
        if (browserPair.matchAndSetGroup(UC_BROWSER_VERSION_PATTERN, browserUA, "UC Browser", 1)) return;
        if (browserPair.matchAndSetGroup(UC_BROWSER_JUC_VERSION_PATTERN, browserUA, "UC Browser", 1)) return;
        if (browserPair.matchAndSetGroup(AMAZON_SILK_VERSION_PATTERN, browserUA, "Amazon Silk Browser", 1)) return;
        if (browserPair.matchAndSetGroup(BAIDU_BROWSER_VERSION_PATTERN, browserUA, "Baidu Browser", 1)) return;
        if (browserPair.matchAndSetGroup(SAMSUNG_BROWSER_CHROME_VERSION_PATTERN, browserUA, "Samsung Browser", 1))
            return;
        if (browserPair.matchAndSetGroup(EDGE_ANDROID_VERSION_PATTERN, browserUA, "Edge", 1)) return;
        // 中国常用浏览器识别（需在 Chrome 匹配之前，因为其 UA 含 Chrome 标识）
        if (browserPair.matchAndSetGroup(QQ_BROWSER_PATTERN, browserUA, "QQ Browser", 1)) return;
        if (browserPair.matchAndSetGroup(WECHAT_PATTERN, browserUA, "WeChat Built-in", 1)) return;
        if (browserPair.matchAndSetGroup(SOGOU_MOBILE_PATTERN, browserUA, "Sogou Mobile", 1)) return;
        if (browserPair.matchAndSetGroup(BRAVE_PATTERN, browserUA, "Brave", 1)) return;
        if (browserPair.matchAndSetGroup(ALIPAY_PATTERN, browserUA, "Alipay Built-in", 1)) return;
        if (browserPair.matchAndSetGroup(CHROMIUM_VERSION_PATTERN, browserUA, "Chromium", 1)) return;
        if (browserPair.matchAndSetGroup(CHROME_VERSION_PATTERN, browserUA, "Chrome Mobile", 1)) return;
        if (browserPair.matchAndSet(ANDROID_WEBKIT_VERSION_MARKER_PATTERN, browserUA, "Android Webkit", osPair.getVersion()))
            return;

        // 兜底
        browserPair.setName("Android");
        browserPair.setVersion(osPair.getVersion());
    }

    // ========================================================================
    // iOS
    // ========================================================================

    /**
     * 处理 iOS 设备的浏览器匹配。
     */
    private void handleIOSDevice(VirtualCapabilityDevice vcd) {
        NameVersionPair osPair = vcd.getOsPair();
        NameVersionPair browserPair = vcd.getBrowserPair();
        String deviceUA = vcd.getDeviceUserAgent();
        String browserUA = vcd.getBrowserUserAgent();

        osPair.setName("iOS");
        if (osPair.matchAndSetGroup(IOS_CPU_OS_PATTERN, deviceUA, "iOS", 2)
                || osPair.matchAndSetGroup(IOS_SCALE_PATTERN, deviceUA, "iOS", 1)
                || osPair.matchAndSetGroup(IOS_SERVER_BAG_PATTERN, deviceUA, "iOS", 1)
                || osPair.matchAndSetGroup(IOS_DEVICE_PREFIX_VERSION_PATTERN, deviceUA, "iOS", 1)
                || osPair.matchAndSetGroup(IOS_DEVICE_IOS_VERSION_PATTERN, deviceUA, "iOS", 1)) {
            osPair.setVersion(osPair.getVersion().replace("_", "."));
        }
        if (osPair.matchAndSetGroup(IOS_UCWEB_OS_VERSION_PATTERN, deviceUA, "iOS", 1)) {
            osPair.setVersion(osPair.getVersion().replace("_", "."));
        }

        // 在具体浏览器覆盖之前捕获上游信息
        captureUpstreamBrowser(vcd, browserUA);

        // 知名浏览器
        if (browserPair.matchAndSetGroup(IOS_CHROME_CRIOS_VERSION_PATTERN, browserUA, "Chrome Mobile on iOS", 1))
            return;
        if (browserPair.matchAndSetGroup(IOS_FIREFOX_FOCUS_VERSION_PATTERN, browserUA, "Firefox Focus", 1)) return;
        if (browserPair.matchAndSetGroup(IOS_FIREFOX_FXIOS_VERSION_PATTERN, browserUA, "Firefox on iOS", 1)) return;
        if (browserPair.matchAndSetGroup(IOS_OPERA_OPIOS_VERSION_PATTERN, browserUA, "Opera Mini on iOS", 1)) return;
        if (browserPair.matchAndSetGroup(IOS_UC_BROWSER_VERSION_PATTERN, browserUA, "UC Web Browser on iOS", 1)) return;
        if (browserPair.matchAndSetGroup(IOS_UCWEB_UCBROWSER_VERSION_PATTERN, browserUA, "UC Web Browser on iOS", 1))
            return;
        if (browserPair.matchAndSet(IOS_FACEBOOK_FBAN_PATTERN, browserUA, "Facebook on iOS", osPair.getVersion()))
            return;

        // Mobile App (iOS)
        if (browserPair.matchAndSetNameAndGroup(IOS_MOBILE_APP_NAME_VERSION_PATTERN, browserUA, 2)) {
            browserPair.setName(browserPair.getName() + " Mobile Application");
            return;
        }

        // Google Search / Edge / Mobile Safari
        if (browserPair.matchAndSetGroup(IOS_GOOGLE_SEARCH_APP_GSA_VERSION_PATTERN, browserUA, "Google Search Application", 1))
            return;
        if (browserPair.matchAndSetGroup(EDGE_IOS_VERSION_PATTERN, browserUA, "Edge", 1)) return;
        if (browserPair.matchAndSetGroup(QQ_BROWSER_PATTERN, browserUA, "QQ Browser", 1)) return;
        if (browserPair.matchAndSetGroup(WECHAT_PATTERN, browserUA, "WeChat Built-in", 1)) return;
        if (browserPair.matchAndSetGroup(SOGOU_MOBILE_PATTERN, browserUA, "Sogou Mobile", 1)) return;
        if (browserPair.matchAndSetGroup(ALIPAY_PATTERN, browserUA, "Alipay Built-in", 1)) return;
        if (browserPair.matchAndSetGroup(IOS_SAFARI_VERSION_PATTERN, browserUA, "Mobile Safari", 1)) return;

        // 兜底
        browserPair.setName("Mobile Safari");
        browserPair.setVersion(osPair.getVersion());
    }

    // ========================================================================
    // Symbian (原 Symbian3 分支)
    // ========================================================================

    /**
     * 处理 Symbian S60 设备的浏览器匹配。
     */
    private void handleSymbianDevice(VirtualCapabilityDevice vcd) {
        NameVersionPair osPair = vcd.getOsPair();
        NameVersionPair browserPair = vcd.getBrowserPair();
        String deviceUA = vcd.getDeviceUserAgent();
        String browserUA = vcd.getBrowserUserAgent();

        osPair.matchAndSet(SYMBIAN3_PATTERN, deviceUA, "Symbian", "^3");
        if (!browserPair.matchAndSetGroup(NOKIA_BROWSER_VERSION_PATTERN, browserUA, "Symbian S60 Browser", 1)
                && !browserPair.matchAndSetGroup(OPERA_MOBI_VERSION_PATTERN, browserUA, "Opera Mobi", 1)
                && !browserPair.matchAndSetGroup(SYMBIAN_UCWEB_UCBROWSER_VERSION_PATTERN, browserUA, "UC Web Browser on Symbian", 1)) {
            browserPair.setName("Symbian S60 Browser");
        }
    }

    // ========================================================================
    // BlackBerry
    // ========================================================================

    /**
     * 处理 BlackBerry 设备的浏览器匹配。
     */
    private void handleBlackBerryDevice(VirtualCapabilityDevice vcd) {
        NameVersionPair osPair = vcd.getOsPair();
        NameVersionPair browserPair = vcd.getBrowserPair();
        String deviceUA = vcd.getDeviceUserAgent();
        String browserUA = vcd.getBrowserUserAgent();

        osPair.matchAndSet(BLACKBERRY_PATTERN, deviceUA, "BlackBerry", null);
        osPair.matchAndSetGroup(BLACKBERRY_VERSION_PATTERN, deviceUA, null, 1);

        if (osPair.matchAndSetGroup(BLACKBERRY_UC_BROWSER_PATTERN, deviceUA, null, 1)) {
            browserPair.setName("UC Web");
            browserPair.setVersion(osPair.getGroup(2));
        } else if (osPair.matchAndSetGroup(UCWEB_UCBROWSER_COMBINED_VERSION_PATTERN, deviceUA, null, 1)) {
            browserPair.setName("UC Web");
            browserPair.setVersion(osPair.getGroup(2));
        } else if (!browserPair.matchAndSetGroup(OPERA_MINI_PATTERN, browserUA, "Opera Mini", 1)) {
            if (osPair.matchAndSetGroup(BLACKBERRY_BROWSER_VERSION_PATTERN, deviceUA, null, 1)) {
                browserPair.setName("BlackBerry Browser");
                browserPair.setVersion(osPair.getVersion());
            } else if (osPair.matchAndSetGroup(BB10_BROWSER_VERSION_PATTERN, deviceUA, null, 1)) {
                browserPair.setName("BlackBerry Webkit Browser");
                browserPair.setVersion(osPair.getVersion());
            } else {
                browserPair.setName("BlackBerry Browser");
                browserPair.setVersion(osPair.getVersion());
            }
        }
    }

    // ========================================================================
    // 其他设备 / 桌面浏览器检测
    // ========================================================================

    /**
     * 处理非主流平台及桌面浏览器的操作系统和浏览器匹配。
     * 包含 RIM Tablet OS、NetFront、Obigo、Bada、webOS、Opera、Maemo、Java 以及
     * 各种桌面浏览器（Chrome、Firefox、Safari、IE、Edge、PaleMoon 等）。
     */
    private void handleDesktopOrOtherDevice(VirtualCapabilityDevice vcd, InternalDevice device) {
        NameVersionPair osPair = vcd.getOsPair();
        NameVersionPair browserPair = vcd.getBrowserPair();
        String deviceUA = vcd.getDeviceUserAgent();
        String browserUA = vcd.getBrowserUserAgent();
        String cleanedDeviceUA = vcd.getCleanedDeviceUserAgent();

        // --- RIM Tablet OS ---
        if (StringMatchUtils.indexOf(deviceUA, "RIM Tablet OS") >= 0
                && osPair.matchAndSetGroup(RIM_TABLET_OS_VERSION_PATTERN, deviceUA, "RIM Tablet OS", 1)) {
            browserPair.setName("RIM OS Browser");
            browserPair.setVersion(osPair.getGroup(2));
            return;
        }

        // --- NetFront ---
        if (StringMatchUtils.indexOf(deviceUA, "NetFront") >= 0
                && browserPair.matchAndSetGroup(NETFRONT_VERSION_PATTERN, browserUA, "NetFront", 1)) {
            return;
        }

        // --- Obigo (Teleca Obigo) ---
        if (browserPair.containsAndSetName(deviceUA, "Obigo", "Teleca Obigo")
                && browserPair.matchAndSetGroup(OBIGO_Q_VERSION_PATTERN, browserUA, null, 1)) {
            return;
        }

        // --- Bada + Dolfin ---
        if (StringMatchUtils.indexOf(deviceUA, "Dolfin") >= 0
                && osPair.matchAndSetGroup(BADA_DOLFIN_PATTERN, deviceUA, "Bada", 1)) {
            browserPair.setName("Dolfin Browser");
            browserPair.setVersion(osPair.getGroup(2));
            return;
        }

        // --- MAUI Browser ---
        if (browserPair.containsAndSetName(deviceUA, "MAUI", "MAUI Browser")) {
            return;
        }

        // --- Openwave Browser (via Dolfin) ---
        if (StringMatchUtils.indexOf(deviceUA, "Dolfin") >= 0
                && browserPair.matchAndSetGroup(OPENWAVE_BROWSER_VERSION_PATTERN, browserUA, "Openwave Browser", 1)) {
            return;
        }

        // --- webOS ---
        if (osPair.matchAndSetGroup(WEBOS_VERSION_PATTERN, deviceUA, "webOS", 1)) {
            browserPair.setName("webOS Browser");
            browserPair.setVersion(osPair.getVersion());
            return;
        }

        // --- Opera (mobile variants) ---
        if (StringMatchUtils.indexOf(deviceUA, "Opera") >= 0) {
            if (browserPair.containsAndSetName(deviceUA, "Opera Mobi", "Opera Mobile")) {
                browserPair.matchAndSetGroup(OPERA_MOBI_VERSION_PATTERN, deviceUA, null, 1);
                return;
            }
            if (browserPair.matchAndSetGroup(OPERA_MINI_PATTERN, deviceUA, "Opera Mini", 1)
                    || browserPair.matchAndSetGroup(OPERA_LINK_SYNC_VERSION_PATTERN, deviceUA, "Opera Link Sync", 1)) {
                return;
            }
        }

        // --- Maemo ---
        if (StringMatchUtils.indexOf(deviceUA, "Maemo") >= 0) {
            osPair.setName("Maemo");
            if (browserPair.matchAndSetGroup(MAEMO_FIREFOX_VERSION_PATTERN, browserUA, "Firefox", 1)) {
                return;
            }
        }

        // ============================================================
        // Java / UCWeb Java Applet / 桌面浏览器
        // ============================================================

        // 原始条件: (!containsAnyOf("Java", "UCBrowser/") || !ucwebJavaMatch) && !javaAppletMatched
        // 即: 不是 Java/UCBrowser Java Applet 且不是 Java Applet 时进入桌面浏览器分支
        boolean isJavaOrUcwebJava = StringMatchUtils.containsAnyOf(deviceUA, "Java", "UCBrowser/")
                && browserPair.matchAndSetGroup(UCWEB_JAVA_UCBROWSER_VERSION_PATTERN, browserUA, "UCBrowser Java Applet", 1);
        boolean isJavaApplet = !isJavaOrUcwebJava
                && browserPair.matchAndSet(JAVA_APPLET_PATTERN, browserUA, "Java Applet", null);

        if (!isJavaOrUcwebJava && !isJavaApplet) {
            // --- DesktopApp (Mac / Windows) ---
            if (StringMatchUtils.indexOf(deviceUA, "DesktopApp") != -1) {
                if (osPair.matchAndSetNameFromGroup(DESKTOP_APP_MAC_PATTERN, deviceUA, 1)) {
                    browserPair.setName(osPair.getGroup(2) + " Desktop Application");
                    browserPair.setVersion(osPair.getGroup(3));
                    return;
                }
                if (osPair.matchAndSetNameFromGroup(DESKTOP_APP_WINDOWS_PATTERN, deviceUA, 1)) {
                    browserPair.setName(osPair.getGroup(2) + " Desktop Application");
                    browserPair.setVersion(osPair.getGroup(3));
                    return;
                }
            }

            // --- Baidu Browser (with OS) ---
            if (osPair.matchAndSetNameFromGroup(BAIDU_BROWSER_OS_VERSION_PATTERN, deviceUA, 1)) {
                browserPair.setName("Baidu Browser");
                browserPair.setVersion(osPair.getGroup(2));
                return;
            }

            // --- 360 Browser ---
            if (StringMatchUtils.containsAnyOf(deviceUA, "360Browser", "360SE")
                    && osPair.matchAndSetNameFromGroup(BROWSER_360_OS_PATTERN, deviceUA, 1)) {
                browserPair.setName("360 Browser");
                return;
            }

            // --- MSIE (Windows/Mac) ---
            if (StringMatchUtils.indexOf(deviceUA, "MSIE") >= 0
                    && osPair.matchAndSetNameFromGroup(MSIE_WINDOWS_VERSION_PATTERN, deviceUA, 2)) {
                browserPair.setName("IE");
                browserPair.setVersion(osPair.getGroup(1));
                return;
            }

            // --- Trident / Edge ---
            if (StringMatchUtils.containsAnyOf(deviceUA, "Trident", " Edge/")) {
                if (osPair.matchAndSetNameFromGroup(TRIDENT_WINDOWS_RV_VERSION_PATTERN, deviceUA, 1)) {
                    browserPair.setName("IE");
                    browserPair.setVersion(osPair.getGroup(2));
                    return;
                }
                if (osPair.matchAndSetNameFromGroup(EDGE_WINDOWS_VERSION_PATTERN, deviceUA, 1)) {
                    browserPair.setName("Edge");
                    browserPair.setVersion(osPair.getGroup(2));
                    return;
                }
            }

            // --- Yandex Browser ---
            if (StringMatchUtils.indexOf(deviceUA, "YaBrowser") >= 0
                    && osPair.matchAndSetNameFromGroup(YANDEX_BROWSER_VERSION_PATTERN, deviceUA, 1)) {
                browserPair.setName("Yandex browser");
                browserPair.setVersion(osPair.getGroup(2));
                return;
            }

            // --- Opera (OPR desktop) ---
            if (StringMatchUtils.indexOf(deviceUA, "OPR") >= 0
                    && osPair.matchAndSetNameFromGroup(OPERA_OPR_DESKTOP_VERSION_PATTERN, deviceUA, 1)) {
                browserPair.setName("Opera");
                browserPair.setVersion(osPair.getGroup(2));
                return;
            }

            // --- Opera Classic ---
            if (StringMatchUtils.indexOf(deviceUA, "Opera") >= 0
                    && osPair.matchAndSetNameFromGroup(OPERA_CLASSIC_VERSION_PATTERN, deviceUA, 2)) {
                browserPair.setName("Opera");
                browserPair.setVersion(osPair.getGroup(1));
                browserPair.matchAndSetGroup(OPERA_VERSION_PATTERN, browserUA, null, 1);
                return;
            }

            // --- Linux x86_64 + SamsungBrowser → DeX Samsung Browser ---
            if (deviceUA.contains("Linux x86_64") && deviceUA.contains("SamsungBrowser/")) {
                osPair.setName("Linux");
                if (browserPair.matchAndSetGroup(SAMSUNG_BROWSER_VERSION_PATTERN, deviceUA, "DeX Samsung Browser", 1)) {
                    return;
                }
            }

            // --- Chrome (Mac / Windows) ---
            if (StringMatchUtils.indexOf(deviceUA, "Chrome") >= 0) {
                if (osPair.matchAndSetNameFromGroup(CHROME_MAC_VERSION_PATTERN, deviceUA, 1)) {
                    browserPair.setName("Chrome");
                    browserPair.setVersion(osPair.getGroup(2));
                    return;
                }
                if (osPair.matchAndSetNameFromGroup(CHROME_WINDOWS_VERSION_PATTERN, deviceUA, 1)) {
                    browserPair.setName("Chrome");
                    browserPair.setVersion(osPair.getGroup(2));
                    return;
                }
            }

            // --- Epiphany ---
            if (deviceUA.contains("Epiphany/")) {
                osPair.setName("Linux");
                if (browserPair.matchAndSetGroup(EPIPHANY_VERSION_PATTERN, deviceUA, "Epiphany", 1)) {
                    return;
                }
            }

            // --- Safari (Desktop) ---
            if (StringMatchUtils.indexOf(deviceUA, "Safari") >= 0
                    && osPair.matchAndSetNameFromGroup(SAFARI_DESKTOP_VERSION_PATTERN, cleanedDeviceUA, 1)) {
                if (StringMatchUtils.indexOf(deviceUA, "CFNetwork") >= 0) {
                    browserPair.setName("OSX App");
                } else {
                    browserPair.setName("Safari");
                }
                browserPair.setVersion(osPair.getGroup(2));
                return;
            }

            // --- PaleMoon ---
            if (deviceUA.contains("PaleMoon")) {
                if (osPair.matchAndSetNameFromGroup(PALEMOON_WINDOWS_VERSION_PATTERN, deviceUA, 1)) {
                    browserPair.setName("PaleMoon");
                    browserPair.setVersion(osPair.getGroup(2));
                    return;
                }
                if (osPair.matchAndSetNameFromGroup(PALEMOON_LINUX_MAC_VERSION_PATTERN, deviceUA, 1)) {
                    browserPair.setName("PaleMoon");
                    browserPair.setVersion(osPair.getGroup(2));
                    return;
                }
            }

            // --- Firefox ---
            if (StringMatchUtils.indexOf(deviceUA, "Firefox") >= 0) {
                if (osPair.matchAndSetNameFromGroup(FIREFOX_WINDOWS_VERSION_PATTERN, deviceUA, 1)) {
                    browserPair.setName("Firefox");
                    browserPair.setVersion(osPair.getGroup(2));
                    return;
                }
                if (osPair.matchAndSetNameFromGroup(FIREFOX_LINUX_MAC_VERSION_PATTERN, deviceUA, 1)) {
                    browserPair.setName("Firefox");
                    browserPair.setVersion(osPair.getGroup(2));
                    return;
                }
                if (browserUA.contains("(X11; ")) {
                    osPair.setName("Linux");
                    browserPair.matchAndSetGroup(FIREFOX_SIMPLE_VERSION_PATTERN, deviceUA, "Firefox", 1);
                    return;
                }
            }

            // --- CFNetwork App (兜底使用设备能力值) ---
            if (StringMatchUtils.indexOf(browserUA, "CFNetwork") >= 0) {
                osPair.setName(device.getCapability("device_os"));
                osPair.setVersion(device.getCapability("device_os_version"));
                browserPair.setName("CFNetwork App");
                browserPair.setVersion(device.getCapability("mobile_browser_version"));
                return;
            }

            // --- 最终兜底：尝试 Windows / Mac OS 名称，或 Linux ---
            if (!osPair.matchAndSetNameFromGroup(WINDOWS_OS_NAME_PATTERN, deviceUA, 1)
                    && !osPair.matchAndSetNameFromGroup(MAC_OS_NAME_PATTERN, deviceUA, 1)
                    && browserUA != null
                    && (browserUA.contains("(X11; ") || browserUA.contains("Linux x86_64"))) {
                osPair.setName("Linux");
            }
        }
    }

    /**
     * 捕获上游浏览器（Chrome/Chromium/WebKit）的版本信息。
     * <p>当 UA 被识别为基于 Chrome 内核的二次开发浏览器（如 QQ Browser、WeChat、Edge 等）时，
     * 将底层 Chrome 版本保存为上游信息，供 {@code upstream_browser} / {@code upstream_browser_version}
     * 虚拟能力使用。</p>
     */
    private static void captureUpstreamBrowser(VirtualCapabilityDevice vcd, String browserUA) {
        String upstreamName = null;
        String upstreamVersion = null;

        // 尝试匹配 Chrome 版本（优先）
        java.util.regex.Matcher chromeMatcher = CHROME_VERSION_PATTERN.matcher(browserUA);
        if (chromeMatcher.find()) {
            upstreamName = "Chrome";
            upstreamVersion = chromeMatcher.group(1);
        }

        // 尝试匹配 Chromium 版本（Chrome 未匹配时）
        if (upstreamName == null) {
            java.util.regex.Matcher chromiumMatcher = CHROMIUM_VERSION_PATTERN.matcher(browserUA);
            if (chromiumMatcher.find()) {
                upstreamName = "Chromium";
                upstreamVersion = chromiumMatcher.group(1);
            }
        }

        // 尝试匹配 Safari/WebKit 版本（兜底）
        if (upstreamName == null) {
            java.util.regex.Matcher safariMatcher = SAFARI_DESKTOP_VERSION_PATTERN.matcher(browserUA);
            if (safariMatcher.find()) {
                upstreamName = "WebKit";
                upstreamVersion = safariMatcher.group(2);
            }
        }

        if (upstreamName != null) {
            vcd.setBrowserCore(upstreamName, upstreamVersion);
        }
    }
}
