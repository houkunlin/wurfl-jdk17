package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import org.apache.commons.lang3.Validate;

/**
 * Normalizes User-Agent strings for UP Link.
 */

public class UPLinkNormalizer implements UserAgentNormalizer {
    @Override
    public String normalize(String userAgent) {
        Validate.notNull(userAgent, "The userAgent is null");
        int upLinkIndex;
        upLinkIndex = userAgent.indexOf("UP.Link");
        if (upLinkIndex >= 0) {
            userAgent = userAgent.substring(0, upLinkIndex);
        }

        return userAgent;
    }
}
