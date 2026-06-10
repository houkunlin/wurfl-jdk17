package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Matcher implementation for identifying HTC Mac devices and browsers.
 */

final class HTCMacMatcher extends MatcherBase {
    private static final String GENERIC_HTC_ANDROID_DISGUISED_AS_MAC = "generic_android_htc_disguised_as_mac";

    public HTCMacMatcher(UserAgentNormalizer normalizer, WURFLModel wurflModel) {
        super(normalizer, wurflModel);
    }

    @Override
/**
 * Returns the require devic eds.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds;
        requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(GENERIC_HTC_ANDROID_DISGUISED_AS_MAC);
        return requiredDeviceIds;
    }

    @Override
/**
 * Returns whether this ca nandle.
 */

    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return cleanedDeviceUserAgent.startsWith("Mozilla/5.0 (Macintosh") && cleanedDeviceUserAgent.contains("HTC");
    }

    @Override
/**
 * Ri satch.
 */

    protected String risMatch(String normalizedUserAgent) {
        int matchLength = StringMatchUtils.indexOfOrLength(normalizedUserAgent, "---");
        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength);
    }

    @Override
/**
 * Appl yecover yatch.
 */

    protected String applyRecoveryMatch(WURFLRequest request) {
        return GENERIC_HTC_ANDROID_DISGUISED_AS_MAC;
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "HTCMacMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "HTCMac";
    }
}
