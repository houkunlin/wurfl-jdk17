package com.scientiamobile.wurfl.core.request;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

public interface UserAgentNormalizerFactory {
    UserAgentNormalizer create();
}
