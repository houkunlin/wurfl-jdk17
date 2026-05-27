package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class GrundigMatcher extends MatcherBase {
    @Override
    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsDesktopBrowser()
                && cleanedDeviceUserAgent != null
                && cleanedDeviceUserAgent.regionMatches(true, 0, "grundig", 0, 7);
    }

    @Override
    public String getMatcherName() {
        return "GrundigMatcher";
    }

    @Override
    public String getBucketMatcherName() {
        return "Grundig";
    }
}
