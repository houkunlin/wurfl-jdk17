package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

/**
 * Matcher implementation for identifying Ben Q devices and browsers.
 */

final class BenQMatcher extends MatcherBase {
    @Override
    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsDesktopBrowser()
                && cleanedDeviceUserAgent != null
                && cleanedDeviceUserAgent.regionMatches(true, 0, "benq", 0, 4);
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "BenQMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "BenQ";
    }
}
