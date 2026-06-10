package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Matcher implementation for identifying LG devices and browsers.
 */

final class LGMatcher extends MatcherBase {
    public LGMatcher(UserAgentNormalizer normalizer, WURFLModel wurflModel) {
        super(normalizer, wurflModel);
    }

    @Override
/**
 * Returns the require devic eds.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds;
        requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add("generic");
        return requiredDeviceIds;
    }

    @Override
/**
 * Returns whether this ca nandle.
 */

    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsDesktopBrowser() && cleanedDeviceUserAgent != null && cleanedDeviceUserAgent.regionMatches(true, 0, "lg", 0, 2);
    }

    @Override
/**
 * Ri satch.
 */

    protected String risMatch(String normalizedUserAgent) {
        int matchLength = StringMatchUtils.indexOfOrLength(normalizedUserAgent, "/", normalizedUserAgent.indexOf("LG"));
        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength);
    }

    @Override
/**
 * Appl yecover yatch.
 */

    protected String applyRecoveryMatch(WURFLRequest request) {
        FilteredDeviceIndex deviceIndex = this.getFilter().getIndex();
        String matchedUserAgent;
        String normalizedDeviceUserAgent = request.getNormalizedDeviceUserAgent();
        matchedUserAgent = StringMatchUtils.risMatch(deviceIndex.getUserAgents(), normalizedDeviceUserAgent, 7);
        return matchedUserAgent != null
                ? deviceIndex.getDeviceIdByUserAgent(matchedUserAgent)
                : "generic";
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "LGMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "LG";
    }
}
