package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.util.Locale;

/**
 * Matcher implementation for identifying Alcatel devices and browsers.
 */

final class AlcatelMatcher extends MatcherBase {
    @Override
    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().toLowerCase(Locale.US).startsWith("alcatel");
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "AlcatelMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "Alcatel";
    }
}
