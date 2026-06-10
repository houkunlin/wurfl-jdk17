package com.scientiamobile.wurfl.core.request;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

/**
 * Factory for creating User Agent Normalizer instances.
 */

public interface UserAgentNormalizerFactory {
    /**
     * 创建一个新的 User-Agent 规范化器实例。
     *
     * @return User-Agent 规范化器实例
     */
    UserAgentNormalizer create();
}
