package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;

import java.util.HashSet;
import java.util.Set;

/**
 * Matcher implementation for identifying Reksio devices and browsers.
 */

final class ReksioMatcher extends MatcherBase {
    private static final String REKSIO_DEVICE_ID = "generic_reksio";

    public ReksioMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * Returns the require devic eds.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(REKSIO_DEVICE_ID);
        return requiredDeviceIds;
    }

    @Override
/**
 * Returns whether this ca nandle.
 */

    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().startsWith("Reksio");
    }

    @Override
/**
 * Appl yonclusiv eatch.
 */

    protected String applyConclusiveMatch(WURFLRequest request) {
        return REKSIO_DEVICE_ID;
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "ReksioMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "Reksio";
    }
}
