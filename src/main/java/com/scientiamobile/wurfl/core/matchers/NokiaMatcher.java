package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Matcher implementation for identifying Nokia devices and browsers.
 */

final class NokiaMatcher extends MatcherBase {
    private static final String NOKIA_GENERIC_SERIES60 = "nokia_generic_series60";
    private static final String NOKIA_GENERIC_SERIES80 = "nokia_generic_series80";
    private static final String NOKIA_GENERIC_MEEGO = "nokia_generic_meego";

    public NokiaMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * Returns the require devic eds.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(NOKIA_GENERIC_SERIES60);
        requiredDeviceIds.add(NOKIA_GENERIC_SERIES80);
        requiredDeviceIds.add(NOKIA_GENERIC_MEEGO);
        requiredDeviceIds.add("generic_mobile");
        return requiredDeviceIds;
    }

    @Override
/**
 * Returns whether this ca nandle.
 */

    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsDesktopBrowser() && cleanedDeviceUserAgent.contains("Nokia") && !StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, "Android", "iPhone");
    }

    @Override
/**
 * Ri satch.
 */

    protected String risMatch(String userAgent) {
        int matchLength = StringMatchUtils.indexOfAnyOrLength(userAgent, new String[]{"/", " "}, userAgent.indexOf("Nokia"));
        if (StringMatchUtils.startsWithAnyOf(userAgent, "Nokia/", "Nokia ")) {
            matchLength = userAgent.length();
        }

        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
    }

    @Override
/**
 * Appl yecover yatch.
 */

    protected String applyRecoveryMatch(WURFLRequest request) {
        String normalizedUserAgent = request.getNormalizedDeviceUserAgent();
        if (normalizedUserAgent.contains("Series60")) {
            return NOKIA_GENERIC_SERIES60;
        } else if (normalizedUserAgent.contains("Series80")) {
            return NOKIA_GENERIC_SERIES80;
        } else {
            return normalizedUserAgent.contains("MeeGo") ? NOKIA_GENERIC_MEEGO : "generic";
        }
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "NokiaMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "Nokia";
    }
}
