package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

/**
 * Matcher implementation for identifying Sharp devices and browsers.
 */

final class SharpMatcher extends MatcherBase {
    @Override
    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsDesktopBrowser() && cleanedDeviceUserAgent != null && cleanedDeviceUserAgent.regionMatches(true, 0, "sharp", 0, 5);
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "SharpMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "Sharp";
    }
}
