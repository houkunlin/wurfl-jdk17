package com.scientiamobile.wurfl.core.request;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

/**
 * User-Agent 规范化器工厂接口。
 * <p>定义了创建 {@link UserAgentNormalizer} 实例的标准方法，
 * 不同的实现类可以创建不同类型的规范化器。</p>
 */

public interface UserAgentNormalizerFactory {
    /**
     * 创建一个新的 User-Agent 规范化器实例。
     *
     * @return User-Agent 规范化器实例
     */
    UserAgentNormalizer create();
}
