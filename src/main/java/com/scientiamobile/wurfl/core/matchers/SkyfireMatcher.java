package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Matcher implementation for identifying Skyfire devices and browsers.
 */

final class SkyfireMatcher extends MatcherBase {
    private static final String GENERIC_SKYFIRE_VERSION2 = "generic_skyfire_version2";
    private static final String GENERIC_SKYFIRE_VERSION1 = "generic_skyfire_version1";

    public SkyfireMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * Returns the require devic eds.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(GENERIC_SKYFIRE_VERSION1);
        requiredDeviceIds.add(GENERIC_SKYFIRE_VERSION2);
        return requiredDeviceIds;
    }

    @Override
/**
 * Returns whether this ca nandle.
 */

    public boolean canHandle(WURFLRequest request) {
        return request.getCleanedDeviceUserAgent().contains("Skyfire");
    }

    @Override
/**
 * Ri satch.
 */

    protected String risMatch(String userAgent) {
        int skyfireIndex = StringMatchUtils.indexOf(userAgent, "Skyfire");
        int matchLength = StringMatchUtils.indexOfOrLength(userAgent, ".", skyfireIndex);
        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
    }

    @Override
/**
 * Appl yecover yatch.
 */

    protected String applyRecoveryMatch(WURFLRequest request) {
        return request.getNormalizedDeviceUserAgent().contains("Skyfire/2.") ? GENERIC_SKYFIRE_VERSION2 : GENERIC_SKYFIRE_VERSION1;
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "SkyfireMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "Skyfire";
    }
}
