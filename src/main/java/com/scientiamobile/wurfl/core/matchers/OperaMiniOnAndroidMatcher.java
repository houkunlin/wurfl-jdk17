package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Matcher implementation for identifying Opera Mini On Android devices and browsers.
 */

final class OperaMiniOnAndroidMatcher extends MatcherBase {
    private static final String GENERIC_OPERA_MINI_ANDROID_VERSION5 = "generic_opera_mini_android_version5";
    private static final String[] OPERA_MINI_ANDROID_PREFIXES = new String[]{"Opera/9.80 (J2ME/MIDP; Opera Mini/5", "Opera/9.80 (Android; Opera Mini/5.0", "Opera/9.80 (Android; Opera Mini/5.1"};

    public OperaMiniOnAndroidMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * Returns the require devic eds.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(GENERIC_OPERA_MINI_ANDROID_VERSION5);
        return requiredDeviceIds;
    }

    @Override
/**
 * Returns whether this ca nandle.
 */

    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && StringMatchUtils.containsAllOf(request.getCleanedDeviceUserAgent(), "Android", "Opera Mini");
    }

    @Override
/**
 * Ri satch.
 */

    protected String risMatch(String userAgent) {
        int matchLength;
        matchLength = userAgent.indexOf(" Build/");
        if (matchLength < 0) {
            for (String prefix : OPERA_MINI_ANDROID_PREFIXES) {
                if (userAgent.startsWith(prefix)) {
                    matchLength = prefix.length();
                    break;
                }
            }
        }

        return matchLength >= 0 ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength) : null;
    }

    @Override
/**
 * Appl yecover yatch.
 */

    protected String applyRecoveryMatch(WURFLRequest request) {
        return GENERIC_OPERA_MINI_ANDROID_VERSION5;
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "OperaMiniOnAndroidMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "OperaMiniOnAndroid";
    }
}
