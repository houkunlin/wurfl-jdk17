package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import org.apache.commons.lang3.StringUtils;

/**
 * Normalizes User-Agent strings for Firefox.
 */

public class FirefoxNormalizer implements UserAgentNormalizer {
    @Override
    public String normalize(String userAgent) {
        String normalizedUserAgent = userAgent;
        int firefoxIndex;
        firefoxIndex = userAgent.indexOf("Firefox");
        if (firefoxIndex >= 0) {
            normalizedUserAgent = StringUtils.substring(userAgent, firefoxIndex);
        }

        return normalizedUserAgent;
    }
}
