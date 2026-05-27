package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

public class SafariNormalizer implements UserAgentNormalizer {
    @Override
    public String normalize(String userAgent) {
        int versionTokenStart;
        versionTokenStart = userAgent.indexOf("Version/");
        if (versionTokenStart != -1) {
            versionTokenStart += 8;
            int majorVersionDotIndex;
            majorVersionDotIndex = userAgent.indexOf(46, versionTokenStart);
            if (majorVersionDotIndex != -1) {
                return "Safari " + userAgent.substring(versionTokenStart, majorVersionDotIndex) + "---" + userAgent;
            }
        }

        return userAgent;
    }
}
