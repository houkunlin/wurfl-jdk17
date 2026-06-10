package com.scientiamobile.wurfl.core.request;

/**
 * Factory for creating User Agent Resolver instances.
 */

public interface UserAgentResolverFactory {
    /**
     * 创建一个新的 User-Agent 解析器实例。
     *
     * @return User-Agent 解析器实例
     */
    UserAgentResolver create();
}
