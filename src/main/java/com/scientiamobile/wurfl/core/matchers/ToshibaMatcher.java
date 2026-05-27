package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class ToshibaMatcher extends MatcherBase {
    @Override
    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().startsWith("Toshiba");
    }

    @Override
    public String getMatcherName() {
        return "ToshibaMatcher";
    }

    @Override
    public String getBucketMatcherName() {
        return "Toshiba";
    }
}
