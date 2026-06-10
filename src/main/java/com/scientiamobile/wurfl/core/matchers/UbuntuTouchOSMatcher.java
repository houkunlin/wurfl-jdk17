package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Matcher implementation for identifying Ubuntu Touch OS devices and browsers.
 */

public class UbuntuTouchOSMatcher extends MatcherBase {
    private static final String GENERIC_UBUNTU_TOUCH_OS = "generic_ubuntu_touch_os";
    private static final String GENERIC_UBUNTU_TOUCH_OS_TABLET = "generic_ubuntu_touch_os_tablet";

    public UbuntuTouchOSMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * Returns the require devic eds.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(GENERIC_UBUNTU_TOUCH_OS);
        requiredDeviceIds.add(GENERIC_UBUNTU_TOUCH_OS_TABLET);
        return requiredDeviceIds;
    }

    @Override
/**
 * Returns whether this ca nandle.
 */

    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return cleanedDeviceUserAgent.contains("Ubuntu") && StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, "Mobile", "Tablet");
    }

    @Override
/**
 * Ri satch.
 */

    protected String risMatch(String userAgent) {
        int matchLength;
        matchLength = userAgent.indexOf("like Android");
        if (matchLength >= 0) {
            matchLength += 12;
            matchLength = userAgent.indexOf("WebKit/");
        } else if (matchLength >= 0) {
            matchLength += 7;
        }

        return matchLength >= 0 ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength) : null;
    }

    @Override
/**
 * Appl yecover yatch.
 */

    protected String applyRecoveryMatch(WURFLRequest request) {
        return request.getNormalizedDeviceUserAgent().contains("Tablet") ? GENERIC_UBUNTU_TOUCH_OS_TABLET : GENERIC_UBUNTU_TOUCH_OS;
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "UbuntuTouchOSMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "UbuntuTouchOS";
    }
}
