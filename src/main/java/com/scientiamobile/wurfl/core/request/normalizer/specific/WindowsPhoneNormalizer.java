package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Normalizes User-Agent strings for Windows Phone.
 */

public class WindowsPhoneNormalizer implements UserAgentNormalizer {
    private static final Pattern WINDOWS_PHONE_AD_CLIENT_MODEL_PATTERN = Pattern.compile("Windows ?Phone ?Ad ?Client/[0-9\\.]+ ?\\(.+; ?Windows ?Phone(?: ?OS)? ?[0-9\\.]+; ?([^;\\)]+(; ?[^;\\)]+)?)");
    private static final Pattern WINDOWS_PHONE_APP_UA_PATTERN = Pattern.compile("^[^/]+/[0-9\\.-_]+ Windows Phone/([\\d\\.]+) (.+)$");

    @Override
/**
 * Normalizes the given User-Agent string.
 * @param userAgent the raw User-Agent string
 * @return the normalized User-Agent string
 */

    public String normalize(String userAgent) {
        String windowsPhoneModel = null;
        String windowsPhoneVersion = null;
        Matcher windowsPhoneAppUaMatcher;
        windowsPhoneAppUaMatcher = WINDOWS_PHONE_APP_UA_PATTERN.matcher(userAgent);
        if (windowsPhoneAppUaMatcher.find()) {
            userAgent = "Mozilla/5.0 (Mobile; Windows Phone " + windowsPhoneAppUaMatcher.group(1) + "; Android 4.0; ARM; Trident/7.0; Touch; rv:11.0; IEMobile/11.0; " + windowsPhoneAppUaMatcher.group(2) + ") like iPhone OS 7_0_3 Mac OS X AppleWebKit/537 (KHTML, like Gecko) Mobile Safari/537 " + userAgent;
        }

        if (!StringMatchUtils.containsAnyOf(userAgent, "WPDesktop", "ZuneWP7") && !StringMatchUtils.containsAllOf(userAgent, "Mozilla/5.0 (Windows NT ", " ARM;", " Edge/")) {
            if (UserAgentUtils.isWindowsPhoneAdClient(userAgent)) {
                windowsPhoneModel = UserAgentUtils.cleanAndReplaceWindowsPhoneModel(userAgent, WINDOWS_PHONE_AD_CLIENT_MODEL_PATTERN);
                windowsPhoneVersion = UserAgentUtils.getWindowsPhoneVersion(userAgent);
            } else if (!userAgent.contains("NativeHost")) {
                windowsPhoneModel = UserAgentUtils.getWindowsPhoneModel(userAgent);
                windowsPhoneVersion = UserAgentUtils.getWindowsPhoneVersion(userAgent);
            }
        } else {
            windowsPhoneModel = UserAgentUtils.getWindowsPhoneDesktopModel(userAgent);
            windowsPhoneVersion = UserAgentUtils.getWindowsPhoneDesktopVersion(userAgent);
        }

        return windowsPhoneModel != null && windowsPhoneVersion != null ? "WP" + windowsPhoneVersion + " " + windowsPhoneModel + "---" + userAgent : userAgent;
    }
}
