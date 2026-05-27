package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

import java.util.regex.Pattern;

public class SerialNumberNormalizer implements UserAgentNormalizer {
    private static final Pattern SN_PATTERN = Pattern.compile("/SN[\\dX]+");
    private static final Pattern ST_TF_NT_PATTERN = Pattern.compile("\\[(ST|TF|NT)[\\dX]+\\]");

    @Override
    public String normalize(String userAgent) {
        userAgent = SN_PATTERN.matcher(userAgent).replaceAll("/SNXXXXXXXXXXXXXXX");
        return ST_TF_NT_PATTERN.matcher(userAgent).replaceAll("TFXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    }
}
