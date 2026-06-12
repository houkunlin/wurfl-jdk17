package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;
import java.util.regex.Matcher;

/**
 * 判断请求是否来自 App 内嵌 WebView 的虚拟能力评估器。
 * <p>与 {@link IsApp} 类似但判断条件更为严格，
 * 旨在识别那些在原生应用中通过 WebView 加载页面的请求。
 * 检测策略包括排除已知浏览器关键词、检测 {@code ; wv) } 标识、
 * 检查 {@code X-Requested-With} 头、分析 Chrome 版本以及
 * 识别旧版 Android WebView 的 User-Agent 模式。</p>
 */

public class IsAppWebview extends AbstractVirtualCapabilityEvaluator implements VirtualCapabilityEvaluator, Serializable {
    @Serial
    private static final long serialVersionUID = 165298984131843694L;

    @Override
    public String eval(Device device, WURFLRequest request) {
        String userAgent = request.isUrlEncoded() ? request.getCleanedDeviceUserAgent() : request.getOriginalUserAgent();

        // Fast path: exclude known non-app browsers
        for (String keyword : NON_APP_BROWSER_KEYWORDS) {
            if (userAgent.contains(keyword)) {
                return "false";
            }
        }

        String deviceOs = device.getCapability("device_os");
        boolean isAndroid = "Android".equals(deviceOs);

        // Android-specific WebView signatures
        if (isAndroid && userAgent.contains("; wv) ")) {
            return "true";
        }
        if (isAndroid && userAgent.contains("Chrome") && !userAgent.contains("Version")) {
            return "false";
        }

        // iOS / macOS non-Safari → likely app WebView
        if (isIosNonSafari(deviceOs, userAgent) || isMacOsNonSafari(device, userAgent, request)) {
            return "true";
        }

        // Check X-Requested-With header for known app/browser packages
        String requestedWith = request.getHeader("X-Requested-With");
        if (isRequestedWithAppPackage("Android", deviceOs, requestedWith)) {
            return "true";
        }
        if (ANDROID_BROWSER_PACKAGE_NAMES.contains(requestedWith)) {
            return "false";
        }

        // Analyze Android WebKit User-Agent for legacy WebView detection
        if (!ANDROID_WEBKIT_KHTML_PATTERN.matcher(request.getDeviceUserAgent()).find()) {
            return "false";
        }

        Matcher androidUaPrefixMatcher = IsApp.ANDROID_UA_PREFIX_PATTERN.matcher(userAgent);
        Matcher androidSafariSuffixMatcher = IsApp.ANDROID_SAFARI_SUFFIX_PATTERN.matcher(userAgent);
        if (androidUaPrefixMatcher.find() || androidSafariSuffixMatcher.find()) {
            // Early Chrome versions (< 3) are not WebViews
            if (isChromeMajorVersionBelow3(userAgent)) {
                return "false";
            }
            return "true";
        }

        // Legacy Android 1.x-4.x without standard Safari UA → likely WebView
        if (ANDROID_LEGACY_VERSION_PATTERN.matcher(userAgent).find()
                && !ANDROID_LEGACY_SAFARI_UA_PATTERN.matcher(userAgent).matches()) {
            return "true";
        }

        return "false";
    }

    /**
     * 判断 Chrome 主版本号是否低于 3（对应原始逻辑中单/双位数版本且首位字符 &lt; '3'）。
     * <p>用于识别旧版 Android WebView：Chrome 3-9 及 30+ 视为 WebView 特征，
     * Chrome 0-2 及 10-29 视为非 WebView。</p>
     */
    private static boolean isChromeMajorVersionBelow3(String userAgent) {
        Matcher chromeVersionMatcher = CHROME_MAJOR_VERSION_PATTERN.matcher(userAgent);
        if (!chromeVersionMatcher.find()) {
            return false;
        }
        // 去除前导零，保留至少一位数字
        String versionStr = chromeVersionMatcher.group(1).replaceFirst("^0+(?!$)", "");
        // 仅处理不超过两位数的版本号
        if (versionStr.length() > 2) {
            return false;
        }
        return versionStr.charAt(0) < '3';
    }

    @Override
    public String getHandledVirtualCapabilityName() {
        return "is_app_webview";
    }
}
