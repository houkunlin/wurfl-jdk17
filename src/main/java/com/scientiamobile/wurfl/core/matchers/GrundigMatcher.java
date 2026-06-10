package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

/**
 * Matcher implementation for identifying Grundig devices and browsers.
 */

final class GrundigMatcher extends MatcherBase {
    @Override
    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsDesktopBrowser()
                && cleanedDeviceUserAgent != null
                && cleanedDeviceUserAgent.regionMatches(true, 0, "grundig", 0, 7);
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "GrundigMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "Grundig";
    }
}
