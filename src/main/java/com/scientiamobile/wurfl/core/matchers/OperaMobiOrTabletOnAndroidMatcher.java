package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Matcher implementation for identifying Opera Mobi Or Tablet On Android devices and browsers.
 */

final class OperaMobiOrTabletOnAndroidMatcher extends MatcherBase {
    private static final String GENERIC_ANDROID_VER2_0_OPERA_MOBI = "generic_android_ver2_0_opera_mobi";
    private static final String GENERIC_ANDROID_VER2_1_OPERA_TABLET = "generic_android_ver2_1_opera_tablet";
    private static final Set<String> SUPPORTED_ANDROID_OPERA_DEVICE_IDS;

    static {
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS = new HashSet<>();
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver1_5_opera_mobi");
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver1_6_opera_mobi");
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add(GENERIC_ANDROID_VER2_0_OPERA_MOBI);
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver2_1_opera_mobi");
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver2_2_opera_mobi");
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver2_3_opera_mobi");
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver4_0_opera_mobi");
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver4_1_opera_mobi");
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver4_2_opera_mobi");
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add(GENERIC_ANDROID_VER2_1_OPERA_TABLET);
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver2_2_opera_tablet");
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver2_3_opera_tablet");
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver3_0_opera_tablet");
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver3_1_opera_tablet");
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver3_2_opera_tablet");
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver4_0_opera_tablet");
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver4_1_opera_tablet");
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver4_2_opera_tablet");
    }

    public OperaMobiOrTabletOnAndroidMatcher(UserAgentNormalizer userAgentNormalizer, WURFLModel wurflModel) {
        super(userAgentNormalizer, wurflModel);
    }

    @Override
/**
 * Returns the require devic eds.
 */

    protected Set<String> getRequiredDeviceIds() {
        return new HashSet<>(SUPPORTED_ANDROID_OPERA_DEVICE_IDS);
    }

    @Override
/**
 * Returns whether this ca nandle.
 */

    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsDesktopBrowser() && cleanedDeviceUserAgent.contains("Android") && StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, "Opera Tablet", "Opera Mobi");
    }

    @Override
/**
 * Ri satch.
 */

    protected String risMatch(String userAgent) {
        int matchLength;
        matchLength = (matchLength = userAgent.indexOf("---")) == -1 ? userAgent.length() : matchLength + 3;
        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
    }

    @Override
/**
 * Appl yecover yatch.
 */

    protected String applyRecoveryMatch(WURFLRequest request) {
        String normalizedUserAgent = request.getNormalizedDeviceUserAgent();
        boolean isOperaTablet = normalizedUserAgent.contains("Opera Tablet");
        String androidVersion = UserAgentUtils.getAndroidVersion(normalizedUserAgent, true);
        String deviceId = "generic_android_ver" + androidVersion.replaceAll("\\.", "_") + "_opera_" + (isOperaTablet ? "tablet" : "mobi");
        if (SUPPORTED_ANDROID_OPERA_DEVICE_IDS.contains(deviceId)) {
            return deviceId;
        } else {
            return isOperaTablet ? GENERIC_ANDROID_VER2_1_OPERA_TABLET : GENERIC_ANDROID_VER2_0_OPERA_MOBI;
        }
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "OperaMobiOrTabletOnAndroidMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "OperaMobiOrTabletOnAndroid";
    }
}
