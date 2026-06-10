package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Matcher implementation for identifying Safari devices and browsers.
 */

final class SafariMatcher extends MatcherBase {
    public SafariMatcher(UserAgentNormalizer userAgentNormalizer, WURFLModel wurflModel) {
        super(userAgentNormalizer, wurflModel);
    }

    @Override
/**
 * Returns the require devic eds.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add("generic_web_browser");
        requiredDeviceIds.add("generic_xhtml");
        return requiredDeviceIds;
    }

    @Override
/**
 * Returns whether this ca nandle.
 */

    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsMobileBrowser() && StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, "Safari") && StringMatchUtils.startsWithAnyOf(cleanedDeviceUserAgent, "Mozilla/5.0 (Macintosh", "Mozilla/5.0 (Windows");
    }

    @Override
/**
 * Ri satch.
 */

    protected String risMatch(String userAgent) {
        int matchLength;
        matchLength = userAgent.indexOf("---");
        return matchLength != -1 ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength + 3) : null;
    }

    @Override
/**
 * Appl yecover yatch.
 */

    protected String applyRecoveryMatch(WURFLRequest request) {
        String normalizedUserAgent = request.getNormalizedDeviceUserAgent();
        return !normalizedUserAgent.contains("Macintosh") && !normalizedUserAgent.contains("Windows") ? "generic_xhtml" : "generic_web_browser";
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "SafariMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "Safari";
    }
}
