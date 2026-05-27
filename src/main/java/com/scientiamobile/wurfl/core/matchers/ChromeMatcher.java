package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

final class ChromeMatcher extends MatcherBase {
    private static final String CHROME_DEVICE_ID = "google_chrome";

    public ChromeMatcher(UserAgentNormalizer normalizer, WURFLModel wurflModel) {
        super(normalizer, wurflModel);
    }

    @Override
    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds;
        requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(CHROME_DEVICE_ID);
        return requiredDeviceIds;
    }

    @Override
    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsMobileBrowser() && cleanedDeviceUserAgent != null && cleanedDeviceUserAgent.contains("Chrome");
    }

    @Override
    protected String risMatch(String normalizedUserAgent) {
        return StringMatchUtils.risMatch(
                this.getFilter().getIndex().getUserAgents(),
                normalizedUserAgent,
                StringMatchUtils.indexOfOrLength(normalizedUserAgent, ".")
        );
    }

    @Override
    protected String applyRecoveryMatch(WURFLRequest request) {
        return CHROME_DEVICE_ID;
    }

    @Override
    public String getMatcherName() {
        return "ChromeMatcher";
    }

    @Override
    public String getBucketMatcherName() {
        return "Chrome";
    }
}
