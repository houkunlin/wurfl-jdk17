package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OperaMiniNormalizer implements UserAgentNormalizer {
    private static final Pattern OPERA_MINI_RES_PATTERN = Pattern.compile("^Opera/[\\d\\.]+ .+?\\d{3}X\\d{3} (.+)$");

    @Override
    public String normalize(String userAgent) {
        Matcher operaMiniMatcher;
        operaMiniMatcher = OPERA_MINI_RES_PATTERN.matcher(userAgent);
        return operaMiniMatcher.matches() ? operaMiniMatcher.group(1) + "---" + userAgent : userAgent;
    }
}
