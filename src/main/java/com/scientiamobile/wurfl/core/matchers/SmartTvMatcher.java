package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;

import java.util.HashSet;
import java.util.Set;

final class SmartTvMatcher extends MatcherBase {
    private static final String GOOGLE_TV_BROWSER = "generic_smarttv_googletv_browser";
    private static final String APPLE_TV_BROWSER = "generic_smarttv_appletv_browser";
    private static final String BOXEEBOX_BROWSER = "generic_smarttv_boxeebox_browser";
    private static final String CHROMECAST = "generic_smarttv_chromecast";
    private static final String TIZEN_3_0 = "generic_tizen_smarttv_3_0";
    private static final String TIZEN_2_4 = "generic_tizen_smarttv_2_4";
    private static final String TIZEN_2_3 = "generic_tizen_smarttv_2_3";
    private static final String TIZEN_GENERIC = "generic_tizen_smarttv";

    public SmartTvMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add("generic_smarttv_browser");
        return requiredDeviceIds;
    }

    @Override
    public boolean canHandle(WURFLRequest request) {
        return request._internalIsSmartTvBrowser();
    }

    @Override
    protected String applyConclusiveMatch(WURFLRequest request) {
        return null;
    }

    @Override
    protected String applyRecoveryMatch(WURFLRequest request) {
        String normalizedUserAgent = request.getNormalizedDeviceUserAgent();
        if (normalizedUserAgent.contains("Tizen 3.0")) {
            return TIZEN_3_0;
        } else if (normalizedUserAgent.contains("Tizen 2.4")) {
            return TIZEN_2_4;
        } else if (normalizedUserAgent.contains("Tizen 2.3")) {
            return TIZEN_2_3;
        } else if (normalizedUserAgent.contains("Tizen")) {
            return TIZEN_GENERIC;
        } else if (normalizedUserAgent.contains("SmartTV")) {
            return "generic_smarttv_browser";
        } else if (normalizedUserAgent.contains("GoogleTV")) {
            return GOOGLE_TV_BROWSER;
        } else if (normalizedUserAgent.contains("AppleTV")) {
            return APPLE_TV_BROWSER;
        } else if (normalizedUserAgent.contains("Boxee")) {
            return BOXEEBOX_BROWSER;
        } else {
            return normalizedUserAgent.contains("CrKey") ? CHROMECAST : "generic_smarttv_browser";
        }
    }

    @Override
    public String getMatcherName() {
        return "SmartTvMatcher";
    }

    @Override
    public String getBucketMatcherName() {
        return "SmartTV";
    }
}
