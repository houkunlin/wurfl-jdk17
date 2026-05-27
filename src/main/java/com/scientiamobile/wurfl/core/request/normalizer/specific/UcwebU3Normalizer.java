package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UcwebU3Normalizer implements UserAgentNormalizer {
    public static final Pattern IPHONE = Pattern.compile("iPhone OS (\\d+)_(\\d+)(?:_\\d+)* like");
    public static final Pattern IPAD = Pattern.compile("CPU OS (\\d+)_(\\d+)?.+like Mac.+; iPad([0-9,]+)\\) AppleWebKit");

    @Override
    public String normalize(String userAgent) {
        String ucBrowserVersion;
        ucBrowserVersion = UserAgentUtils.getUcBrowserVersion(userAgent, false);
        if (ucBrowserVersion == null) {
            return userAgent;
        } else {
            String normalizedPrefix = null;
            if (userAgent.contains("Windows Phone")) {
                String windowsPhoneVersion = UserAgentUtils.getWindowsPhoneVersion(userAgent);
                String windowsPhoneModel;
                windowsPhoneModel = UserAgentUtils.getWindowsPhoneModel(userAgent);
                if (windowsPhoneModel != null && windowsPhoneVersion != null) {
                    normalizedPrefix = windowsPhoneVersion + " U3WP " + ucBrowserVersion + " " + windowsPhoneModel + "---";
                }
            } else if (userAgent.contains("Android")) {
                String androidModel = UserAgentUtils.getAndroidModel(userAgent);
                String androidVersion = UserAgentUtils.getAndroidVersion(userAgent, false);
                if (androidModel != null && androidVersion != null) {
                    normalizedPrefix = androidVersion + " U3Android " + ucBrowserVersion + " " + androidModel + "---";
                }
            } else if (userAgent.contains("iPhone;")) {
                Matcher matcher;
                matcher = IPHONE.matcher(userAgent);
                if (matcher.find()) {
                    String iosVersion = matcher.group(1) + "." + (matcher.group(2) == null ? "" : matcher.group(2));
                    normalizedPrefix = iosVersion + " U3iPhone " + ucBrowserVersion + "---";
                }
            } else {
                Matcher matcher;
                matcher = IPAD.matcher(userAgent);
                if (userAgent.contains("iPad") && matcher.find()) {
                    String iosMajorVersion = matcher.group(1);
                    String iosMinorVersion = matcher.group(2);
                    String iosVersion = iosMajorVersion + "." + (iosMinorVersion == null ? "" : iosMinorVersion);
                    String ipadModel = matcher.group(3);
                    normalizedPrefix = iosVersion + " U3iPad " + ucBrowserVersion + " " + ipadModel + "---";
                }
            }

            return normalizedPrefix == null ? userAgent : normalizedPrefix + userAgent;
        }
    }
}
