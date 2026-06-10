package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Matcher implementation for identifying Kindle devices and browsers.
 */

final class KindleMatcher extends MatcherBase {
    private static final String GENERIC_AMAZON_KINDLE = "generic_amazon_kindle";
    private static final Map<String, String> DEVICE_BY_TOKEN;

    static {
        DEVICE_BY_TOKEN = new LinkedHashMap<>();
        DEVICE_BY_TOKEN.put("Kindle/1", "amazon_kindle_ver1");
        DEVICE_BY_TOKEN.put("Kindle/2", "amazon_kindle2_ver1");
        DEVICE_BY_TOKEN.put("Kindle/3", "amazon_kindle3_ver1");
        DEVICE_BY_TOKEN.put("Kindle Fire", "amazon_kindle_fire_ver1");
        DEVICE_BY_TOKEN.put("Silk", "amazon_kindle_fire_ver1");
    }

    public KindleMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * Returns the require devic eds.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(GENERIC_AMAZON_KINDLE);
        requiredDeviceIds.addAll(DEVICE_BY_TOKEN.values());
        return requiredDeviceIds;
    }

    @Override
/**
 * Returns whether this ca nandle.
 */

    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return cleanedDeviceUserAgent.contains("Android") && StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, "/Kindle", "Silk") ? false : StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, "Kindle", "Silk");
    }

    @Override
/**
 * Ri satch.
 */

    protected String risMatch(String userAgent) {
        int matchLength;
        matchLength = userAgent.indexOf("Build/");
        if (matchLength != -1) {
            return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
        } else {
            matchLength = userAgent.indexOf("Kindle/");
            if (matchLength >= 0) {
                matchLength += 7;
                char firstVersionChar;
                firstVersionChar = userAgent.charAt(matchLength);
                if (firstVersionChar >= '1' && firstVersionChar <= '3') {
                    return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength + 1);
                }
            }

            matchLength = userAgent.indexOf("PlayStation Vita");
            return matchLength >= 0 ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength + 16 + 1) : null;
        }
    }

    @Override
/**
 * Appl yecover yatch.
 */

    protected String applyRecoveryMatch(WURFLRequest request) {
        String normalizedUserAgent = request.getNormalizedDeviceUserAgent();

        for (Map.Entry<String, String> entry : DEVICE_BY_TOKEN.entrySet()) {
            if (normalizedUserAgent.contains(entry.getKey())) {
                return entry.getValue();
            }
        }

        return GENERIC_AMAZON_KINDLE;
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "KindleMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "Kindle";
    }
}
