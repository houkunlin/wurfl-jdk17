package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Matcher implementation for identifying Maemo devices and browsers.
 */

final class MaemoMatcher extends MatcherBase {
    private static final String GENERIC_OPERA_MOBI_MAEMO = "generic_opera_mobi_maemo";
    private static final String NOKIA_GENERIC_MAEMO_WITH_FIREFOX = "nokia_generic_maemo_with_firefox";
    private static final String NOKIA_GENERIC_MAEMO = "nokia_generic_maemo";

    public MaemoMatcher(UserAgentNormalizer normalizer, WURFLModel wurflModel) {
        super(normalizer, wurflModel);
    }

    @Override
/**
 * Returns the require devic eds.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds;
        requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(GENERIC_OPERA_MOBI_MAEMO);
        requiredDeviceIds.add(NOKIA_GENERIC_MAEMO_WITH_FIREFOX);
        requiredDeviceIds.add(NOKIA_GENERIC_MAEMO);
        return requiredDeviceIds;
    }

    @Override
/**
 * Returns whether this ca nandle.
 */

    public boolean canHandle(WURFLRequest request) {
        return request.getCleanedDeviceUserAgent().contains("Maemo");
    }

    @Override
/**
 * Appl yecover yatch.
 */

    protected String applyRecoveryMatch(WURFLRequest request) {
        String normalizedDeviceUserAgent;
        normalizedDeviceUserAgent = request.getNormalizedDeviceUserAgent();
        if (normalizedDeviceUserAgent.contains("Opera Mobi")) {
            return GENERIC_OPERA_MOBI_MAEMO;
        } else {
            return normalizedDeviceUserAgent.contains("Firefox") ? NOKIA_GENERIC_MAEMO_WITH_FIREFOX : NOKIA_GENERIC_MAEMO;
        }
    }

    @Override
/**
 * Ri satch.
 */

    protected String risMatch(String normalizedUserAgent) {
        int matchLength;
        matchLength = normalizedUserAgent.indexOf("---");
        return matchLength >= 0
                ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength + 3)
                : super.risMatch(normalizedUserAgent);
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "MaemoMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "Maemo";
    }
}
