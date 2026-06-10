package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Matcher implementation for identifying Bot devices and browsers.
 */

final class BotMatcher extends AbstractMatcher {
    private static final String GOOGLE_IMAGE_PROXY = "google_image_proxy";

    public BotMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * Returns the require devic eds.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds;
        requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(GOOGLE_IMAGE_PROXY);
        requiredDeviceIds.add("generic_web_crawler");
        return requiredDeviceIds;
    }

    @Override
/**
 * Returns whether this ca nandle.
 */

    public boolean canHandle(WURFLRequest request) {
        return request._internalIsBot();
    }

    @Override
/**
 * Ri satch.
 */

    protected String risMatch(String normalizedUserAgent) {
        int matchLength = normalizedUserAgent.startsWith("Mozilla")
                ? StringMatchUtils.firstCloseParenthesis(normalizedUserAgent)
                : StringMatchUtils.firstSlash(normalizedUserAgent);
        return matchLength != -1
                ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength)
                : StringMatchUtils.NULL_STRING;
    }

    @Override
/**
 * Appl yonclusiv eatch.
 */

    protected String applyConclusiveMatch(WURFLRequest request) {
        return request.getCleanedDeviceUserAgent().contains("GoogleImageProxy") ? GOOGLE_IMAGE_PROXY : super.applyConclusiveMatch(request);
    }

    @Override
/**
 * Appl yecover yatch.
 */

    protected String applyRecoveryMatch(WURFLRequest request) {
        return "generic_web_crawler";
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "BotMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "Bot";
    }
}
