package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Matcher implementation for identifying Net Front On Android devices and browsers.
 */

final class NetFrontOnAndroidMatcher extends MatcherBase {
    private static final String GENERIC_ANDROID_VER2_0_NETFRONT_LIFEBROWSER = "generic_android_ver2_0_netfrontlifebrowser";
    private static final Map<String, String> ANDROID_VERSION_TO_DEVICE_ID;

    static {
        ANDROID_VERSION_TO_DEVICE_ID = new HashMap<>();
        ANDROID_VERSION_TO_DEVICE_ID.put("2.1", "generic_android_ver2_1_netfrontlifebrowser");
        ANDROID_VERSION_TO_DEVICE_ID.put("2.2", "generic_android_ver2_2_netfrontlifebrowser");
        ANDROID_VERSION_TO_DEVICE_ID.put("2.3", "generic_android_ver2_3_netfrontlifebrowser");
    }

    public NetFrontOnAndroidMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * Returns the require devic eds.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(GENERIC_ANDROID_VER2_0_NETFRONT_LIFEBROWSER);
        return requiredDeviceIds;
    }

    @Override
/**
 * Returns whether this ca nandle.
 */

    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsDesktopBrowser() && StringMatchUtils.containsAllOf(cleanedDeviceUserAgent, "Android", "NetFrontLifeBrowser/2.2");
    }

    @Override
/**
 * Ri satch.
 */

    protected String risMatch(String userAgent) {
        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, StringMatchUtils.indexOfOrLength(userAgent, "NetFrontLifeBrowser/2.2"));
    }

    @Override
/**
 * Appl yecover yatch.
 */

    protected String applyRecoveryMatch(WURFLRequest request) {
        String androidVersion = UserAgentUtils.getAndroidVersion(request.getNormalizedDeviceUserAgent(), true);
        String deviceId = ANDROID_VERSION_TO_DEVICE_ID.get(androidVersion);
        return deviceId != null ? deviceId : GENERIC_ANDROID_VER2_0_NETFRONT_LIFEBROWSER;
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "NetFrontOnAndroidMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "NetFrontOnAndroid";
    }
}
