package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

final class PantechMatcher extends MatcherBase {
    public PantechMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsDesktopBrowser()
                && StringMatchUtils.startsWithAnyOf(cleanedDeviceUserAgent, "Pantech", "PT-", "PANTECH", "PG-");
    }

    @Override
    public String getMatcherName() {
        return "PantechMatcher";
    }

    @Override
    public String getBucketMatcherName() {
        return "Pantech";
    }
}
