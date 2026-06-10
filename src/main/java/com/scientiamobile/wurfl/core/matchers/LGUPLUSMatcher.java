package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Matcher implementation for identifying LGUPLUS devices and browsers.
 */

final class LGUPLUSMatcher extends MatcherBase {
    private static final String GENERIC_LGUPLUS = "generic_lguplus";
    private static final Map<String, String[]> DEVICE_BY_TOKENS;

    static {
        DEVICE_BY_TOKENS = new LinkedHashMap<>();
        DEVICE_BY_TOKENS.put("generic_lguplus_rexos_facebook_browser", new String[]{"Windows NT 5", "POLARIS"});
        DEVICE_BY_TOKENS.put("generic_lguplus_rexos_webviewer_browser", new String[]{"Windows NT 5"});
        DEVICE_BY_TOKENS.put("generic_lguplus_winmo_facebook_browser", new String[]{"Windows CE", "POLARIS"});
        DEVICE_BY_TOKENS.put("generic_lguplus_android_webkit_browser", new String[]{"Android", "AppleWebKit"});
    }

    public LGUPLUSMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * Returns the require devic eds.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds;
        requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.addAll(DEVICE_BY_TOKENS.keySet());
        requiredDeviceIds.add(GENERIC_LGUPLUS);
        return requiredDeviceIds;
    }

    @Override
/**
 * Returns whether this ca nandle.
 */

    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && StringMatchUtils.containsAnyOf(request.getCleanedDeviceUserAgent(), "lgtelecom", "LGUPLUS");
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
        String normalizedDeviceUserAgent = request.getNormalizedDeviceUserAgent();
        for (Map.Entry<String, String[]> entry : DEVICE_BY_TOKENS.entrySet()) {
            if (StringMatchUtils.containsAllOf(normalizedDeviceUserAgent, entry.getValue())) {
                return entry.getKey();
            }
        }

        return GENERIC_LGUPLUS;
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "LGUPLUSMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "LGUPLUS";
    }
}
