package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Matcher implementation for identifying HTC devices and browsers.
 */

final class HTCMatcher extends MatcherBase {
    private static final Pattern HTC_PREFIX = Pattern.compile("^.*?HTC[^/ ;]+[/ ;]");

    public HTCMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * Returns the require devic eds.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds;
        requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add("generic");
        requiredDeviceIds.add("generic_ms_mobile");
        return requiredDeviceIds;
    }

    @Override
/**
 * Returns whether this ca nandle.
 */

    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && StringMatchUtils.containsAnyOf(request.getCleanedDeviceUserAgent(), "HTC", "XV6875");
    }

    @Override
/**
 * Ri satch.
 */

    protected String risMatch(String normalizedUserAgent) {
        int matchLength = normalizedUserAgent.length();
        Matcher prefixMatcher;
        prefixMatcher = HTC_PREFIX.matcher(normalizedUserAgent);
        if (prefixMatcher.find()) {
            matchLength = prefixMatcher.group(0).length();
        }

        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength);
    }

    @Override
/**
 * Appl yecover yatch.
 */

    protected String applyRecoveryMatch(WURFLRequest request) {
        return request.getNormalizedDeviceUserAgent().contains("Windows CE;") ? "generic_ms_mobile" : "generic";
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "HTCMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "HTC";
    }
}
