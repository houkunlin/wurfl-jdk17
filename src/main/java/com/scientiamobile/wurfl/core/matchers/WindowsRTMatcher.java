package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Matcher implementation for identifying Windows RT devices and browsers.
 */

final class WindowsRTMatcher extends AbstractMatcher {
    private static final String WINDOWS_8_RT_VER1_SUBOS81 = "windows_8_rt_ver1_subos81";
    private static final String GENERIC_WINDOWS_8_RT = "generic_windows_8_rt";

    public WindowsRTMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * Returns the require devic eds.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(GENERIC_WINDOWS_8_RT);
        requiredDeviceIds.add(WINDOWS_8_RT_VER1_SUBOS81);
        return requiredDeviceIds;
    }

    @Override
/**
 * Returns whether this ca nandle.
 */

    public boolean canHandle(WURFLRequest request) {
        return StringMatchUtils.containsAllOf(request.getCleanedDeviceUserAgent(), "Windows NT ", " ARM;", "Trident/");
    }

    @Override
/**
 * Ri satch.
 */

    protected String risMatch(String userAgent) {
        if (userAgent.contains("like Gecko")) {
            int geckoIndex;
            geckoIndex = userAgent.indexOf(" Gecko");
            if (geckoIndex >= 0) {
                return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, geckoIndex + 6);
            }
        } else {
            int armIndex;
            armIndex = userAgent.indexOf(" ARM;");
            if (armIndex >= 0) {
                return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, armIndex + 5);
            }
        }

        return null;
    }

    @Override
/**
 * Appl yecover yatch.
 */

    protected String applyRecoveryMatch(WURFLRequest request) {
        return request.getNormalizedDeviceUserAgent().contains("like Gecko") ? WINDOWS_8_RT_VER1_SUBOS81 : GENERIC_WINDOWS_8_RT;
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "WindowsRTMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "WindowsRT";
    }
}
