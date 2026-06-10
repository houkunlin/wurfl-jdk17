package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

/**
 * Matcher implementation for identifying Qtek devices and browsers.
 */

final class QtekMatcher extends MatcherBase {
    @Override
    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().startsWith("Qtek");
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "QtekMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "Qtek";
    }
}
