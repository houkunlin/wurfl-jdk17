package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

/**
 * Normalizes User-Agent strings for Encription Level.
 */

public class EncriptionLevelNormalizer implements UserAgentNormalizer {
    private static final CharSequence ENCRIPTION_LEVEL_TOKEN = new String(" U;");

    @Override
    public String normalize(String userAgent) {
        return userAgent.replace(ENCRIPTION_LEVEL_TOKEN, "");
    }
}
