package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Matcher implementation for identifying Firefox devices and browsers.
 */

final class FirefoxMatcher extends MatcherBase {
    private static final String FIREFOX_DEVICE_ID = "firefox";

    public FirefoxMatcher(UserAgentNormalizer normalizer, WURFLModel wurflModel) {
        super(normalizer, wurflModel);
    }

    @Override
/**
 * Returns the require devic eds.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds;
        requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(FIREFOX_DEVICE_ID);
        return requiredDeviceIds;
    }

    @Override
/**
 * Returns whether this ca nandle.
 */

    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsMobileBrowser()
                && cleanedDeviceUserAgent.contains("Firefox")
                && !StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, "Tablet", "Sony", "Novarra", "Opera");
    }

    @Override
/**
 * Ri satch.
 */

    protected String risMatch(String normalizedUserAgent) {
        String firefoxUserAgent = normalizedUserAgent.substring(normalizedUserAgent.indexOf("Firefox"));
        int matchLength = StringMatchUtils.indexOfOrLength(firefoxUserAgent, ".");
        return matchLength == -1
                ? null
                : StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), firefoxUserAgent, matchLength + 1);
    }

    @Override
/**
 * Appl yecover yatch.
 */

    protected String applyRecoveryMatch(WURFLRequest request) {
        return FIREFOX_DEVICE_ID;
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "FirefoxMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "Firefox";
    }
}
