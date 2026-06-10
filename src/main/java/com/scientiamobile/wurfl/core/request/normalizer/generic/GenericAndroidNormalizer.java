package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

import java.util.regex.Pattern;

/**
 * Normalizes User-Agent strings for Generic Android.
 */

public class GenericAndroidNormalizer implements UserAgentNormalizer {
    private static final Pattern ANDROID_VERSION_PATTERN = Pattern.compile("Android[ \\-\\/](\\d\\.\\d)[^; \\/\\)]+");

    @Override
    public String normalize(String userAgent) {
        return ANDROID_VERSION_PATTERN.matcher(userAgent).replaceAll("Android $1");
    }
}
