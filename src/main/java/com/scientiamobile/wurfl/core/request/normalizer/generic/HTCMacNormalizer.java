package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTCMacNormalizer implements UserAgentNormalizer {
    private static final Pattern LOCALE_SUBSTITUTION_PATTERN = Pattern.compile("; [a-z]{2}(?:-[a-zA-Z]{2})?(?:\\.utf8|\\.big5)?\\b ");
    private static final Pattern HTC_MODEL_PATTERN = Pattern.compile("(HTC[^;\\)]+)");

    @Override
    public String normalize(String userAgent) {
        userAgent = LOCALE_SUBSTITUTION_PATTERN.matcher(userAgent).replaceFirst("; xx-xx");
        Matcher htcModelMatcher;
        htcModelMatcher = HTC_MODEL_PATTERN.matcher(userAgent);
        if (htcModelMatcher.find()) {
            String htcModel = htcModelMatcher.group();
            return htcModel.replaceAll("[ _\\-/]", "~") + "---" + userAgent;
        } else {
            return userAgent;
        }
    }
}
