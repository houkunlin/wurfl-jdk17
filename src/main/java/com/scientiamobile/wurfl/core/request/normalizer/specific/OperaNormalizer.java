package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OperaNormalizer implements UserAgentNormalizer {
    private static final Pattern OPERA_VERSION_PATTERN = Pattern.compile("Version/(\\d+\\.\\d+)");
    private static final Pattern OPERA_CHROMIUM_VERSION_PATTERN = Pattern.compile("OPR/(\\d+\\.\\d+)");

    @Override
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
