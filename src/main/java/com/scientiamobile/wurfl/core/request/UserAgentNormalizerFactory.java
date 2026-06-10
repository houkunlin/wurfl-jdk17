package com.scientiamobile.wurfl.core.request;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

/**
 * Factory for creating User Agent Normalizer instances.
 */

public interface UserAgentNormalizerFactory {
    UserAgentNormalizer create();
}
