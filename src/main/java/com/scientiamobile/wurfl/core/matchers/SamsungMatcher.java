package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Matcher implementation for identifying Samsung devices and browsers.
 */

final class SamsungMatcher extends MatcherBase {
    private static final String SAMSUNG = "Samsung";
    private static final String[] LEADING_SLASH_PREFIXES = new String[]{"SEC-", "SAMSUNG-", "SCH"};
    private static final String[] LEADING_SPACE_PREFIXES = new String[]{SAMSUNG, "SPH", "SGH"};
    private static final String[] CAN_HANDLE_PREFIXES = new String[]{"SEC-", "SPH", "SGH", "SCH"};

    public SamsungMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * Returns the require devic eds.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add("generic");
        return requiredDeviceIds;
    }

    @Override
/**
 * Returns whether this ca nandle.
 */

    public boolean canHandle(WURFLRequest request) {
        if (request.getOriginalUserAgent().contains("SamsungBrowser")) {
            return false;
        }
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsDesktopBrowser()
            && (StringMatchUtils.startsWithAnyOf(cleanedDeviceUserAgent, CAN_HANDLE_PREFIXES)
            || cleanedDeviceUserAgent.toLowerCase().contains("samsung"));
    }

    @Override
/**
 * Ri satch.
 */

    protected String risMatch(String userAgent) {
        int matchLength;
        if (StringMatchUtils.startsWithAnyOf(userAgent, LEADING_SLASH_PREFIXES)) {
            matchLength = StringMatchUtils.firstSlash(userAgent);
        } else if (StringMatchUtils.startsWithAnyOf(userAgent, LEADING_SPACE_PREFIXES)) {
            matchLength = StringMatchUtils.firstSpace(userAgent);
        } else {
            matchLength = StringMatchUtils.secondSlash(userAgent);
        }
        if (matchLength == -1) {
            return StringMatchUtils.NULL_STRING;
        }
        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
    }

    @Override
/**
 * Appl yecover yatch.
 */

    protected String applyRecoveryMatch(WURFLRequest request) {
        String normalizedUserAgent = request.getNormalizedDeviceUserAgent();
        int samsungIndex = StringMatchUtils.indexOf(normalizedUserAgent, SAMSUNG);
        int matchLength = StringMatchUtils.indexOfOrLength(normalizedUserAgent, "/", samsungIndex);
        String matchedUserAgent = StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength);
        return !StringUtils.isBlank(matchedUserAgent) ? this.getFilter().getIndex().getDeviceIdByUserAgent(matchedUserAgent) : "generic";
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "SamsungMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return SAMSUNG;
    }
}
