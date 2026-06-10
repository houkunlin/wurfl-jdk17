package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Normalizes User-Agent strings for Web OS.
 */

public class WebOSNormalizer implements UserAgentNormalizer {
    private static final Pattern TRAILING_APP_NAME_AND_VERSION_PATTERN = Pattern.compile(" ([^/]+)/([\\d\\.]+)$");
    private static final Pattern WEBOS_MAJOR_VERSION_PATTERN = Pattern.compile("(?:hpw|web)OS.(\\d)\\.");

    @Override
/**
 * Normalizes the given User-Agent string.
 * @param userAgent the raw User-Agent string
 * @return the normalized User-Agent string
 */

    public String normalize(String userAgent) {
        Matcher matcher;
        matcher = WEBOS_MAJOR_VERSION_PATTERN.matcher(userAgent);
        String webOsToken = matcher.find() ? "webOS".concat(matcher.group(1)) : null;
        matcher = TRAILING_APP_NAME_AND_VERSION_PATTERN.matcher(userAgent);
        String appNameAndVersion = matcher.find() ? matcher.group(1) + " " + matcher.group(2) : null;
        return webOsToken != null && appNameAndVersion != null ? appNameAndVersion + " " + webOsToken + "---" + userAgent : userAgent;
    }
}
