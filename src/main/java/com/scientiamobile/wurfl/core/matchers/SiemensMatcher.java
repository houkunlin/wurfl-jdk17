package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

/**
 * Matcher implementation for identifying Siemens devices and browsers.
 */

final class SiemensMatcher extends MatcherBase {
    @Override
    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().startsWith("SIE-");
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "SiemensMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "Siemens";
    }
}
