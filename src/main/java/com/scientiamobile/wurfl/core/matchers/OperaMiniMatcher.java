package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Matcher implementation for identifying Opera Mini devices and browsers.
 */

final class OperaMiniMatcher extends MatcherBase {
    private static final SortedMap<String, String> OPERA_MINI_VERSION_TO_DEVICE_ID;

    static {
        OPERA_MINI_VERSION_TO_DEVICE_ID = new TreeMap<>();
        OPERA_MINI_VERSION_TO_DEVICE_ID.put("Opera Mini/1", "generic_opera_mini_version1");
        OPERA_MINI_VERSION_TO_DEVICE_ID.put("Opera Mini/2", "generic_opera_mini_version2");
        OPERA_MINI_VERSION_TO_DEVICE_ID.put("Opera Mini/3", "generic_opera_mini_version3");
        OPERA_MINI_VERSION_TO_DEVICE_ID.put("Opera Mini/4", "generic_opera_mini_version4");
        OPERA_MINI_VERSION_TO_DEVICE_ID.put("Opera Mini/5", "generic_opera_mini_version5");
        OPERA_MINI_VERSION_TO_DEVICE_ID.put("Opera Mini/6", "generic_opera_mini_version6");
        OPERA_MINI_VERSION_TO_DEVICE_ID.put("Opera Mini/7", "generic_opera_mini_version7");
    }

    public OperaMiniMatcher(UserAgentNormalizer userAgentNormalizer, WURFLModel wurflModel) {
        super(userAgentNormalizer, wurflModel);
    }

    @Override
/**
 * Returns the require devic eds.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.addAll(OPERA_MINI_VERSION_TO_DEVICE_ID.values());
        return requiredDeviceIds;
    }

    @Override
/**
 * Returns whether this ca nandle.
 */

    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && StringMatchUtils.containsAnyOf(request.getCleanedDeviceUserAgent(), "Opera Mini", "OperaMini", "Opera Mobi", "OperaMobi");
    }

    @Override
/**
 * Ri satch.
 */

    protected String risMatch(String userAgent) {
        int matchLength;
        matchLength = userAgent.indexOf("---");
        if (matchLength >= 0) {
            return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength + 3);
        } else if ((matchLength = StringMatchUtils.indexOf(userAgent, "Opera Mini")) >= 0 && (matchLength = StringMatchUtils.indexOf(userAgent, ".", matchLength)) >= 0) {
            return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength + 1);
        } else {
            matchLength = StringMatchUtils.firstSlash(userAgent);
            return matchLength != -1 ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength) : StringMatchUtils.NULL_STRING;
        }
    }

    @Override
/**
 * Appl yecover yatch.
 */

    protected String applyRecoveryMatch(WURFLRequest request) {
        String normalizedUserAgent = request.getNormalizedDeviceUserAgent();
        for (String versionPrefix : OPERA_MINI_VERSION_TO_DEVICE_ID.keySet()) {
            if (normalizedUserAgent.toLowerCase().contains(versionPrefix.toLowerCase())) {
                return OPERA_MINI_VERSION_TO_DEVICE_ID.get(versionPrefix);
            }
        }

        return normalizedUserAgent.contains("Opera Mobi") ? "generic_opera_mini_version4" : "generic_opera_mini_version1";
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "OperaMiniMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "OperaMini";
    }
}
