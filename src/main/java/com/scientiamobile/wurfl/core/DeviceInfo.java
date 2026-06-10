package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.matchers.MatchType;

/**
 * Information about a matched device, including its ID and match details.
 */

public class DeviceInfo {
    private final String id;
    private final MatchType matchType;
    private final String matcherName;
    private final String bucketMatcherName;
    private final String normalizedUserAgent;
    private final String originalUserAgent;

    public DeviceInfo(String id, MatchType matchType, String matcherName, String bucketMatcherName, String normalizedUserAgent, String originalUserAgent) {
        this.id = id;
        this.matchType = matchType;
        this.matcherName = matcherName;
        this.bucketMatcherName = bucketMatcherName;
        this.normalizedUserAgent = normalizedUserAgent;
        this.originalUserAgent = originalUserAgent;
    }

    /**
     * Returns the id.
     */

    public String getId() {
        return this.id;
    }

    public MatchType getMatchType() {
        return this.matchType;
    }

    /**
     * Returns the matche rame.
 */

    public String getMatcherName() {
        return this.matcherName;
    }

    public String getBucketMatcherName() {
        return this.bucketMatcherName;
    }

    /**
     * Returns the normalize dse rgent.
 */

    public String getNormalizedUserAgent() {
        return this.normalizedUserAgent;
    }

    public String getOriginalUserAgent() {
        return this.originalUserAgent;
    }

    @Override
/**
 * Returns a string representation of this object.
 */

    public String toString() {
        StringBuilder builder;
        builder = new StringBuilder("{id='");
        builder.append(this.id).append('\'');
        builder.append(", match=").append(this.matchType);
        builder.append(", matcher=").append(this.matcherName);
        builder.append('}');
        return builder.toString();
    }
}
