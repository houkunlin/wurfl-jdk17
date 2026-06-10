package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Matcher implementation for identifying Do Co Mo devices and browsers.
 */

final class DoCoMoMatcher extends MatcherBase {
    private static final String DOCOMO_VER2 = "docomo_generic_jap_ver2";
    private static final String DOCOMO_VER1 = "docomo_generic_jap_ver1";

    public DoCoMoMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * Returns the require devic eds.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds;
        requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(DOCOMO_VER1);
        requiredDeviceIds.add(DOCOMO_VER2);
        return requiredDeviceIds;
    }

    @Override
/**
 * Returns whether this ca nandle.
 */

    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().startsWith("DoCoMo");
    }

    @Override
/**
 * Ri satch.
 */

    protected String risMatch(String normalizedUserAgent) {
        int matchLength;
        matchLength = StringMatchUtils.secondSlash(normalizedUserAgent);
        if (matchLength == -1) {
            matchLength = StringMatchUtils.firstOpenParenthesis(normalizedUserAgent);
        }

        return matchLength != -1
                ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength)
                : StringMatchUtils.NULL_STRING;
    }

    @Override
/**
 * Appl yecover yatch.
 */

    protected String applyRecoveryMatch(WURFLRequest request) {
        return request.getNormalizedDeviceUserAgent().startsWith("DoCoMo/2") ? DOCOMO_VER2 : DOCOMO_VER1;
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "DoCoMoMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "DoCoMo";
    }
}
