package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;

/**
 * Matcher implementation for identifying Vodafone devices and browsers.
 */

final class VodafoneMatcher extends MatcherBase {
    public VodafoneMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * Returns whether this ca nandle.
 */

    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().startsWith("Vodafone");
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "VodafoneMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "Vodafone";
    }
}
