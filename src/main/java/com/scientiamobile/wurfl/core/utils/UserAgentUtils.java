package com.scientiamobile.wurfl.core.utils;

import com.scientiamobile.wurfl.core.WURFLEngine;
import com.scientiamobile.wurfl.core.request.HttpServletRequestHeaderProvider;
import com.scientiamobile.wurfl.core.request.WURFLHeaderProvider;
import com.scientiamobile.wurfl.core.resource.ResourceUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User-Agent 解析与处理工具类。
 * <p>提供 WURFL 引擎中最核心的 User-Agent 字符串解析方法集合，
 * 涵盖从 HTTP 请求头中提取 User-Agent、解析 Android/iOS/Windows Phone 等
 * 主流操作系统版本与设备型号、判断设备类型（移动/桌面/智能电视/爬虫）、
 * 清洗 URL 编码内容以及构建 API 日志 User-Agent 等功能。
 * 所有方法均为无状态的静态工具方法。</p>
 */

public final class UserAgentUtils {

    /**
     * 用于去除 UA Profile 中引号的正则模式
     */
    public static final Pattern STRIP_QUOTE_PATTERN;

    /**
     * 用于匹配命名空间编号（如 "ns=123"）的正则模式
     */
    public static final Pattern NAMESPACE_NUMBER_PATTERN;

    /** WURFL 官方认可的 Android 版本号集合 */
    private static final SortedSet<String> SUPPORTED_ANDROID_VERSIONS;

    /** 当前 JVM 的 Java 版本 */
    private static final String JAVA_VERSION = System.getProperty("java.version");

    /** 当前操作系统名称 */
    private static final String OS_NAME = System.getProperty("os.name");

    /** 当前操作系统版本 */
    private static final String OS_VERSION = System.getProperty("os.version");

    /** 匹配 "Android/2.3" 格式的版本号 */
    private static final Pattern ANDROID_VERSION_SLASH_PATTERN;

    /** 匹配 "Android 2.3" 格式的版本号 */
    private static final Pattern ANDROID_VERSION_SPACE_PATTERN;

    /** 匹配 Amazon 设备 ":::Android_2.3" 格式的版本号 */
    private static final Pattern AMAZON_ANDROID_VERSION_PATTERN;

    /** 匹配 Opera on Android 的主版本号 */
    private static final Pattern OPERA_ON_ANDROID_MAJOR_VERSION_PATTERN;

    /** 匹配 "Model Linux Android Release" 格式中的设备型号 */
    private static final Pattern ANDROID_MODEL_LINUX_ANDROID_RELEASE_PATTERN;

    /** 匹配 "Model Android/Linux" 格式中的设备型号 */
    private static final Pattern ANDROID_MODEL_ANDROID_LINUX_PATTERN;

    /** 匹配 Android User-Agent 中 "Build/" 或 "MIUI/" 前的设备型号 */
    private static final Pattern ANDROID_MODEL_BUILD_PATTERN;

    /** 匹配 Amazon 购物应用的设备型号 */
    private static final Pattern ANDROID_MODEL_AMAZON_APP_PATTERN;

    /** 匹配 GIONEE 品牌设备型号 */
    private static final Pattern GIONEE_MODEL_PATTERN;

    /** 用于修复分号后缺少空格的 User-Agent 字符串 */
    private static final Pattern SEMICOLON_WITHOUT_SPACE_PATTERN;

    /** 匹配 Amazon 购物平台 User-Agent 中的设备型号 */
    private static final Pattern AMAZON_SHOPPING_MODEL_PATTERN;

    /** 匹配 User-Agent 中的 "xx-xx" 区域设置占位符 */
    private static final Pattern XX_XX_LOCALE_PATTERN;

    /** 匹配中国移动定制后缀（_CMCC_TD, _CMCC, _TD） */
    private static final Pattern CMCC_SUFFIX_PATTERN;

    /** 匹配华为品牌前缀 "HW-HUAWEI_" */
    private static final Pattern HUAWEI_PREFIX_PATTERN;

    /** 匹配酷派品牌前缀 "YL-Coolpad_" */
    private static final Pattern COOLPAD_PREFIX_PATTERN;

    /** 匹配 HTC 品牌前缀 */
    private static final Pattern HTC_PREFIX_PATTERN;

    /** 匹配版本号后缀（如 " V2.3"） */
    private static final Pattern VERSION_SUFFIX_PATTERN;

    /** 匹配 UC 浏览器主版本号 */
    private static final Pattern UC_BROWSER_MAJOR_VERSION_PATTERN;

    /** 匹配 "Adr 4.0.3" 格式的 Android 版本 */
    private static final Pattern ADR_ANDROID_VERSION_PATTERN;

    /** 匹配 UC 浏览器 User-Agent 中的 Android 设备型号 */
    private static final Pattern UC_ANDROID_MODEL_PATTERN;

    /** 匹配末尾多余版本号信息 */
    private static final Pattern VERSION_TRAILING_PATTERN;

    /** 匹配 "/..." 到最后的部分 */
    private static final Pattern SLASH_TO_END_PATTERN;

    /** 匹配三星设备型号 */
    private static final Pattern SAMSUNG_MODEL_PATTERN;

    /** 匹配 Orange 后缀 */
    private static final Pattern ORANGE_SUFFIX_PATTERN;

    /** 匹配 LG 设备型号（带可选连字符） */
    private static final Pattern LG_MODEL_PATTERN;

    /** 匹配 LG 设备型号（连字符可选） */
    private static final Pattern LG_MODEL_OPTIONAL_HYPHEN_PATTERN;

    /** 匹配时间戳标记 "[1234567890]" */
    private static final Pattern TIMESTAMP_IN_BRACKETS_PATTERN;

    /** 匹配设备品牌前缀（SAMSUNG/SonyEricsson/Sony/HUAWEI） */
    private static final Pattern BRAND_PREFIX_PATTERN;

    /** 匹配 Windows Phone 设备的型号 */
    private static final Pattern WINDOWS_PHONE_MODEL_PATTERN;

    /** 匹配 Edge 浏览器 User-Agent 中的 Windows Phone 型号 */
    private static final Pattern WINDOWS_PHONE_EDGE_MODEL_PATTERN;

    /** 匹配 Windows Phone OS 版本号 */
    private static final Pattern WINDOWS_PHONE_VERSION_PATTERN;

    /** 匹配 Windows NT 版本号 */
    private static final Pattern WINDOWS_NT_VERSION_PATTERN;

    /** 匹配 Windows Phone Desktop 模式下的设备型号 */
    private static final Pattern WINDOWS_PHONE_DESKTOP_MODEL_PATTERN;

    /** 匹配 ARM 架构 Edge 浏览器的 Windows Phone 型号 */
    private static final Pattern WINDOWS_PHONE_ARM_EDGE_MODEL_PATTERN;

    /** 匹配诺基亚 RM 设备型号 */
    private static final Pattern NOKIA_RM_MODEL_PATTERN;

    /** 匹配微软 RM 设备型号 */
    private static final Pattern MICROSOFT_RM_MODEL_PATTERN;

    /** 用于检测移动设备的关键词列表 */
    private static final List<String> MOBILE_KEYWORDS;

    /** 用于检测桌面浏览器的关键词列表 */
    private static final List<String> DESKTOP_KEYWORDS;

    /** 用于检测智能电视的关键词列表 */
    private static final List<String> SMART_TV_KEYWORDS;

    /** 用于检测爬虫/机器人程序的关键词列表 */
    private static final List<String> BOT_KEYWORDS;

    /** 移动设备关键词的 Aho-Corasick 匹配器 */
    private static final AhoCorasickKeywordMatcher MOBILE_KEYWORDS_MATCHER;

    /** 桌面浏览器关键词的 Aho-Corasick 匹配器 */
    private static final AhoCorasickKeywordMatcher DESKTOP_BROWSER_MATCHER;

    /** 智能电视关键词的 Aho-Corasick 匹配器 */
    private static final AhoCorasickKeywordMatcher SMART_TV_BROWSER_MATCHER;

    /** 爬虫关键词的 Aho-Corasick 匹配器 */
    private static final AhoCorasickKeywordMatcher BOT_MATCHER;

    /** 匹配桌面 Safari 浏览器的标准 User-Agent 模式 */
    private static final Pattern DESKTOP_SAFARI_PATTERN;

    /** 匹配 IE11 及以上版本的 Trident 引擎标识 */
    private static final Pattern IE11_TRIDENT_PATTERN;

    /** 匹配 MSIE 9 和 10 */
    private static final Pattern MSIE_9_10_PATTERN;

    /** 匹配旧版 MSIE 浏览器 */
    private static final Pattern MSIE_LEGACY_PATTERN;

    /** 匹配屏幕尺寸标识（如 "480x800"） */
    private static final Pattern SCREEN_SIZE_PATTERN;

    static {
        SUPPORTED_ANDROID_VERSIONS = new TreeSet<>();
        SUPPORTED_ANDROID_VERSIONS.add("1.0");
        SUPPORTED_ANDROID_VERSIONS.add("1.5");
        SUPPORTED_ANDROID_VERSIONS.add("1.6");
        SUPPORTED_ANDROID_VERSIONS.add("2.0");
        SUPPORTED_ANDROID_VERSIONS.add("2.1");
        SUPPORTED_ANDROID_VERSIONS.add("2.2");
        SUPPORTED_ANDROID_VERSIONS.add("2.3");
        SUPPORTED_ANDROID_VERSIONS.add("2.4");
        SUPPORTED_ANDROID_VERSIONS.add("3.0");
        SUPPORTED_ANDROID_VERSIONS.add("3.1");
        SUPPORTED_ANDROID_VERSIONS.add("3.2");
        SUPPORTED_ANDROID_VERSIONS.add("3.3");
        SUPPORTED_ANDROID_VERSIONS.add("4.0");
        SUPPORTED_ANDROID_VERSIONS.add("4.1");
        SUPPORTED_ANDROID_VERSIONS.add("4.2");
        SUPPORTED_ANDROID_VERSIONS.add("4.3");
        SUPPORTED_ANDROID_VERSIONS.add("4.4");
        SUPPORTED_ANDROID_VERSIONS.add("4.5");
        SUPPORTED_ANDROID_VERSIONS.add("5.0");
        SUPPORTED_ANDROID_VERSIONS.add("5.1");
        SUPPORTED_ANDROID_VERSIONS.add("5.2");
        SUPPORTED_ANDROID_VERSIONS.add("5.3");
        SUPPORTED_ANDROID_VERSIONS.add("6.0");
        SUPPORTED_ANDROID_VERSIONS.add("6.1");
        SUPPORTED_ANDROID_VERSIONS.add("7.0");
        SUPPORTED_ANDROID_VERSIONS.add("7.1");
        SUPPORTED_ANDROID_VERSIONS.add("7.2");
        SUPPORTED_ANDROID_VERSIONS.add("8.0");
        SUPPORTED_ANDROID_VERSIONS.add("8.1");
        SUPPORTED_ANDROID_VERSIONS.add("9.0");
        STRIP_QUOTE_PATTERN = Pattern.compile("\"");
        NAMESPACE_NUMBER_PATTERN = Pattern.compile("ns=(\\d*)");
        ANDROID_VERSION_SLASH_PATTERN = Pattern.compile("Android/(\\d\\.\\d)");
        ANDROID_VERSION_SPACE_PATTERN = Pattern.compile("Android (\\d\\.\\d)");
        AMAZON_ANDROID_VERSION_PATTERN = Pattern.compile(":::Android_(\\d\\.\\d)");
        OPERA_ON_ANDROID_MAJOR_VERSION_PATTERN = Pattern.compile("Version/(\\d+)\\.\\d+");
        ANDROID_MODEL_LINUX_ANDROID_RELEASE_PATTERN = Pattern.compile("(^[A-Za-z0-9_\\-\\+ ]+)[/ ]?(?:[A-Za-z0-9_\\-\\+\\.]+)? +Linux/[0-9\\.\\+]+ +Android[ /][0-9\\.]+ +Release/[0-9\\.]+");
        ANDROID_MODEL_ANDROID_LINUX_PATTERN = Pattern.compile("(^[A-Za-z0-9_\\-\\+ ]+)[/ ]?(?:[A-Za-z0-9_\\-\\+\\.]+)? Android/[0-9\\.]+ \\(Linux;");
        ANDROID_MODEL_BUILD_PATTERN = Pattern.compile("Android [^;]+;(?>(?: xx-xx[ ;]+)?)(.+?)(?:Build/|MIUI/|\\))");
        ANDROID_MODEL_AMAZON_APP_PATTERN = Pattern.compile("^(?:AmazonWebView|Appstore|Amazon\\.com)/.+Android[/ ][\\d\\.]+/(?:[\\d]+/)?([A-Za-z0-9_\\- ]+)\\b");
        GIONEE_MODEL_PATTERN = Pattern.compile("Mobile Safari/[\\d\\.]+ (GiONEE-[A-Za-z0-9]+)/");
        SEMICOLON_WITHOUT_SPACE_PATTERN = Pattern.compile(";(?! )");
        AMAZON_SHOPPING_MODEL_PATTERN = Pattern.compile("^mShop:::Amazon_Android_[\\d\\.]+:::(.+?):::Android_[\\d\\.]+");
        XX_XX_LOCALE_PATTERN = Pattern.compile("xx-xx");
        CMCC_SUFFIX_PATTERN = Pattern.compile("(?:_CMCC_TD|_CMCC|_TD)\\b");
        HUAWEI_PREFIX_PATTERN = Pattern.compile("HW-HUAWEI_");
        COOLPAD_PREFIX_PATTERN = Pattern.compile("YL-Coolpad[ _]");
        HTC_PREFIX_PATTERN = Pattern.compile("HTC[ _\\-/]");
        VERSION_SUFFIX_PATTERN = Pattern.compile("( V| )\\d+?\\.[\\d\\.]+$");
        UC_BROWSER_MAJOR_VERSION_PATTERN = Pattern.compile("UCBrowser\\/(\\d+)\\.\\d");
        ADR_ANDROID_VERSION_PATTERN = Pattern.compile("; Adr (\\d+\\.\\d+)\\.?");
        UC_ANDROID_MODEL_PATTERN = Pattern.compile("Adr [\\d\\.]+; [a-zA-Z]+-[a-zA-Z]+; (.*)\\) U2");
        VERSION_TRAILING_PATTERN = Pattern.compile("(/| +V?\\d)[\\.\\d]+$");
        SLASH_TO_END_PATTERN = Pattern.compile("/.*$");
        SAMSUNG_MODEL_PATTERN = Pattern.compile("(SAMSUNG[^/]+)/.*$");
        ORANGE_SUFFIX_PATTERN = Pattern.compile("ORANGE/.*$");
        LG_MODEL_PATTERN = Pattern.compile("(LG-[A-Za-z0-9\\-]+).*$");
        LG_MODEL_OPTIONAL_HYPHEN_PATTERN = Pattern.compile("(LG-?[A-Za-z0-9\\-]+).*$");
        TIMESTAMP_IN_BRACKETS_PATTERN = Pattern.compile("\\[[\\d]{10}\\]");
        BRAND_PREFIX_PATTERN = Pattern.compile("^(?:SAMSUNG|SonyEricsson|Sony|HUAWEI)[ \\-]?");
        WINDOWS_PHONE_MODEL_PATTERN = Pattern.compile("IEMobile/\\d+\\.\\d+;(?: ARM;)?(?: Touch;)? ?([^;\\)]+(; ?[^;\\)]+)?)");
        WINDOWS_PHONE_EDGE_MODEL_PATTERN = Pattern.compile("Android [\\d\\.]+?; ([^;\\)]+(; ?[^;\\)]+)?).+?Edge/\\d");
        WINDOWS_PHONE_VERSION_PATTERN = Pattern.compile("Windows ?Phone(?: ?OS)? ?(\\d+\\.\\d+)");
        WINDOWS_NT_VERSION_PATTERN = Pattern.compile("Windows NT (\\d+\\.\\d+)");
        WINDOWS_PHONE_DESKTOP_MODEL_PATTERN = Pattern.compile("\\(Windows NT [\\d\\.]+?; ARM;.+?; WPDesktop; ([^;\\)]+(; ?[^;\\)]+)?)\\) like Gecko");
        WINDOWS_PHONE_ARM_EDGE_MODEL_PATTERN = Pattern.compile("\\(Windows NT [\\d\\.]+?; ARM; ([^;\\)]+(; ?[^;\\)]+)?).+?Edge/\\d");
        NOKIA_RM_MODEL_PATTERN = Pattern.compile("(NOKIA; RM-.+?)_.*");
        MICROSOFT_RM_MODEL_PATTERN = Pattern.compile("(Microsoft; RM-.+?)_.*");
        MOBILE_KEYWORDS = new ArrayList<>();
        DESKTOP_KEYWORDS = new ArrayList<>();
        SMART_TV_KEYWORDS = new ArrayList<>();
        BOT_KEYWORDS = new ArrayList<>();
        MOBILE_KEYWORDS.add("midp");
        MOBILE_KEYWORDS.add("mobile");
        MOBILE_KEYWORDS.add("android");
        MOBILE_KEYWORDS.add("samsung");
        MOBILE_KEYWORDS.add("nokia");
        MOBILE_KEYWORDS.add("up.browser");
        MOBILE_KEYWORDS.add("phone");
        MOBILE_KEYWORDS.add("opera mini");
        MOBILE_KEYWORDS.add("opera mobi");
        MOBILE_KEYWORDS.add("brew");
        MOBILE_KEYWORDS.add("sonyericsson");
        MOBILE_KEYWORDS.add("blackberry");
        MOBILE_KEYWORDS.add("netfront");
        MOBILE_KEYWORDS.add("uc browser");
        MOBILE_KEYWORDS.add("symbian");
        MOBILE_KEYWORDS.add("j2me");
        MOBILE_KEYWORDS.add("wap2.");
        MOBILE_KEYWORDS.add("up.link");
        MOBILE_KEYWORDS.add(" arm;");
        MOBILE_KEYWORDS.add("windows ce");
        MOBILE_KEYWORDS.add("vodafone");
        MOBILE_KEYWORDS.add("ucweb");
        MOBILE_KEYWORDS.add("zte-");
        MOBILE_KEYWORDS.add("ipad;");
        MOBILE_KEYWORDS.add("docomo");
        MOBILE_KEYWORDS.add("armv");
        MOBILE_KEYWORDS.add("maemo");
        MOBILE_KEYWORDS.add("palm");
        MOBILE_KEYWORDS.add("bolt");
        MOBILE_KEYWORDS.add("fennec");
        MOBILE_KEYWORDS.add("wireless");
        MOBILE_KEYWORDS.add("adr-");
        MOBILE_KEYWORDS.add("htc");
        MOBILE_KEYWORDS.add("; xbox");
        MOBILE_KEYWORDS.add("nintendo");
        MOBILE_KEYWORDS.add("zunewp7");
        MOBILE_KEYWORDS.add("skyfire");
        MOBILE_KEYWORDS.add("silk");
        MOBILE_KEYWORDS.add("untrusted");
        MOBILE_KEYWORDS.add("lgtelecom");
        MOBILE_KEYWORDS.add(" gt-");
        MOBILE_KEYWORDS.add("ventana");
        MOBILE_KEYWORDS.add("tizen");
        DESKTOP_KEYWORDS.add("wow64");
        DESKTOP_KEYWORDS.add(".net clr");
        DESKTOP_KEYWORDS.add("gtb7");
        DESKTOP_KEYWORDS.add("macintosh");
        DESKTOP_KEYWORDS.add("slcc1");
        DESKTOP_KEYWORDS.add("gtb6");
        DESKTOP_KEYWORDS.add("funwebproducts");
        DESKTOP_KEYWORDS.add("aol 9.");
        DESKTOP_KEYWORDS.add("gtb8");
        DESKTOP_KEYWORDS.add("iceweasel");
        DESKTOP_KEYWORDS.add("epiphany");
        SMART_TV_KEYWORDS.add("googletv");
        SMART_TV_KEYWORDS.add("boxee");
        SMART_TV_KEYWORDS.add("sonydtv");
        SMART_TV_KEYWORDS.add("appletv");
        SMART_TV_KEYWORDS.add("apple tv");
        SMART_TV_KEYWORDS.add("smarttv");
        SMART_TV_KEYWORDS.add("smart-tv");
        SMART_TV_KEYWORDS.add("dlna");
        SMART_TV_KEYWORDS.add("ce-html");
        SMART_TV_KEYWORDS.add("inettvbrowser");
        SMART_TV_KEYWORDS.add("opera tv");
        SMART_TV_KEYWORDS.add("viera");
        SMART_TV_KEYWORDS.add("konfabulator");
        SMART_TV_KEYWORDS.add("sony bravia");
        SMART_TV_KEYWORDS.add("crkey");
        SMART_TV_KEYWORDS.add("sonycebrowser");
        SMART_TV_KEYWORDS.add("hbbtv");
        SMART_TV_KEYWORDS.add("large screen");
        SMART_TV_KEYWORDS.add("netcast");
        SMART_TV_KEYWORDS.add("philipstv");
        SMART_TV_KEYWORDS.add("digital-tv");
        SMART_TV_KEYWORDS.add(" mb90/");
        SMART_TV_KEYWORDS.add(" mb91/");
        SMART_TV_KEYWORDS.add(" mb95/");
        SMART_TV_KEYWORDS.add("vizio-dtv");
        SMART_TV_KEYWORDS.add("bravia");
        SMART_TV_KEYWORDS.add("roku");
        BOT_KEYWORDS.add("+http");
        BOT_KEYWORDS.add("bot");
        BOT_KEYWORDS.add("crawler");
        BOT_KEYWORDS.add("spider");
        BOT_KEYWORDS.add("novarra");
        BOT_KEYWORDS.add("transcoder");
        BOT_KEYWORDS.add("yahoo! searchmonkey");
        BOT_KEYWORDS.add("yahoo! slurp");
        BOT_KEYWORDS.add("feedfetcher-google");
        BOT_KEYWORDS.add("mowser");
        BOT_KEYWORDS.add("trove");
        BOT_KEYWORDS.add("google web preview");
        BOT_KEYWORDS.add("googleimageproxy");
        BOT_KEYWORDS.add("gigablastopensource");
        BOT_KEYWORDS.add("http-client");
        BOT_KEYWORDS.add("exactsearch");
        BOT_KEYWORDS.add("gecko/20100721");
        BOT_KEYWORDS.add("sureseeker.com");
        BOT_KEYWORDS.add("ltx71");
        BOT_KEYWORDS.add("searchsecure");
        BOT_KEYWORDS.add("iopus-i-m");
        MOBILE_KEYWORDS_MATCHER = new AhoCorasickKeywordMatcher(MOBILE_KEYWORDS);
        DESKTOP_BROWSER_MATCHER = new AhoCorasickKeywordMatcher(DESKTOP_KEYWORDS);
        SMART_TV_BROWSER_MATCHER = new AhoCorasickKeywordMatcher(SMART_TV_KEYWORDS);
        BOT_MATCHER = new AhoCorasickKeywordMatcher(BOT_KEYWORDS);
        DESKTOP_SAFARI_PATTERN = Pattern.compile("^Mozilla/5\\.0 \\((?:Macintosh|Windows)[^\\)]+\\) AppleWebKit/[\\d\\.]+ \\(KHTML, like Gecko\\) Version/[\\d\\.]+ Safari/[\\d\\.]+$");
        IE11_TRIDENT_PATTERN = Pattern.compile("^Mozilla\\/5\\.0 \\(Windows NT.+?Trident.+?; rv:\\d\\d\\.\\d+\\)");
        MSIE_9_10_PATTERN = Pattern.compile("^Mozilla\\/5\\.0 \\(compatible; MSIE (9|10)\\.0; Windows NT \\d\\.\\d");
        MSIE_LEGACY_PATTERN = Pattern.compile("^Mozilla\\/4\\.0 \\(compatible; MSIE \\d\\.\\d; Windows NT \\d\\.\\d");
        SCREEN_SIZE_PATTERN = Pattern.compile("[^\\d]\\d{3}x\\d{3}");
    }

    private UserAgentUtils() {
    }

    /**
     * 从 HTTP 请求中获取 User-Agent 字符串。
     * <p>根据 WURFL 最佳实践，优先使用 {@code Device-Stock-UA} 头（原始设备 UA），
     * 其次尝试 {@code X-OperaMini-Phone-UA}（Opera Mini 转发的原始 UA），
     * 最后回退到标准的 {@code User-Agent} 头。如果三者均不存在则返回空字符串。</p>
     *
     * @param request HTTP Servlet 请求对象
     * @return User-Agent 字符串，可能为空字符串
     */
    public static String getUserAgent(HttpServletRequest request) {
        Validate.notNull(request, "The HttpServletRequest is null");
        String userAgent;
        userAgent = request.getHeader("Device-Stock-UA");
        if (userAgent == null || "".equals(userAgent)) {
            userAgent = request.getHeader("X-OperaMini-Phone-UA");
        }

        if (userAgent == null || "".equals(userAgent)) {
            userAgent = request.getHeader("User-Agent");
        }

        if (userAgent == null) {
            userAgent = "";
        }

        return userAgent;
    }

    /**
     * 从 HTTP 请求中提取 UA Profile 值。
     *
     * @param request HTTP Servlet 请求对象
     * @return UA Profile URL 字符串，可能为 null
     */
    public static String getUaProfile(HttpServletRequest request) {
        return getUaProfile((WURFLHeaderProvider) (new HttpServletRequestHeaderProvider(request)));
    }

    /**
     * 从请求头提供者中提取 UA Profile 值。
     * <p>优先查找名为 {@code Profile} 的请求头，获取 URL 后去除其中的引号字符。</p>
     *
     * @param headerProvider WURFL 请求头提供者
     * @return 清洗后的 UA Profile URL，可能为 null
     */
    public static String getUaProfile(WURFLHeaderProvider headerProvider) {
        Validate.notNull(headerProvider, "The WURFLHeaderProvider is null");
        String headerName = null;
        String uaProfile = null;
        if (headerProvider.getHeader("Profile") != null) {
            headerName = "Profile";
        }

        if (headerName != null && headerName.trim().length() > 0) {
            uaProfile = headerProvider.getHeader(headerName);
        }

        if (uaProfile != null && uaProfile.trim().length() > 0) {
            uaProfile = STRIP_QUOTE_PATTERN.matcher(uaProfile).replaceAll("");
        }

        return uaProfile;
    }

    /**
     * 判断 HTTP 请求的 Accept 头中是否包含 XHTML 或 WML 标记。
     * <p>用于初步判断设备是否支持 XHTML MP 或 WAP 浏览器。</p>
     *
     * @param request HTTP Servlet 请求对象
     * @return 如果 Accept 头包含 XHTML 或 WML 标记则返回 {@code true}
     */
    public static boolean isXhtmlRequester(HttpServletRequest request) {
        Validate.notNull(request, "HttpRequest is null");
        String accept;
        accept = request.getHeader("accept");
        return accept != null && (accept.indexOf("application/vnd.wap.xhtml+xml") != -1 || accept.indexOf("text/vnd.wap.wml") != -1);
    }

    /**
     * 创建一个忽略大小写的包含判断谓词。
     * <p>用于在集合或流式操作中判断字符串是否包含指定值。</p>
     *
     * @param value 被搜索的基准字符串
     * @return 新的 Predicate 实例
     */
    public static Predicate<String> isContainedIn(String value) {
        return new ContainsIgnoreCasePredicate(value);
    }

    /**
     * 动态生成匹配区域设置（Locale）的正则表达式模式。
     * <p>基于 JVM 支持的所有 ISO 语言和国家代码生成，用于从 User-Agent 中
     * 匹配合法的区域设置标识（如 "; en-US"）。</p>
     *
     * @return 匹配区域设置的 Pattern 对象
     */
    public static Pattern createLocalePattern() {
        StringBuilder patternBuilder;
        patternBuilder = new StringBuilder();
        patternBuilder.append("; (");
        String[] languages = Locale.getISOLanguages();

        for (int i = 0; i < languages.length; ++i) {
            if (i > 0) {
                patternBuilder.append("|");
            }

            patternBuilder.append(languages[i]);
        }

        patternBuilder.append(")");
        patternBuilder.append("(-(");
        String[] countries = Locale.getISOCountries();

        for (int i = 0; i < countries.length; ++i) {
            if (i > 0) {
                patternBuilder.append("|");
            }

            patternBuilder.append(countries[i]);
        }

        patternBuilder.append("))?");
        return Pattern.compile(patternBuilder.toString(), Pattern.CASE_INSENSITIVE);
    }

    /**
     * 从 HTTP 请求中提取所有请求头，以键值对形式返回。
     *
     * @param request HTTP Servlet 请求对象
     * @return 请求头的键值映射
     */
    public static Map<String, String> getHeaders(HttpServletRequest request) {
        HashMap<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }

        return headers;
    }

    /**
     * 从 User-Agent 中提取 Android 操作系统版本号。
     * <p>依次尝试匹配 "Android 4.0"、 "Android/4.0" 和 Amazon 定制格式，
     * 仅返回 WURFL 官方支持的版本号范围。</p>
     *
     * @param userAgent              User-Agent 字符串
     * @param returnDefaultIfMissing 如果未找到匹配版本时是否返回默认值 "4.0"
     * @return 版本号字符串，如 "4.4"；未找到且 returnDefaultIfMissing 为 false 时返回 null
     */
    public static String getAndroidVersion(String userAgent, boolean returnDefaultIfMissing) {
        Matcher matcher;
        boolean found;
        matcher = ANDROID_VERSION_SPACE_PATTERN.matcher(userAgent);
        found = matcher.find();
        if (!found) {
            matcher = ANDROID_VERSION_SLASH_PATTERN.matcher(userAgent);
            found = matcher.find();
        }

        if (!found) {
            matcher = AMAZON_ANDROID_VERSION_PATTERN.matcher(userAgent);
            found = matcher.find();
        }

        if (found) {
            userAgent = matcher.group(1);
            if (SUPPORTED_ANDROID_VERSIONS.contains(userAgent)) {
                return userAgent;
            }
        }

        return returnDefaultIfMissing ? "4.0" : null;
    }

    /**
     * 从 User-Agent 中提取 Opera on Android 的版本号。
     * <p>仅识别版本 11 和 12，其他版本（包括未匹配）返回默认值 "10"。</p>
     *
     * @param userAgent              User-Agent 字符串
     * @param returnDefaultIfMissing 未匹配时是否返回默认值
     * @return Opera 主版本号，如 "11"
     */
    public static String getOperaOnAndroidVersion(String userAgent, boolean returnDefaultIfMissing) {
        Matcher matcher;
        matcher = OPERA_ON_ANDROID_MAJOR_VERSION_PATTERN.matcher(userAgent);
        if (!matcher.find() || !matcher.group(1).equals("11") && !matcher.group(1).equals("12")) {
            return returnDefaultIfMissing ? "10" : null;
        } else {
            return matcher.group(1);
        }
    }

    /**
     * 从 User-Agent 中提取 Android 设备型号。
     * <p>尝试多种 Android User-Agent 格式，包括标准 Linux Android Release 格式、
     * Amazon 应用格式、GIONEE 品牌等。对提取的型号进行品牌前缀清洗
     * （HTC/HUAWEI/Coolpad/Samsung/Orange/LG）及品牌名标准化处理。</p>
     *
     * @param userAgent User-Agent 字符串
     * @return 清洗后的设备型号，如果无法提取则返回 null
     */
    public static String getAndroidModel(String userAgent) {
        userAgent = SEMICOLON_WITHOUT_SPACE_PATTERN.matcher(userAgent).replaceAll("; ");
        Matcher primaryMatcher;
        primaryMatcher = GIONEE_MODEL_PATTERN.matcher(userAgent);
        if (primaryMatcher.find()) {
            userAgent = primaryMatcher.group(1);
        } else {
            primaryMatcher = ANDROID_MODEL_LINUX_ANDROID_RELEASE_PATTERN.matcher(userAgent);
            Matcher secondaryMatcher = ANDROID_MODEL_ANDROID_LINUX_PATTERN.matcher(userAgent);
            if (primaryMatcher.find()) {
                userAgent = StringMatchUtils.rtrim(primaryMatcher.group(1), ';', ' ');
            } else if (secondaryMatcher.find()) {
                userAgent = StringMatchUtils.rtrim(secondaryMatcher.group(1), ' ', ';');
            } else {
                primaryMatcher = ANDROID_MODEL_BUILD_PATTERN.matcher(userAgent);
                secondaryMatcher = ANDROID_MODEL_AMAZON_APP_PATTERN.matcher(userAgent);
                Matcher amazonShoppingMatcher = AMAZON_SHOPPING_MODEL_PATTERN.matcher(userAgent);
                if (primaryMatcher.find()) {
                    userAgent = StringMatchUtils.rtrim(primaryMatcher.group(1), ';', ' ');
                } else if (secondaryMatcher.find()) {
                    userAgent = secondaryMatcher.group(1);
                } else {
                    if (!amazonShoppingMatcher.find()) {
                        return null;
                    }

                    userAgent = amazonShoppingMatcher.group(1);
                }
            }
        }

        if (userAgent != null && userAgent.startsWith("Build/")) {
            return null;
        } else {
            int slashIndex;
            userAgent = StringMatchUtils.replaceAll(StringMatchUtils.replaceAll(userAgent, XX_XX_LOCALE_PATTERN, ""), CMCC_SUFFIX_PATTERN, "");
            if (userAgent.contains("*") && (slashIndex = StringMatchUtils.indexOf(userAgent, "/")) >= 0) {
                userAgent = userAgent.substring(0, slashIndex);
            }

            userAgent = StringMatchUtils.replaceAll(StringMatchUtils.replaceAll(userAgent, HUAWEI_PREFIX_PATTERN, "HUAWEI "), COOLPAD_PREFIX_PATTERN, "Coolpad ");
            if (userAgent.contains("HTC")) {
                userAgent = StringMatchUtils.replaceAll(userAgent, HTC_PREFIX_PATTERN, "HTC~");
                slashIndex = userAgent.indexOf("/");
                if (slashIndex >= 0) {
                    userAgent = userAgent.substring(0, slashIndex);
                }

                userAgent = StringMatchUtils.replaceAll(userAgent, VERSION_SUFFIX_PATTERN, "");
            }

            userAgent = StringMatchUtils.replaceAll(StringMatchUtils.replaceAll(StringMatchUtils.replaceAll(StringMatchUtils.replaceAll(StringMatchUtils.replaceAll(userAgent, SAMSUNG_MODEL_PATTERN, "$1"), ORANGE_SUFFIX_PATTERN, "ORANGE"), LG_MODEL_OPTIONAL_HYPHEN_PATTERN, "$1"), TIMESTAMP_IN_BRACKETS_PATTERN, "").trim(), BRAND_PREFIX_PATTERN, "");
            if (userAgent.length() == 0) {
                userAgent = null;
            }

            return userAgent;
        }
    }

    /**
     * 从 User-Agent 中提取 UC 浏览器的主版本号。
     *
     * @param userAgent              User-Agent 字符串
     * @param returnDefaultIfMissing 参数保留用于接口一致性，本实现中未使用
     * @return UC 浏览器主版本号，如 "12"；如果无法匹配则返回 null
     */
    public static String getUcBrowserVersion(String userAgent, boolean returnDefaultIfMissing) {
        Matcher matcher;
        matcher = UC_BROWSER_MAJOR_VERSION_PATTERN.matcher(userAgent);
        return matcher.find() ? matcher.group(1) : null;
    }

    /**
     * 从 UC 浏览器的 User-Agent 中提取 Android 版本号。
     * <p>匹配 "Adr 4.0.3" 格式的版本标识，仅返回 WURFL 官方支持的版本。</p>
     *
     * @param userAgent              User-Agent 字符串
     * @param returnDefaultIfMissing 未匹配时是否返回默认值 "4.0"
     * @return Android 版本号，如 "4.0"
     */
    public static String getUcAndroidVersion(String userAgent, boolean returnDefaultIfMissing) {
        Matcher matcher;
        matcher = ADR_ANDROID_VERSION_PATTERN.matcher(userAgent);
        if (matcher.find()) {
            String androidVersion = matcher.group(1);
            if (SUPPORTED_ANDROID_VERSIONS.contains(androidVersion)) {
                return androidVersion;
            }
        }

        return returnDefaultIfMissing ? "4.0" : null;
    }

    /**
     * 从 UC 浏览器的 User-Agent 中提取 Android 设备型号。
     *
     * @param userAgent              User-Agent 字符串
     * @param returnDefaultIfMissing 参数保留用于接口一致性，本实现中未使用
     * @return 设备型号字符串，如果无法匹配则返回 null
     */
    public static String getUcAndroidModel(String userAgent, boolean returnDefaultIfMissing) {
        Matcher matcher;
        matcher = UC_ANDROID_MODEL_PATTERN.matcher(userAgent);
        if (!matcher.find()) {
            return null;
        } else {
            String model = matcher.group(1);
            if (userAgent.contains("HTC")) {
                model = HTC_PREFIX_PATTERN.matcher(model).replaceFirst("HTC~");
                model = VERSION_TRAILING_PATTERN.matcher(model).replaceFirst("");
                model = SLASH_TO_END_PATTERN.matcher(model).replaceFirst("");
            }

            model = SAMSUNG_MODEL_PATTERN.matcher(model).replaceFirst("$1");
            model = ORANGE_SUFFIX_PATTERN.matcher(model).replaceFirst("ORANGE");
            model = LG_MODEL_PATTERN.matcher(model).replaceFirst("$1");
            String cleaned;
            cleaned = TIMESTAMP_IN_BRACKETS_PATTERN.matcher(model).replaceFirst("").trim();
            return StringUtils.isEmpty(cleaned) ? null : cleaned;
        }
    }

    /**
     * 从 User-Agent 中提取 Windows Phone 操作系统版本号。
     * <p>识别 Windows Phone 6.5/7.0/7.5/7.8/8.0/8.1/10.0 等常见版本。</p>
     *
     * @param userAgent User-Agent 字符串
     * @return 标准化的 Windows Phone 版本号，如 "8.1"；如果无法匹配则返回 null
     */
    public static final String getWindowsPhoneVersion(String userAgent) {
        Matcher matcher;
        matcher = WINDOWS_PHONE_VERSION_PATTERN.matcher(userAgent);
        if (matcher.find()) {
            String windowsPhoneVersion;
            windowsPhoneVersion = matcher.group(1);
            if (windowsPhoneVersion.startsWith("10.0")) {
                return "10.0";
            } else if (!windowsPhoneVersion.startsWith("6.3") && !windowsPhoneVersion.startsWith("8.1")) {
                if (windowsPhoneVersion.startsWith("8.")) {
                    return "8.0";
                } else if (windowsPhoneVersion.startsWith("7.8")) {
                    return "7.8";
                } else if (!windowsPhoneVersion.startsWith("7.10") && !windowsPhoneVersion.startsWith("7.5")) {
                    return windowsPhoneVersion.startsWith("6.5") ? "6.5" : "7.0";
                } else {
                    return "7.5";
                }
            } else {
                return "8.1";
            }
        } else {
            return null;
        }
    }

    /**
     * 从 User-Agent 中提取 Windows Phone 设备型号。
     *
     * @param userAgent User-Agent 字符串
     * @return 清洗后的设备型号，如果无法匹配则返回 null
     */
    public static final String getWindowsPhoneModel(String userAgent) {
        return cleanAndReplaceWindowsPhoneModel(userAgent, WINDOWS_PHONE_MODEL_PATTERN, WINDOWS_PHONE_EDGE_MODEL_PATTERN);
    }

    /**
     * 从 Windows Phone 桌面模式的 User-Agent 中提取 Windows NT 版本号。
     *
     * @param userAgent User-Agent 字符串
     * @return Windows NT 版本号，如 "10.0"；如果无法匹配则返回 null
     */
    public static final String getWindowsPhoneDesktopVersion(String userAgent) {
        Matcher matcher;
        matcher = WINDOWS_NT_VERSION_PATTERN.matcher(userAgent);
        if (matcher.find()) {
            String windowsNtVersion;
            windowsNtVersion = matcher.group(1);
            if (windowsNtVersion.indexOf("10.0") >= 0) {
                return "10.0";
            } else {
                return windowsNtVersion.indexOf("6.3") < 0 && windowsNtVersion.indexOf("8.1") < 0 ? "8.0" : "8.1";
            }
        } else {
            return null;
        }
    }

    /**
     * 从 Windows Phone 桌面模式的 User-Agent 中提取设备型号。
     *
     * @param userAgent User-Agent 字符串
     * @return 清洗后的设备型号，如果无法匹配则返回 null
     */
    public static final String getWindowsPhoneDesktopModel(String userAgent) {
        return cleanAndReplaceWindowsPhoneModel(userAgent, WINDOWS_PHONE_DESKTOP_MODEL_PATTERN, WINDOWS_PHONE_ARM_EDGE_MODEL_PATTERN);
    }

    /**
     * 使用一组正则模式从 Windows Phone User-Agent 中提取并清洗设备型号。
     * <p>先修复分号后缺少空格的问题，然后依次尝试每个模式，使用第一个匹配成功的结果。
     * 提取后去除 "_blocked" 后缀，并尝试使用 Nokia/Microsoft RM 模式进行标准化。</p>
     *
     * @param userAgent User-Agent 字符串
     * @param patterns  待尝试的正则匹配模式列表
     * @return 清洗后的设备型号，如果所有模式都未匹配则返回 null
     */
    public static final String cleanAndReplaceWindowsPhoneModel(String userAgent, Pattern... patterns) {
        userAgent = SEMICOLON_WITHOUT_SPACE_PATTERN.matcher(userAgent).replaceAll("; ");
        Matcher matcher = null;
        Pattern[] patternArray;
        patternArray = patterns;
        int patternCount = patternArray.length;

        for (int i = 0; i < patternCount; ++i) {
            matcher = patternArray[i].matcher(userAgent);
            if (matcher.find()) {
                break;
            }
            matcher = null;
        }

        if (matcher != null) {
            String model = matcher.group(1).replace("_blocked", "");
            model = NOKIA_RM_MODEL_PATTERN.matcher(model).replaceFirst("$1");
            return MICROSOFT_RM_MODEL_PATTERN.matcher(model).replaceFirst("$1");
        } else {
            return null;
        }
    }

    /**
     * 判断 User-Agent 是否来自 Windows Phone 广告客户端。
     *
     * @param userAgent User-Agent 字符串
     * @return 如果匹配则返回 {@code true}
     */
    public static boolean isWindowsPhoneAdClient(String userAgent) {
        return StringMatchUtils.startsWithAnyOf(userAgent, "Windows Phone Ad Client", "WindowsPhoneAdClient");
    }

    /**
     * 检测 User-Agent 中是否包含移动设备关键词。
     *
     * @param userAgent User-Agent 字符串
     * @return 如果包含移动关键词则返回 {@code true}
     */
    public static boolean mobileKeywordsDetected(String userAgent) {
        return MOBILE_KEYWORDS_MATCHER.matchesAny(userAgent);
    }

    /**
     * 检测 User-Agent 中是否包含屏幕尺寸标识（如 "480x800"）。
     * <p>屏幕尺寸信息有助于推断设备的显示能力。</p>
     *
     * @param userAgent User-Agent 字符串
     * @return 如果包含屏幕尺寸标识则返回 {@code true}
     */
    public static boolean screenSizeDetected(String userAgent) {
        return SCREEN_SIZE_PATTERN.matcher(userAgent).find();
    }

    /**
     * 检测 User-Agent 是否来自桌面浏览器。
     *
     * @param userAgent User-Agent 字符串
     * @return 如果是桌面浏览器则返回 {@code true}
     */
    public static boolean isDesktopBrowser(String userAgent) {
        return DESKTOP_BROWSER_MATCHER.matchesAny(userAgent);
    }

    /**
     * 检测 User-Agent 是否来自智能电视或机顶盒设备。
     *
     * @param userAgent User-Agent 字符串
     * @return 如果是智能电视则返回 {@code true}
     */
    public static boolean isSmartTvBrowser(String userAgent) {
        return SMART_TV_BROWSER_MATCHER.matchesAny(userAgent);
    }

    /**
     * 检测 User-Agent 是否来自网络爬虫或机器人程序。
     *
     * @param userAgent User-Agent 字符串
     * @return 如果是爬虫则返回 {@code true}
     */
    public static boolean isBot(String userAgent) {
        return BOT_MATCHER.matchesAny(userAgent);
    }

    /**
     * 使用正则精确匹配桌面 Safari 浏览器的标准 User-Agent 模式。
     *
     * @param userAgent User-Agent 字符串
     * @return 如果完全匹配桌面 Safari 格式则返回 {@code true}
     */
    public static boolean isDesktopPattern(String userAgent) {
        return DESKTOP_SAFARI_PATTERN.matcher(userAgent).matches();
    }

    /**
     * 检测 User-Agent 是否来自 Internet Explorer 浏览器。
     * <p>支持 IE11（Trident 引擎）、MSIE 9/10、以及旧版 MSIE。</p>
     *
     * @param userAgent User-Agent 字符串
     * @return 如果是 IE 浏览器则返回 {@code true}
     */
    public static boolean isIEPattern(String userAgent) {
        return IE11_TRIDENT_PATTERN.matcher(userAgent).find() || MSIE_9_10_PATTERN.matcher(userAgent).find() || MSIE_LEGACY_PATTERN.matcher(userAgent).find();
    }

    /**
     * 生成用于 WURFL API 日志的 User-Agent 字符串。
     * <p>包含构建 ID、WURFL API 版本、数据快照版本、Java 版本和操作系统信息，
     * 用于 WURFL 服务端识别调用方。</p>
     *
     * @param wurflEngine WURFL 引擎实例
     * @return 格式化的 API User-Agent 字符串
     */
    public static String createApiUserAgent(WURFLEngine wurflEngine) {
        String wurflVersion;
        String snapshotVersion;
        wurflVersion = wurflEngine.getWURFLUtils().getVersion();
        if (StringUtils.isEmpty(wurflVersion)) {
            snapshotVersion = wurflVersion;
        } else {
            String versionPrefix = "scientiamobile.com - ";
            int prefixIndex;
            prefixIndex = wurflVersion.indexOf(versionPrefix);
            if (prefixIndex == -1) {
                snapshotVersion = wurflVersion;
            } else {
                wurflVersion = wurflVersion.substring(prefixIndex + versionPrefix.length());
                snapshotVersion = "Snapshot_" + wurflVersion.substring(0, wurflVersion.lastIndexOf(32)).replace('-', '_');
            }
        }

        return (new StringBuilder(ResourceUtils.getBuildId())).append("/WURFL_JAVA_API/1.9.1.0 WURFL/").append(snapshotVersion).append(" Java/").append(JAVA_VERSION).append(" ").append(OS_NAME).append("/").append(OS_VERSION).toString();
    }

    /**
     * 获取移动设备关键词列表（不可修改视图）。
     *
     * @return 不可修改的关键词列表
     */
    public static List<String> getMobileKeywords() {
        return Collections.unmodifiableList(MOBILE_KEYWORDS);
    }

    /**
     * 判断 User-Agent 是否具有特殊的日志格式特征。
     * <p>如果字符串中不包含空格但包含 2 个以上的 '{@code +}' 字符，
     * 则认为是某种非标准日志格式。</p>
     *
     * @param userAgent User-Agent 字符串
     * @return 如果符合日志格式特征则返回 {@code true}
     */
    public static boolean hasIIsLoggingStyle(String userAgent) {
        return StringUtils.countMatches(userAgent, " ") == 0 && StringUtils.countMatches(userAgent, "+") > 2;
    }

    /**
     * 判断 User-Agent 是否为原始 URL 编码格式。
     * <p>如果字符串中包含 2 个以上的 '{@code %}' 字符，
     * 则表明它可能是经过 URL 编码的。</p>
     *
     * @param userAgent User-Agent 字符串
     * @return 如果是 URL 编码格式则返回 {@code true}
     */
    public static boolean isRawUrlEncoded(String userAgent) {
        return StringUtils.countMatches(userAgent, "%") > 2;
    }

    /**
     * 获取 ASNI 可打印字符的 User-Agent 字符串，同时统计特殊字符出现的次数。
     * <p>移除所有非 ASCII 可打印字符，并统计 '{@code +}' 和 '{@code %}' 的出现次数，
     * 以及是否包含空格。结果以 {@link UserAgentWithNeedleCount} 形式返回，
     * 供后续判断 URL 编码格式或日志格式使用。</p>
     *
     * @param userAgentBuilder User-Agent 字符串的 StringBuilder，方法内会直接修改
     * @return 包含清洗后字符串和统计信息的对象
     */
    public static UserAgentWithNeedleCount getAsciiPrintableStringWithNeedleCount(StringBuilder userAgentBuilder) {
        boolean hasSpaceChars = false;
        char[] needles = new char[]{'+', '%'};
        int[] needleCounts = new int[2];
        if (userAgentBuilder != null && userAgentBuilder.length() != 0) {
            for (int i = userAgentBuilder.length() - 1; i >= 0; --i) {
                char ch = userAgentBuilder.charAt(i);
                hasSpaceChars = hasSpaceChars || ch == ' ';
                if (!CharUtils.isAsciiPrintable(ch)) {
                    userAgentBuilder.deleteCharAt(i);
                } else {
                    for (int needleIndex = 0; needleIndex < 2; ++needleIndex) {
                        if (ch == needles[needleIndex]) {
                            needleCounts[needleIndex]++;
                        }
                    }
                }
            }

            return new UserAgentWithNeedleCount(userAgentBuilder.toString(), needleCounts[0], needleCounts[1], hasSpaceChars);
        } else {
            return new UserAgentWithNeedleCount("", 0, 0, false);
        }
    }

    /**
     * 从 User-Agent 字符串中移除所有非 ASCII 可打印字符。
     * <p>遍历字符串，仅保留 ASCII 可打印字符（包括字母、数字、标点符号和空格），
     * 移除控制字符和扩展 ASCII 字符。</p>
     *
     * @param userAgent 原始 User-Agent 字符串
     * @return 仅含 ASCII 可打印字符的字符串；如果输入为 null 则返回空字符串
     */
    public static String getAsciiPrintableString(String userAgent) {
        if (userAgent == null) {
            return "";
        } else {
            StringBuilder userAgentBuilder = new StringBuilder(userAgent);

            for (int i = userAgentBuilder.length() - 1; i >= 0; --i) {
                if (!CharUtils.isAsciiPrintable(userAgentBuilder.charAt(i))) {
                    userAgentBuilder.deleteCharAt(i);
                }
            }

            return userAgentBuilder.toString();
        }
    }
}
