package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

/**
 * Matcher implementation for identifying Nec devices and browsers.
 */

final class NecMatcher extends MatcherBase {
    public NecMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * Returns whether this ca nandle.
 */

    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && StringMatchUtils.startsWithAnyOf(request.getCleanedDeviceUserAgent(), "NEC-", "KGT");
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "NecMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "Nec";
    }
}
