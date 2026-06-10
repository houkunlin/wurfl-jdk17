package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

import java.util.regex.Pattern;

/**
 * Normalizes User-Agent strings for Ascii.
 */

public class AsciiNormalizer implements UserAgentNormalizer {
    private static final Pattern NON_ASCII_PATTERN = Pattern.compile("[^ -~]+");

    @Override
    public String normalize(String userAgent) {
        return NON_ASCII_PATTERN.matcher(userAgent).replaceAll("");
    }
}
