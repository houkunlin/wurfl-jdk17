package com.scientiamobile.wurfl.core.request.normalizer;

/**
 * Normalizes User-Agent strings for User Agent.
 */

public interface UserAgentNormalizer {
    String normalize(String userAgent);
}
