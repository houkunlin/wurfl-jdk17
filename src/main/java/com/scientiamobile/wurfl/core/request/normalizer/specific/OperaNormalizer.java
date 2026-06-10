package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Normalizes User-Agent strings for Opera.
 */

public class OperaNormalizer implements UserAgentNormalizer {
    private static final Pattern OPERA_VERSION_PATTERN = Pattern.compile("Version/(\\d+\\.\\d+)");
    private static final Pattern OPERA_CHROMIUM_VERSION_PATTERN = Pattern.compile("OPR/(\\d+\\.\\d+)");

    @Override
/**
 * Normalizes the given User-Agent string.
 * @param userAgent the raw User-Agent string
 * @return the normalized User-Agent string
 */

    public String normalize(String userAgent) {
        Matcher versionMatcher;
        versionMatcher = OPERA_VERSION_PATTERN.matcher(userAgent);
        if (userAgent.startsWith("Opera/9.80") && versionMatcher.find()) {
            return userAgent.replace("Opera/9.80", "Opera/" + versionMatcher.group(1));
        } else {
            versionMatcher = OPERA_CHROMIUM_VERSION_PATTERN.matcher(userAgent);
            return versionMatcher.find() ? "Opera/" + versionMatcher.group(1) + " " + userAgent : userAgent;
        }
    }
}
