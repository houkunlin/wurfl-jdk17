package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Matcher implementation for identifying KDDI devices and browsers.
 */

final class KDDIMatcher extends MatcherBase {
    private static final String OPWV_V62_GENERIC = "opwv_v62_generic";

    public KDDIMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * Returns the require devic eds.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds;
        requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(OPWV_V62_GENERIC);
        return requiredDeviceIds;
    }

    @Override
/**
 * Returns whether this ca nandle.
 */

    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().contains("KDDI-");
    }

    @Override
/**
 * Ri satch.
 */

    protected String risMatch(String normalizedUserAgent) {
        int matchLength = normalizedUserAgent.startsWith("KDDI/") ? StringMatchUtils.secondSlash(normalizedUserAgent) : StringMatchUtils.firstSlash(normalizedUserAgent);
        return matchLength == -1
                ? StringMatchUtils.NULL_STRING
                : StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength);
    }

    @Override
/**
 * Appl yecover yatch.
 */

    protected String applyRecoveryMatch(WURFLRequest request) {
        return OPWV_V62_GENERIC;
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "KDDIMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "Kddi";
    }
}
