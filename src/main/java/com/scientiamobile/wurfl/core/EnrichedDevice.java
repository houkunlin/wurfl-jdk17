package com.scientiamobile.wurfl.core;

public interface EnrichedDevice extends Device {
    String getMatcherName();

    String getBucketMatcherName();

    String getNormalizedUserAgent();
}
