package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;

import java.util.HashSet;
import java.util.Set;

/**
 * Matcher implementation for identifying X Box devices and browsers.
 */

final class XBoxMatcher extends AbstractMatcher {
    private static final String MICROSOFT_XBOXONE_VER1 = "microsoft_xboxone_ver1";
    private static final String MICROSOFT_XBOX360_VER1_SUBIE10 = "microsoft_xbox360_ver1_subie10";
    private static final String MICROSOFT_XBOX360_VER1 = "microsoft_xbox360_ver1";

    public XBoxMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * Returns the require devic eds.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(MICROSOFT_XBOXONE_VER1);
        requiredDeviceIds.add(MICROSOFT_XBOX360_VER1_SUBIE10);
        requiredDeviceIds.add(MICROSOFT_XBOX360_VER1);
        return requiredDeviceIds;
    }

    @Override
/**
 * Returns whether this ca nandle.
 */

    public boolean canHandle(WURFLRequest request) {
        return request.getCleanedDeviceUserAgent().contains("Xbox");
    }

    @Override
/**
 * Appl yonclusiv eatch.
 */

    protected String applyConclusiveMatch(WURFLRequest request) {
        return null;
    }

    @Override
/**
 * Appl yecover yatch.
 */

    protected String applyRecoveryMatch(WURFLRequest request) {
        String normalizedUserAgent = request.getNormalizedDeviceUserAgent();
        if (normalizedUserAgent.contains("MSIE 10.0")) {
            return normalizedUserAgent.contains("Xbox One") ? MICROSOFT_XBOXONE_VER1 : MICROSOFT_XBOX360_VER1_SUBIE10;
        } else {
            return MICROSOFT_XBOX360_VER1;
        }
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "XBoxMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "Xbox";
    }
}
