package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 广告投放所需的应用程序名称虚拟能力评估器。
 * <p>通过分析 User-Agent 字符串推断请求来自哪个具体的原生应用程序。
 * 支持识别 Android Dalvik 应用、iOS CFNetwork 应用、Windows Phone 应用、
 * WebViewApp 以及超过 100 种已知 App 特征关键词。
 * 当无法识别具体应用时，返回 "Stock Browser"（系统内置浏览器）。</p>
 */

public class AppName implements VirtualCapabilityEvaluator, Serializable {

    /**
     * 用于识别 App 的关键词 → App 名称映射表（有序，按匹配优先级排列）。
     * <p>关键词越具体的应排在越前面，避免被宽泛的关键词误匹配。
     * 迭代顺序即为匹配优先级顺序。</p>
     */
    private static final Map<String, String> APP_KEYWORDS = new LinkedHashMap<>();

    @Serial
    private static final long serialVersionUID = 7704959740704532442L;

    /**
     * 匹配 "WebViewApp 应用名/" 格式的模式
     */
    private static final Pattern WEBVIEW_APP_PATTERN = Pattern.compile("WebViewApp ([^/]+)/");

    /**
     * 匹配 Android Dalvik 应用的模式，如 "应用名/版本 Dalvik/"
     */
    private static final Pattern ANDROID_DALVIK_APP_PATTERN = Pattern.compile("^([^/]+)/.+? Dalvik/");

    /**
     * 匹配 iOS CFNetwork 应用的模式，如 "应用名/版本 iPhone3,1 iOS/ CFNetwork/"
     */
    private static final Pattern IOS_CFNETWORK_APP_PATTERN = Pattern.compile("^([^/]+)/[\\d\\.-_]+ i(?:Phone|Pad|Pod)\\d+?,\\d+? iOS/[\\d_]+ CFNetwork/[\\d\\.]+");

    /**
     * 匹配 Windows Phone 应用的模式，如 "应用名/版本 Windows Phone/"
     */
    private static final Pattern WINDOWS_PHONE_APP_PATTERN = Pattern.compile("^([^/]+)/[0-9\\.-_]+ Windows Phone/[\\d\\.]+");

    static {
        // @formatter:off — 关键词按匹配优先级排序，具体的在前，宽泛的在后
        APP_KEYWORDS.put("abcf/", "Freeform/ABC Family");
        APP_KEYWORDS.put("adultswim", "AdultSwim");
        APP_KEYWORDS.put("Aliexpress", "AliExpress");
        APP_KEYWORDS.put("AOLShield", "AOL Shield browser");
        APP_KEYWORDS.put(" GSA/", "Google Search");
        APP_KEYWORDS.put("AmazonAdSDK", "Amazon Ad SDK");
        APP_KEYWORDS.put("Appstore/release-", "Amazon App Store");
        APP_KEYWORDS.put("AppStore/", "iOS App Store");
        APP_KEYWORDS.put("Amazon", "Amazon App");
        APP_KEYWORDS.put("Argo/", "Netflix");
        APP_KEYWORDS.put("BBCStoryOfLife", "BBC Story of Life");
        APP_KEYWORDS.put("bdbrowser", "Baidu browser");
        APP_KEYWORDS.put("com.apple.tv", "TV on iOS");
        APP_KEYWORDS.put("comedycentral", "Comedy Central");
        APP_KEYWORDS.put("DroidRBMobile", "Redbox");
        APP_KEYWORDS.put("ESPN/", "ESPN");
        APP_KEYWORDS.put("FB_IAB/MESSENGER", "Facebook Messenger");
        APP_KEYWORDS.put("FB_IAB", "Facebook");
        APP_KEYWORDS.put("FB4A", "Facebook");
        APP_KEYWORDS.put("FBAV/", "Facebook");
        APP_KEYWORDS.put("UCWEB", "UCBrowser");
        APP_KEYWORDS.put("UCBrowser", "UCBrowser");
        APP_KEYWORDS.put("QQBrowser", "QQ Browser");
        APP_KEYWORDS.put("MQQBrowser", "QQ Browser");
        APP_KEYWORDS.put(" QQ/", "QQ Browser");
        APP_KEYWORDS.put(" Edge/", "Edge Browser");
        APP_KEYWORDS.put(" EdgiOS/", "Edge Browser");
        APP_KEYWORDS.put(" EdgA/", "Edge Browser");
        APP_KEYWORDS.put(" Focus/", "Firefox Focus browser");
        APP_KEYWORDS.put(" Firefox/", "Firefox browser");
        APP_KEYWORDS.put(" FxiOS/", "Firefox browser");
        APP_KEYWORDS.put("Fennec", "Fennec browser");
        APP_KEYWORDS.put("Flipboard", "Flipboard");
        APP_KEYWORDS.put("fxnetworks", "FX Network");
        APP_KEYWORDS.put("Groupon", "Groupon");
        APP_KEYWORDS.put("hola_android", "Hola VPN");
        APP_KEYWORDS.put("iHeartRadio", "iHeartRadio");
        APP_KEYWORDS.put("Indeed App", "Indeed");
        APP_KEYWORDS.put("(InstaFollow)", "InstaFollow");
        APP_KEYWORDS.put("Instagram", "Instagram");
        APP_KEYWORDS.put("itunesstored/", "iTunes");
        APP_KEYWORDS.put("Kik/", "Kik");
        APP_KEYWORDS.put("LA Times/", "LA Times");
        APP_KEYWORDS.put("Liebao", "Liebao");
        APP_KEYWORDS.put("Line/", "LINE");
        APP_KEYWORDS.put("MicroMessenger", "WeChat");
        APP_KEYWORDS.put("Microsoft Outlook", "Microsoft Outlook");
        APP_KEYWORDS.put("Microsoft Office/", "Microsoft Office");
        APP_KEYWORDS.put("MSIE", "Internet Explorer");
        APP_KEYWORDS.put("NAVER", "Naver Search");
        APP_KEYWORDS.put("NewsWeather/", "Google News & Weather");
        APP_KEYWORDS.put("NokiaBrowser", "Nokia Browser");
        APP_KEYWORDS.put("nyt_android", "New York Times");
        APP_KEYWORDS.put("offerup", "OfferUp");
        APP_KEYWORDS.put("OfferUp", "OfferUp");
        APP_KEYWORDS.put("Onefootball", "Onefootball");
        APP_KEYWORDS.put("Opera", "Opera browser");
        APP_KEYWORDS.put(" OPR/", "Opera browser");
        APP_KEYWORDS.put("OviBrowser", "Nokia Ovi Browser");
        APP_KEYWORDS.put("nytiphone", "New York Times");
        APP_KEYWORDS.put("Pandora", "Pandora");
        APP_KEYWORDS.put("Pinterest", "Pinterest");
        APP_KEYWORDS.put("Puffin", "Puffin browser");
        APP_KEYWORDS.put("Relay", "Relay");
        APP_KEYWORDS.put("Reddit", "Reddit");
        APP_KEYWORDS.put(" Silk/", "Silk browser");
        APP_KEYWORDS.put("Skype", "Skype");
        APP_KEYWORDS.put("SoundCloud", "SoundCloud");
        APP_KEYWORDS.put("Spotify", "Spotify");
        APP_KEYWORDS.put("Twitter", "Twitter");
        APP_KEYWORDS.put("Uber/", "Uber");
        APP_KEYWORDS.put("uniqlo-app", "Uniqlo");
        APP_KEYWORDS.put("Valve Steam GameOverlay", "Steam Client");
        APP_KEYWORDS.put("Wash Post", "Washington Post");
        APP_KEYWORDS.put("Windows Maps", "Bing Maps");
        APP_KEYWORDS.put("YaBrowser", "Yandex browser");
        APP_KEYWORDS.put("YJApp", "Yahoo Japan");
        APP_KEYWORDS.put(" CriOS/", "Chrome browser");
        APP_KEYWORDS.put(" Chrome/", "Chrome browser");
        APP_KEYWORDS.put("iCatalog", "iCatalog");
        APP_KEYWORDS.put("mobincube", "Mobincube app builder");
        APP_KEYWORDS.put("AndroidCvpPlayer", "Android CVP Player");
        APP_KEYWORDS.put("ANVSDK", "Anvato Platform");
        APP_KEYWORDS.put("CFNetwork", "Native CFNetwork application");
        APP_KEYWORDS.put("Dalvik", "Native Android application");
        APP_KEYWORDS.put("Darwin", "Native CFNetwork application");
        APP_KEYWORDS.put("FreeWheelAdManager", "FreeWheel");
        APP_KEYWORDS.put("GoogleTagManager", "Google Tag Manager");
        APP_KEYWORDS.put("upLynkAndroidPlayer", "upLynk Android Player");
        APP_KEYWORDS.put("VisualOn OSMP+ Player", "VisualOn OSMP+ Video Player");
        APP_KEYWORDS.put("WindowsPhoneAdClient", "Windows Phone native ad client");
        APP_KEYWORDS.put("Windows Phone Ad Client", "Windows Phone native ad client");
        // @formatter:on
    }

    @Override
    public String eval(Device device, WURFLRequest request) {
        String userAgent = request.isUrlEncoded() ? request.getCleanedDeviceUserAgent() : request.getOriginalUserAgent();

        // 优先尝试 WebViewApp 格式
        if (userAgent.contains("WebViewApp")) {
            Matcher webViewAppMatcher = WEBVIEW_APP_PATTERN.matcher(userAgent);
            return webViewAppMatcher.find() ? webViewAppMatcher.group(1) : "WebView";
        }

        // 依次尝试 Android Dalvik、iOS CFNetwork、Windows Phone 三种主流 App 格式
        String appName = extractByPattern(userAgent, ANDROID_DALVIK_APP_PATTERN);
        if (appName != null) {
            return appName;
        }
        appName = extractByPattern(userAgent, IOS_CFNETWORK_APP_PATTERN);
        if (appName != null) {
            return appName;
        }
        appName = extractByPattern(userAgent, WINDOWS_PHONE_APP_PATTERN);
        if (appName != null) {
            return appName;
        }

        // 在预定义的关键词列表中查找匹配项，返回对应的 App 名称
        return lookupAppName(userAgent);
    }

    /**
     * 使用指定正则模式从 User-Agent 中提取 App 名称（group(1)）。
     *
     * @return 匹配到的 App 名称，无匹配返回 {@code null}
     */
    private static String extractByPattern(String userAgent, Pattern pattern) {
        Matcher matcher = pattern.matcher(userAgent);
        return matcher.find() ? matcher.group(1) : null;
    }

    /**
     * 在预定义的关键词表中查找匹配的 App 名称。
     * <p>按关键词的优先级顺序（LinkedHashMap 插入顺序）逐一检查，
     * 优先匹配排在前面的关键词。</p>
     *
     * @return 匹配的 App 名称，无匹配返回 "Stock Browser"
     */
    private static String lookupAppName(String userAgent) {
        for (Map.Entry<String, String> entry : APP_KEYWORDS.entrySet()) {
            if (userAgent.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        return "Stock Browser";
    }

    @Override
    public String getHandledVirtualCapabilityName() {
        return "advertised_app_name";
    }
}
