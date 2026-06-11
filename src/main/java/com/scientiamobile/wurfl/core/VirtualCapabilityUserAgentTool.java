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
    private static final Pattern SAFARI_DESKTOP_VERSION_PATTERN = Pattern.compile("Mozilla\\/[0-9]\\.0 \\((?:(?:Windows|Macintosh); (?:U; |WOW64; )?)?([a-zA-Z_ \\.0-9]+)(?:;)?.+? Version\\/([\\d\\.]+)\\.?");
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
    private static final Pattern FIREFOX_FOCUS_VERSION_PATTERN = Pattern.compile("Focus/([\\d\\.]+)");
    private static final Pattern IOS_FIREFOX_FOCUS_VERSION_PATTERN = Pattern.compile("^Mozilla/[45]\\.0.+?like Mac OS X.+?Focus/([\\d\\.]+) Mobile\\/[0-9A-Za-z]+");
    private static final Pattern WINDOWS_OS_NAME_PATTERN = Pattern.compile("(Windows [0-9A-Za-z \\.]+)");
    private static final Pattern MAC_OS_NAME_PATTERN = Pattern.compile("Macintosh;(?: U;)?([a-zA-Z_ \\.0-9]+)(?:;)?");
    private static VirtualCapabilityUserAgentTool instance = null;

    private VirtualCapabilityUserAgentTool() {
    }

    /**
     * 获取单例实例。
     *
     * @return 单例实例
     */

    public static VirtualCapabilityUserAgentTool getInstance() {
        if (instance == null) {
            instance = new VirtualCapabilityUserAgentTool();
        }

        return instance;
    }

    /**
     * 为指定的 User-Agent 请求分配设备和浏览器的属性。
     * <p>通过解析 User-Agent 字符串，识别操作系统和浏览器的名称及版本，
     * 并构建 {@link VirtualCapabilityDevice} 返回。</p>
     *
     * @param request         WURFL 请求
     * @param internalDevice  内部设备实例（用于获取设备能力值）
     * @return 包含识别结果的虚拟能力设备
     */

    public final VirtualCapabilityDevice assignProperties(WURFLRequest request, InternalDevice internalDevice) {
        VirtualCapabilityDevice virtualCapabilityDevice = new VirtualCapabilityDevice(request);
        InternalDevice device = internalDevice;
        VirtualCapabilityDevice deviceWithPairs = virtualCapabilityDevice;
        if (deviceWithPairs.getOsPair().containsAndSetName(deviceWithPairs.getDeviceUserAgent(), "Windows CE", "Windows Mobile")) {
            deviceWithPairs.getBrowserPair().setName("IE Mobile");
        } else if (!StringMatchUtils.containsAnyOf(deviceWithPairs.getDeviceUserAgent(), "Windows Phone", "; wds") || !deviceWithPairs.getOsPair().matchAndSetGroup(WINDOWS_PHONE_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), "Windows Phone", 1) && !deviceWithPairs.getOsPair().matchAndSetGroup(WINDOWS_UCWEB_WDS_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), "Windows Phone", 1) && !deviceWithPairs.getOsPair().matchAndSetGroup(WINDOWS_PHONE_VERSION_SLASH_PATTERN, deviceWithPairs.getDeviceUserAgent(), "Windows Phone", 1)) {
            label422:
            {
                if (deviceWithPairs.getDeviceUserAgent().contains("Nintendo")) {
                    deviceWithPairs.getOsPair().setName("Nintendo");
                    if (deviceWithPairs.getBrowserPair().matchAndSetGroup(NINTENDO_NETFRONT_NX_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Netfront NX", 1) || deviceWithPairs.getBrowserPair().matchAndSetGroup(NINTENDO_NETFRONT_NX_VERSION_PATTERN_2, deviceWithPairs.getBrowserUserAgent(), "Netfront NX", 1) || deviceWithPairs.getBrowserPair().matchAndSetGroup(NINTENDO_BROWSER_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Nintendo Browser", 1)) {
                        break label422;
                    }

                    deviceWithPairs.getBrowserPair().setName("Nintendo Browser");
                }

                if (StringMatchUtils.containsAnyOf(deviceWithPairs.getDeviceUserAgent(), "Android", "android", " Adr ")) {
                    label403:
                    {
                        deviceWithPairs.getOsPair().setName("Android");
                        deviceWithPairs.getOsPair().matchAndSetGroup(ANDROID_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), "Android", 1);
                        deviceWithPairs.getOsPair().matchAndSetGroup(AMAZON_ANDROID_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), "Android", 1);
                        deviceWithPairs.getOsPair().matchAndSetGroup(ADR_ANDROID_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), "Android", 1);
                        String deviceOs;
                        deviceOs = device.getCapability("device_os");
                        if (deviceOs.equals("Fire OS")) {
                            String deviceOsVersion = device.getCapability("device_os_version");
                            deviceWithPairs.getOsPair().setName(deviceOs);
                            deviceWithPairs.getOsPair().setVersion(deviceOsVersion);
                        }

                        if (StringMatchUtils.indexOf(deviceWithPairs.getBrowserUserAgent(), "Dalvik") >= 0) {
                            deviceWithPairs.getBrowserPair().setName("Android App");
                            if (deviceWithPairs.getBrowserPair().matchAndSetGroup(ANDROID_APP_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), (String) null, 1)) {
                                break label403;
                            }
                        }

                        if (!deviceWithPairs.getBrowserPair().matchAndSet(FACEBOOK_ANDROID_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Facebook on Android", deviceWithPairs.getOsPair().getVersion()) && !deviceWithPairs.getBrowserPair().matchAndSetGroup(AMAZON_SHOPPING_ANDROID_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Amazon Shopping App", 1)) {
                            if (deviceWithPairs.getBrowserPair().matchAndSetNameAndGroup(ANDROID_MOBILE_APP_NAME_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), 2)) {
                                deviceWithPairs.getBrowserPair().setName(deviceWithPairs.getBrowserPair().getName() + " Mobile Application");
                            } else if (!deviceWithPairs.getBrowserPair().matchAndSetGroup(OPERA_OPR_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Opera", 1)) {
                                if (StringMatchUtils.containsAnyOf(deviceWithPairs.getBrowserUserAgent(), "Aphone Browser", "360browser")) {
                                    deviceWithPairs.getBrowserPair().setName("360 Browser");
                                } else if (!deviceWithPairs.getBrowserPair().matchAndSetGroup(FIREFOX_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Firefox Mobile", 1) && !deviceWithPairs.getBrowserPair().matchAndSetGroup(FIREFOX_FOCUS_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Firefox Focus", 1) && !deviceWithPairs.getBrowserPair().matchAndSetGroup(OPERA_MOBILE_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Opera Mobile", 1) && !deviceWithPairs.getBrowserPair().matchAndSetGroup(OPERA_MINI_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Opera Mini", 1) && !deviceWithPairs.getBrowserPair().matchAndSetGroup(OPERA_TABLET_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Opera Tablet", 1) && !deviceWithPairs.getBrowserPair().matchAndSetGroup(UC_BROWSER_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "UC Browser", 1) && !deviceWithPairs.getBrowserPair().matchAndSetGroup(UC_BROWSER_JUC_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "UC Browser", 1) && !deviceWithPairs.getBrowserPair().matchAndSetGroup(AMAZON_SILK_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Amazon Silk Browser", 1) && !deviceWithPairs.getBrowserPair().matchAndSetGroup(BAIDU_BROWSER_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Baidu Browser", 1) && !deviceWithPairs.getBrowserPair().matchAndSetGroup(SAMSUNG_BROWSER_CHROME_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Samsung Browser", 1) && !deviceWithPairs.getBrowserPair().matchAndSetGroup(EDGE_ANDROID_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Edge", 1) && !deviceWithPairs.getBrowserPair().matchAndSetGroup(CHROMIUM_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Chromium", 1) && !deviceWithPairs.getBrowserPair().matchAndSetGroup(CHROME_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Chrome Mobile", 1) && !deviceWithPairs.getBrowserPair().matchAndSet(ANDROID_WEBKIT_VERSION_MARKER_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Android Webkit", deviceWithPairs.getOsPair().getVersion())) {
                                    deviceWithPairs.getBrowserPair().setName("Android");
                                    deviceWithPairs.getBrowserPair().setVersion(deviceWithPairs.getOsPair().getVersion());
                                }
                            }
                        }
                    }
                } else if (StringMatchUtils.indexOf(deviceWithPairs.getDeviceUserAgent(), "Silk") >= 0 && deviceWithPairs.getBrowserPair().matchAndSetGroup(AMAZON_SILK_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Amazon Silk Browser", 1)) {
                    deviceWithPairs.getOsPair().setName("Android");
                    deviceWithPairs.getOsPair().setVersion("");
                } else if (StringMatchUtils.containsAnyOf(deviceWithPairs.getDeviceUserAgent(), "iPhone", "iPad", "iPod", "iPod touch", "(iOS;")) {
                    deviceWithPairs.getOsPair().setName("iOS");
                    if (deviceWithPairs.getOsPair().matchAndSetGroup(IOS_CPU_OS_PATTERN, deviceWithPairs.getDeviceUserAgent(), "iOS", 2) || deviceWithPairs.getOsPair().matchAndSetGroup(IOS_SCALE_PATTERN, deviceWithPairs.getDeviceUserAgent(), "iOS", 1) || deviceWithPairs.getOsPair().matchAndSetGroup(IOS_SERVER_BAG_PATTERN, deviceWithPairs.getDeviceUserAgent(), "iOS", 1) || deviceWithPairs.getOsPair().matchAndSetGroup(IOS_DEVICE_PREFIX_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), "iOS", 1) || deviceWithPairs.getOsPair().matchAndSetGroup(IOS_DEVICE_IOS_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), "iOS", 1)) {
                        deviceWithPairs.getOsPair().setVersion(deviceWithPairs.getOsPair().getVersion().replaceAll("_", "."));
                    }

                    if (deviceWithPairs.getOsPair().matchAndSetGroup(IOS_UCWEB_OS_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), "iOS", 1)) {
                        deviceWithPairs.getOsPair().setVersion(deviceWithPairs.getOsPair().getVersion().replaceAll("_", "."));
                    }

                    if (!deviceWithPairs.getBrowserPair().matchAndSetGroup(IOS_CHROME_CRIOS_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Chrome Mobile on iOS", 1) && !deviceWithPairs.getBrowserPair().matchAndSetGroup(IOS_FIREFOX_FOCUS_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Firefox Focus", 1) && !deviceWithPairs.getBrowserPair().matchAndSetGroup(IOS_FIREFOX_FXIOS_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Firefox on iOS", 1) && !deviceWithPairs.getBrowserPair().matchAndSetGroup(IOS_OPERA_OPIOS_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Opera Mini on iOS", 1) && !deviceWithPairs.getBrowserPair().matchAndSetGroup(IOS_UC_BROWSER_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "UC Web Browser on iOS", 1) && !deviceWithPairs.getBrowserPair().matchAndSetGroup(IOS_UCWEB_UCBROWSER_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "UC Web Browser on iOS", 1) && !deviceWithPairs.getBrowserPair().matchAndSet(IOS_FACEBOOK_FBAN_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Facebook on iOS", deviceWithPairs.getOsPair().getVersion())) {
                        if (deviceWithPairs.getBrowserPair().matchAndSetNameAndGroup(IOS_MOBILE_APP_NAME_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), 2)) {
                            deviceWithPairs.getBrowserPair().setName(deviceWithPairs.getBrowserPair().getName() + " Mobile Application");
                        } else if (!deviceWithPairs.getBrowserPair().matchAndSetGroup(IOS_GOOGLE_SEARCH_APP_GSA_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Google Search Application", 1) && !deviceWithPairs.getBrowserPair().matchAndSetGroup(EDGE_IOS_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Edge", 1) && !deviceWithPairs.getBrowserPair().matchAndSetGroup(IOS_SAFARI_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Mobile Safari", 1)) {
                            deviceWithPairs.getBrowserPair().setName("Mobile Safari");
                            deviceWithPairs.getBrowserPair().setVersion(deviceWithPairs.getOsPair().getVersion());
                        }
                    }
                } else if (deviceWithPairs.getDeviceUserAgent().contains("Tizen")) {
                    deviceWithPairs.getOsPair().matchAndSetGroup(TIZEN_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), "Tizen", 1);
                    if (!deviceWithPairs.getBrowserPair().matchAndSetGroup(SAMSUNG_BROWSER_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Samsung Browser", 1)) {
                        deviceWithPairs.getBrowserPair().setName("Tizen Browser");
                        deviceWithPairs.getBrowserPair().setVersion(deviceWithPairs.getOsPair().getVersion());
                    }
                } else if (StringMatchUtils.indexOf(deviceWithPairs.getDeviceUserAgent(), "OviBrowser") >= 0 && deviceWithPairs.getBrowserPair().matchAndSetGroup(NOKIA_S40_OVI_BROWSER_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "S40 Ovi Browser", 1)) {
                    deviceWithPairs.getOsPair().setName("Nokia Series 40");
                } else if (!deviceWithPairs.getOsPair().matchAndSetGroup(SYMBIAN_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), "Symbian S60", 1) && !deviceWithPairs.getOsPair().matchAndSetGroup(SYMBIAN_UCWEB_S60_MAJOR_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), "Symbian S60", 1)) {
                    if (StringMatchUtils.indexOf(deviceWithPairs.getDeviceUserAgent(), "BlackBerry") < 0 && StringMatchUtils.indexOf(deviceWithPairs.getDeviceUserAgent(), "(BB10; ") < 0) {
                        if (StringMatchUtils.indexOf(deviceWithPairs.getDeviceUserAgent(), "RIM Tablet OS") >= 0 && deviceWithPairs.getOsPair().matchAndSetGroup(RIM_TABLET_OS_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), "RIM Tablet OS", 1)) {
                            deviceWithPairs.getBrowserPair().setName("RIM OS Browser");
                            deviceWithPairs.getBrowserPair().setVersion(deviceWithPairs.getOsPair().getGroup(2));
                        } else if ((StringMatchUtils.indexOf(deviceWithPairs.getDeviceUserAgent(), "NetFront") < 0 || !deviceWithPairs.getBrowserPair().matchAndSetGroup(NETFRONT_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "NetFront", 1)) && (!deviceWithPairs.getBrowserPair().containsAndSetName(deviceWithPairs.getDeviceUserAgent(), "Obigo", "Teleca Obigo") || !deviceWithPairs.getBrowserPair().matchAndSetGroup(OBIGO_Q_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), (String) null, 1))) {
                            if (StringMatchUtils.indexOf(deviceWithPairs.getDeviceUserAgent(), "Dolfin") >= 0 && deviceWithPairs.getOsPair().matchAndSetGroup(BADA_DOLFIN_PATTERN, deviceWithPairs.getDeviceUserAgent(), "Bada", 1)) {
                                deviceWithPairs.getBrowserPair().setName("Dolfin Browser");
                                deviceWithPairs.getBrowserPair().setVersion(deviceWithPairs.getOsPair().getGroup(2));
                            } else if (!deviceWithPairs.getBrowserPair().containsAndSetName(deviceWithPairs.getDeviceUserAgent(), "MAUI", "MAUI Browser") && (StringMatchUtils.indexOf(deviceWithPairs.getDeviceUserAgent(), "Dolfin") < 0 || !deviceWithPairs.getBrowserPair().matchAndSetGroup(OPENWAVE_BROWSER_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Openwave Browser", 1))) {
                                if (deviceWithPairs.getOsPair().matchAndSetGroup(WEBOS_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), "webOS", 1)) {
                                    deviceWithPairs.getBrowserPair().setName("webOS Browser");
                                    deviceWithPairs.getBrowserPair().setVersion(deviceWithPairs.getOsPair().getVersion());
                                } else {
                                    label407:
                                    {
                                        if (StringMatchUtils.indexOf(deviceWithPairs.getDeviceUserAgent(), "Opera") >= 0) {
                                            if (deviceWithPairs.getBrowserPair().containsAndSetName(deviceWithPairs.getDeviceUserAgent(), "Opera Mobi", "Opera Mobile")) {
                                                deviceWithPairs.getBrowserPair().matchAndSetGroup(OPERA_MOBI_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), (String) null, 1);
                                                break label407;
                                            }

                                            if (deviceWithPairs.getBrowserPair().matchAndSetGroup(OPERA_MINI_PATTERN, deviceWithPairs.getDeviceUserAgent(), "Opera Mini", 1) || deviceWithPairs.getBrowserPair().matchAndSetGroup(OPERA_LINK_SYNC_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), "Opera Link Sync", 1)) {
                                                break label407;
                                            }
                                        }

                                        if (StringMatchUtils.indexOf(deviceWithPairs.getDeviceUserAgent(), "Maemo") >= 0) {
                                            deviceWithPairs.getOsPair().setName("Maemo");
                                            if (deviceWithPairs.getBrowserPair().matchAndSetGroup(MAEMO_FIREFOX_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Firefox", 1)) {
                                                break label407;
                                            }
                                        }

                                        if ((!StringMatchUtils.containsAnyOf(deviceWithPairs.getDeviceUserAgent(), "Java", "UCBrowser/") || !deviceWithPairs.getBrowserPair().matchAndSetGroup(UCWEB_JAVA_UCBROWSER_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "UCBrowser Java Applet", 1)) && !deviceWithPairs.getBrowserPair().matchAndSet(JAVA_APPLET_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Java Applet", (String) null)) {
                                            label315:
                                            {
                                                if (StringMatchUtils.indexOf(deviceWithPairs.getDeviceUserAgent(), "DesktopApp") != -1) {
                                                    if (deviceWithPairs.getOsPair().matchAndSetNameFromGroup(DESKTOP_APP_MAC_PATTERN, deviceWithPairs.getDeviceUserAgent(), 1)) {
                                                        deviceWithPairs.getBrowserPair().setName(deviceWithPairs.getOsPair().getGroup(2) + " Desktop Application");
                                                        deviceWithPairs.getBrowserPair().setVersion(deviceWithPairs.getOsPair().getGroup(3));
                                                        break label315;
                                                    }

                                                    if (deviceWithPairs.getOsPair().matchAndSetNameFromGroup(DESKTOP_APP_WINDOWS_PATTERN, deviceWithPairs.getDeviceUserAgent(), 1)) {
                                                        deviceWithPairs.getBrowserPair().setName(deviceWithPairs.getOsPair().getGroup(2) + " Desktop Application");
                                                        deviceWithPairs.getBrowserPair().setVersion(deviceWithPairs.getOsPair().getGroup(3));
                                                        break label315;
                                                    }
                                                }

                                                if (deviceWithPairs.getOsPair().matchAndSetNameFromGroup(BAIDU_BROWSER_OS_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), 1)) {
                                                    deviceWithPairs.getBrowserPair().setName("Baidu Browser");
                                                    deviceWithPairs.getBrowserPair().setVersion(deviceWithPairs.getOsPair().getGroup(2));
                                                } else if (StringMatchUtils.containsAnyOf(deviceWithPairs.getDeviceUserAgent(), "360Browser", "360SE") && deviceWithPairs.getOsPair().matchAndSetNameFromGroup(BROWSER_360_OS_PATTERN, deviceWithPairs.getDeviceUserAgent(), 1)) {
                                                    deviceWithPairs.getBrowserPair().setName("360 Browser");
                                                } else if (StringMatchUtils.indexOf(deviceWithPairs.getDeviceUserAgent(), "MSIE") >= 0 && deviceWithPairs.getOsPair().matchAndSetNameFromGroup(MSIE_WINDOWS_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), 2)) {
                                                    deviceWithPairs.getBrowserPair().setName("IE");
                                                    deviceWithPairs.getBrowserPair().setVersion(deviceWithPairs.getOsPair().getGroup(1));
                                                } else {
                                                    label416:
                                                    {
                                                        if (StringMatchUtils.containsAnyOf(deviceWithPairs.getDeviceUserAgent(), "Trident", " Edge/")) {
                                                            if (deviceWithPairs.getOsPair().matchAndSetNameFromGroup(TRIDENT_WINDOWS_RV_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), 1)) {
                                                                deviceWithPairs.getBrowserPair().setName("IE");
                                                                deviceWithPairs.getBrowserPair().setVersion(deviceWithPairs.getOsPair().getGroup(2));
                                                                break label416;
                                                            }

                                                            if (deviceWithPairs.getOsPair().matchAndSetNameFromGroup(EDGE_WINDOWS_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), 1)) {
                                                                deviceWithPairs.getBrowserPair().setName("Edge");
                                                                deviceWithPairs.getBrowserPair().setVersion(deviceWithPairs.getOsPair().getGroup(2));
                                                                break label416;
                                                            }
                                                        }

                                                        if (StringMatchUtils.indexOf(deviceWithPairs.getDeviceUserAgent(), "YaBrowser") >= 0 && deviceWithPairs.getOsPair().matchAndSetNameFromGroup(YANDEX_BROWSER_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), 1)) {
                                                            deviceWithPairs.getBrowserPair().setName("Yandex browser");
                                                            deviceWithPairs.getBrowserPair().setVersion(deviceWithPairs.getOsPair().getGroup(2));
                                                        } else if (StringMatchUtils.indexOf(deviceWithPairs.getDeviceUserAgent(), "OPR") >= 0 && deviceWithPairs.getOsPair().matchAndSetNameFromGroup(OPERA_OPR_DESKTOP_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), 1)) {
                                                            deviceWithPairs.getBrowserPair().setName("Opera");
                                                            deviceWithPairs.getBrowserPair().setVersion(deviceWithPairs.getOsPair().getGroup(2));
                                                        } else if (StringMatchUtils.indexOf(deviceWithPairs.getDeviceUserAgent(), "Opera") >= 0 && deviceWithPairs.getOsPair().matchAndSetNameFromGroup(OPERA_CLASSIC_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), 2)) {
                                                            deviceWithPairs.getBrowserPair().setName("Opera");
                                                            deviceWithPairs.getBrowserPair().setVersion(deviceWithPairs.getOsPair().getGroup(1));
                                                            deviceWithPairs.getBrowserPair().matchAndSetGroup(OPERA_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), (String) null, 1);
                                                        } else {
                                                            label419:
                                                            {
                                                                if (deviceWithPairs.getDeviceUserAgent().contains("Linux x86_64") && deviceWithPairs.getDeviceUserAgent().contains("SamsungBrowser/")) {
                                                                    deviceWithPairs.getOsPair().setName("Linux");
                                                                    if (deviceWithPairs.getBrowserPair().matchAndSetGroup(SAMSUNG_BROWSER_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), "DeX Samsung Browser", 1)) {
                                                                        break label419;
                                                                    }
                                                                }

                                                                if (StringMatchUtils.indexOf(deviceWithPairs.getDeviceUserAgent(), "Chrome") >= 0) {
                                                                    if (deviceWithPairs.getOsPair().matchAndSetNameFromGroup(CHROME_MAC_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), 1)) {
                                                                        deviceWithPairs.getBrowserPair().setName("Chrome");
                                                                        deviceWithPairs.getBrowserPair().setVersion(deviceWithPairs.getOsPair().getGroup(2));
                                                                        break label419;
                                                                    }

                                                                    if (deviceWithPairs.getOsPair().matchAndSetNameFromGroup(CHROME_WINDOWS_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), 1)) {
                                                                        deviceWithPairs.getBrowserPair().setName("Chrome");
                                                                        deviceWithPairs.getBrowserPair().setVersion(deviceWithPairs.getOsPair().getGroup(2));
                                                                        break label419;
                                                                    }
                                                                }

                                                                if (deviceWithPairs.getDeviceUserAgent().contains("Epiphany/")) {
                                                                    deviceWithPairs.getOsPair().setName("Linux");
                                                                    if (deviceWithPairs.getBrowserPair().matchAndSetGroup(EPIPHANY_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), "Epiphany", 1)) {
                                                                        break label419;
                                                                    }
                                                                }

                                                                if (StringMatchUtils.indexOf(deviceWithPairs.getDeviceUserAgent(), "Safari") >= 0 && deviceWithPairs.getOsPair().matchAndSetNameFromGroup(SAFARI_DESKTOP_VERSION_PATTERN, deviceWithPairs.getCleanedDeviceUserAgent(), 1)) {
                                                                    if (StringMatchUtils.indexOf(deviceWithPairs.getDeviceUserAgent(), "CFNetwork") >= 0) {
                                                                        deviceWithPairs.getBrowserPair().setName("OSX App");
                                                                        deviceWithPairs.getBrowserPair().setVersion(deviceWithPairs.getOsPair().getGroup(2));
                                                                    } else {
                                                                        deviceWithPairs.getBrowserPair().setName("Safari");
                                                                        deviceWithPairs.getBrowserPair().setVersion(deviceWithPairs.getOsPair().getGroup(2));
                                                                    }
                                                                } else {
                                                                    label420:
                                                                    {
                                                                        if (deviceWithPairs.getDeviceUserAgent().contains("PaleMoon")) {
                                                                            if (deviceWithPairs.getOsPair().matchAndSetNameFromGroup(PALEMOON_WINDOWS_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), 1)) {
                                                                                deviceWithPairs.getBrowserPair().setName("PaleMoon");
                                                                                deviceWithPairs.getBrowserPair().setVersion(deviceWithPairs.getOsPair().getGroup(2));
                                                                                break label420;
                                                                            }

                                                                            if (deviceWithPairs.getOsPair().matchAndSetNameFromGroup(PALEMOON_LINUX_MAC_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), 1)) {
                                                                                deviceWithPairs.getBrowserPair().setName("PaleMoon");
                                                                                deviceWithPairs.getBrowserPair().setVersion(deviceWithPairs.getOsPair().getGroup(2));
                                                                                break label420;
                                                                            }
                                                                        }

                                                                        if (StringMatchUtils.indexOf(deviceWithPairs.getDeviceUserAgent(), "Firefox") >= 0) {
                                                                            if (deviceWithPairs.getOsPair().matchAndSetNameFromGroup(FIREFOX_WINDOWS_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), 1)) {
                                                                                deviceWithPairs.getBrowserPair().setName("Firefox");
                                                                                deviceWithPairs.getBrowserPair().setVersion(deviceWithPairs.getOsPair().getGroup(2));
                                                                                break label420;
                                                                            }

                                                                            if (deviceWithPairs.getOsPair().matchAndSetNameFromGroup(FIREFOX_LINUX_MAC_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), 1)) {
                                                                                deviceWithPairs.getBrowserPair().setName("Firefox");
                                                                                deviceWithPairs.getBrowserPair().setVersion(deviceWithPairs.getOsPair().getGroup(2));
                                                                                break label420;
                                                                            }

                                                                            if (deviceWithPairs.getBrowserUserAgent().contains("(X11; ")) {
                                                                                deviceWithPairs.getOsPair().setName("Linux");
                                                                                deviceWithPairs.getBrowserPair().matchAndSetGroup(FIREFOX_SIMPLE_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), "Firefox", 1);
                                                                                break label420;
                                                                            }
                                                                        }

                                                                        if (StringMatchUtils.indexOf(deviceWithPairs.getBrowserUserAgent(), "CFNetwork") >= 0) {
                                                                            deviceWithPairs.getOsPair().setName(device.getCapability("device_os"));
                                                                            deviceWithPairs.getOsPair().setVersion(device.getCapability("device_os_version"));
                                                                            deviceWithPairs.getBrowserPair().setName("CFNetwork App");
                                                                            deviceWithPairs.getBrowserPair().setVersion(device.getCapability("mobile_browser_version"));
                                                                        } else {
                                                                            String browserUserAgent;
                                                                            if (!deviceWithPairs.getOsPair().matchAndSetNameFromGroup(WINDOWS_OS_NAME_PATTERN, deviceWithPairs.getDeviceUserAgent(), 1) && !deviceWithPairs.getOsPair().matchAndSetNameFromGroup(MAC_OS_NAME_PATTERN, deviceWithPairs.getDeviceUserAgent(), 1) && (browserUserAgent = deviceWithPairs.getBrowserUserAgent()) != null && (browserUserAgent.contains("(X11; ") || browserUserAgent.contains("Linux x86_64"))) {
                                                                                deviceWithPairs.getOsPair().setName("Linux");
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        deviceWithPairs.getOsPair().matchAndSet(BLACKBERRY_PATTERN, deviceWithPairs.getDeviceUserAgent(), "BlackBerry", (String) null);
                        deviceWithPairs.getOsPair().matchAndSetGroup(BLACKBERRY_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), (String) null, 1);
                        if (deviceWithPairs.getOsPair().matchAndSetGroup(BLACKBERRY_UC_BROWSER_PATTERN, deviceWithPairs.getDeviceUserAgent(), (String) null, 1)) {
                            deviceWithPairs.getBrowserPair().setName("UC Web");
                            deviceWithPairs.getBrowserPair().setVersion(deviceWithPairs.getOsPair().getGroup(2));
                        } else if (deviceWithPairs.getOsPair().matchAndSetGroup(UCWEB_UCBROWSER_COMBINED_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), (String) null, 1)) {
                            deviceWithPairs.getBrowserPair().setName("UC Web");
                            deviceWithPairs.getBrowserPair().setVersion(deviceWithPairs.getOsPair().getGroup(2));
                        } else if (!deviceWithPairs.getBrowserPair().matchAndSetGroup(OPERA_MINI_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Opera Mini", 1)) {
                            if (deviceWithPairs.getOsPair().matchAndSetGroup(BLACKBERRY_BROWSER_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), (String) null, 1)) {
                                deviceWithPairs.getBrowserPair().setName("BlackBerry Browser");
                                deviceWithPairs.getBrowserPair().setVersion(deviceWithPairs.getOsPair().getVersion());
                            } else if (deviceWithPairs.getOsPair().matchAndSetGroup(BB10_BROWSER_VERSION_PATTERN, deviceWithPairs.getDeviceUserAgent(), (String) null, 1)) {
                                deviceWithPairs.getBrowserPair().setName("BlackBerry Webkit Browser");
                                deviceWithPairs.getBrowserPair().setVersion(deviceWithPairs.getOsPair().getVersion());
                            } else {
                                deviceWithPairs.getBrowserPair().setName("BlackBerry Browser");
                                deviceWithPairs.getBrowserPair().setVersion(deviceWithPairs.getOsPair().getVersion());
                            }
                        }
                    }
                } else {
                    deviceWithPairs.getOsPair().matchAndSet(SYMBIAN3_PATTERN, deviceWithPairs.getDeviceUserAgent(), "Symbian", "^3");
                    if (!deviceWithPairs.getBrowserPair().matchAndSetGroup(NOKIA_BROWSER_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Symbian S60 Browser", 1) && !deviceWithPairs.getBrowserPair().matchAndSetGroup(OPERA_MOBI_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Opera Mobi", 1) && !deviceWithPairs.getBrowserPair().matchAndSetGroup(SYMBIAN_UCWEB_UCBROWSER_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "UC Web Browser on Symbian", 1)) {
                        deviceWithPairs.getBrowserPair().setName("Symbian S60 Browser");
                    }
                }
            }
        } else if (deviceWithPairs.getBrowserPair().matchAndSetNameAndGroup(MOBILE_APP_NAME_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), 2)) {
            deviceWithPairs.getBrowserPair().setName(deviceWithPairs.getBrowserPair().getName() + " Mobile Application");
        } else if (!deviceWithPairs.getBrowserPair().matchAndSetGroup(UC_BROWSER_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "UC Browser", 1) && !deviceWithPairs.getBrowserPair().matchAndSetGroup(IE_MOBILE_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "IE Mobile", 1) && !deviceWithPairs.getBrowserPair().matchAndSetGroup(EDGE_VERSION_PATTERN, deviceWithPairs.getBrowserUserAgent(), "Edge Mobile", 1)) {
        }

        virtualCapabilityDevice.normalizeOS();
        virtualCapabilityDevice.normalizeBrowser();
        return virtualCapabilityDevice;
    }
}
