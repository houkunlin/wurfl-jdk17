package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Matcher implementation for identifying Nokia Ovi Browser devices and browsers.
 */

final class NokiaOviBrowserMatcher extends MatcherBase {
    private static final String NOKIA_GENERIC_SERIES30PLUS = "nokia_generic_series30plus";
    private static final String NOKIA_GENERIC_SERIES40_OVIBROSR = "nokia_generic_series40_ovibrosr";

    public NokiaOviBrowserMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * Returns the require devic eds.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(NOKIA_GENERIC_SERIES30PLUS);
        requiredDeviceIds.add(NOKIA_GENERIC_SERIES40_OVIBROSR);
        return requiredDeviceIds;
    }

    @Override
/**
 * Returns whether this ca nandle.
 */

    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().contains("S40OviBrowser");
    }

    @Override
/**
 * Ri satch.
 */

    protected String risMatch(String userAgent) {
        int matchLength = StringMatchUtils.indexOfAnyOrLength(userAgent, new String[]{"/", " "}, userAgent.indexOf("Nokia"));
        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
    }

    @Override
/**
 * Appl yecover yatch.
 */

    protected String applyRecoveryMatch(WURFLRequest request) {
        return request.getNormalizedDeviceUserAgent().contains("Series30Plus") ? NOKIA_GENERIC_SERIES30PLUS : NOKIA_GENERIC_SERIES40_OVIBROSR;
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "NokiaOviBrowserMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "NokiaOviBrowser";
    }
}
