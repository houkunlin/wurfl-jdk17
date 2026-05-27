package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class KonquerorMatcher extends MatcherBase {
    @Override
    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsMobileBrowser() && request.getCleanedDeviceUserAgent().contains("Konqueror");
    }

    @Override
    public String getMatcherName() {
        return "KonquerorMatcher";
    }

    @Override
    public String getBucketMatcherName() {
        return "Konqueror";
    }
}
