package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;

import java.util.HashSet;
import java.util.Set;

final class ReksioMatcher extends MatcherBase {
    private static final String REKSIO_DEVICE_ID = "generic_reksio";

    public ReksioMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(REKSIO_DEVICE_ID);
        return requiredDeviceIds;
    }

    @Override
    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().startsWith("Reksio");
    }

    @Override
    protected String applyConclusiveMatch(WURFLRequest request) {
        return REKSIO_DEVICE_ID;
    }

    @Override
    public String getMatcherName() {
        return "ReksioMatcher";
    }

    @Override
    public String getBucketMatcherName() {
        return "Reksio";
    }
}
