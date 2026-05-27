package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.VirtualCapabilityDevice;
import com.scientiamobile.wurfl.core.VirtualCapabilityUserAgentTool;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class AbstractVirtualCapabilityEvaluator implements VirtualCapabilityEvaluator, Serializable {
    protected static final Pattern ANDROID_WEBKIT_KHTML_PATTERN = Pattern.compile("Mozilla/5.0 \\(Linux;( U;)? Android.*AppleWebKit.*\\(KHTML, like Gecko\\)");
    protected static final Pattern ANDROID_LEGACY_SAFARI_UA_PATTERN;
    protected static final Pattern CHROME_MAJOR_VERSION_PATTERN;
    protected static final Pattern ANDROID_LEGACY_VERSION_PATTERN;
    protected static final List<String> APP_INDICATOR_PATTERNS;
    static final Set<String> ANDROID_BROWSER_PACKAGE_NAMES;
    static final Set<String> NON_APP_BROWSER_KEYWORDS;
    private static final long serialVersionUID = 8192401578396133213L;
    private static final Pattern MAJOR_MINOR_VERSION_PATTERN;
    private static Set<String> ANDROID_REQUESTED_WITH_APP_PACKAGES;
    private static List<String> BOT_EXCLUSION_KEYWORDS;

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
        NON_APP_BROWSER_KEYWORDS.add("CriOS");
        NON_APP_BROWSER_KEYWORDS.add("Firefox");
    }

    protected static boolean isIosNonSafari(String deviceOs, String userAgent) {
        return "iOS".equals(deviceOs) && !userAgent.contains("Safari");
    }

    protected static boolean isMacOsNonSafari(Device device, String userAgent, WURFLRequest request) {
        VirtualCapabilityDevice virtualCapabilityDevice = VirtualCapabilityUserAgentTool.getInstance().assignProperties(request, device);
        return "Mac OS X".equals(virtualCapabilityDevice.getOsPairName()) && !userAgent.contains("Safari");
    }

    protected static boolean isRequestedWithAppPackage(String expectedOs, String deviceOs, WURFLRequest request) {
        return isRequestedWithAppPackage(expectedOs, deviceOs, request.getHeader("X-Requested-With"));
    }

    protected static boolean isRequestedWithAppPackage(String expectedOs, String deviceOs, String requestedWith) {
        return expectedOs.equals(deviceOs) && StringUtils.isNotEmpty(requestedWith) && ANDROID_REQUESTED_WITH_APP_PACKAGES.contains(requestedWith);
    }

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

    protected static boolean isSmartphone(Device device) {
        int resolutionWidth;
        try {
            resolutionWidth = Integer.parseInt(device.getCapability("resolution_width"));
        } catch (NumberFormatException e) {
            return false;
        }

        if ("false".equals(device.getCapability("is_wireless_device"))) {
            return false;
        } else if ("true".equals(device.getCapability("is_tablet"))) {
            return false;
        } else if ("false".equals(device.getCapability("can_assign_phone_number"))) {
            return false;
        } else if (!"touchscreen".equals(device.getCapability("pointing_method"))) {
            return false;
        } else if (resolutionWidth < 320) {
            return false;
        } else {
            String deviceOsVersion = device.getCapability("device_os_version");
            Matcher versionMatcher = MAJOR_MINOR_VERSION_PATTERN.matcher(deviceOsVersion);
            float version = 0.0F;
            boolean versionParsed;
            versionParsed = versionMatcher.matches();
            if (versionParsed) {
                try {
                    version = Float.parseFloat(versionMatcher.group(1));
                } catch (NumberFormatException e) {
                    versionParsed = false;
                }
            }

            String deviceOs = device.getCapability("device_os");
            if ("iOS".equals(deviceOs)) {
                return versionParsed && version >= 3.0F;
            } else if ("Android".equals(deviceOs)) {
                return versionParsed && version >= 2.2F;
            } else if ("Windows Phone OS".equals(deviceOs)) {
                return true;
            } else if ("RIM OS".equals(deviceOs)) {
                return versionParsed && (double) version >= (double) 7.0F;
            } else if ("webOS".equals(deviceOs)) {
                return true;
            } else if ("MeeGo".equals(deviceOs)) {
                return true;
            } else if ("Bada OS".equals(deviceOs)) {
                return versionParsed && (double) version >= (double) 2.0F;
            } else {
                return false;
            }
        }
    }
}
