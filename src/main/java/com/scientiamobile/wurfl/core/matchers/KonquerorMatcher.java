package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

/**
 * Matcher implementation for identifying Konqueror devices and browsers.
 */

final class KonquerorMatcher extends MatcherBase {
    @Override
    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsMobileBrowser() && request.getCleanedDeviceUserAgent().contains("Konqueror");
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "KonquerorMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "Konqueror";
    }
}
