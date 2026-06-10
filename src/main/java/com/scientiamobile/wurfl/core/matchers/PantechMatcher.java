package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

/**
 * Matcher implementation for identifying Pantech devices and browsers.
 */

final class PantechMatcher extends MatcherBase {
    public PantechMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * Returns whether this ca nandle.
 */

    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsDesktopBrowser()
                && StringMatchUtils.startsWithAnyOf(cleanedDeviceUserAgent, "Pantech", "PT-", "PANTECH", "PG-");
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "PantechMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "Pantech";
    }
}
