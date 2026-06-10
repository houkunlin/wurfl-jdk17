package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.Collection;

/**
 * Matcher implementation for identifying Catch All RIS devices and browsers.
 */

final class CatchAllRISMatcher extends AbstractMatcher {
    @Override
    public boolean canHandle(WURFLRequest request) {
        return true;
    }

    @Override
/**
 * Ri satch.
 */

    protected String risMatch(String normalizedUserAgent) {
        Collection<String> userAgents = this.getFilter().getIndex().getUserAgents();
        if (normalizedUserAgent != null && normalizedUserAgent.startsWith("CFNetwork")) {
            int matchLength = StringMatchUtils.firstSpace(normalizedUserAgent);
            if (matchLength != -1) {
                return StringMatchUtils.risMatch(userAgents, normalizedUserAgent, matchLength);
            }
        } else {
            int matchLength = StringMatchUtils.firstSlash(normalizedUserAgent);
            if (matchLength != -1) {
                return StringMatchUtils.risMatch(userAgents, normalizedUserAgent, matchLength);
            }
        }

        return StringMatchUtils.NULL_STRING;
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "CatchAllRISMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "CatchAllRis";
    }
}
