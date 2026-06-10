package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

/**
 * Matcher implementation for identifying Kyocera devices and browsers.
 */

final class KyoceraMatcher extends MatcherBase {
    @Override
    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && StringMatchUtils.startsWithAnyOf(request.getCleanedDeviceUserAgent(), "kyocera", "KWC-", "QC-");
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "KyoceraMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "Kyocera";
    }
}
