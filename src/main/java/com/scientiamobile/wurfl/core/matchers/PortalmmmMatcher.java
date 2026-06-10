package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

/**
 * Matcher implementation for identifying Portalmmm devices and browsers.
 */

final class PortalmmmMatcher extends MatcherBase {
    @Override
    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().startsWith("portalmmm");
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "PortalmmmMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "Portalmmm";
    }

    @Override
/**
 * Ri satch.
 */

    protected String risMatch(String normalizedUserAgent) {
        return null;
    }
}
