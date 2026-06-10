package com.scientiamobile.wurfl.core;

/**
 * Implementation of Enriched Device.
 */

public interface EnrichedDevice extends Device {
    String getMatcherName();

    String getBucketMatcherName();

    String getNormalizedUserAgent();
}
