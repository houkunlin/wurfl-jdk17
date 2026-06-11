package com.scientiamobile.wurfl.core.request;

/**
 * User-Agent 解析器工厂接口。
 * <p>定义了创建 {@link UserAgentResolver} 实例的标准方法，
 * 不同的实现类可以创建不同类型的解析器。</p>
 */

public interface UserAgentResolverFactory {
    /**
     * 创建一个新的 User-Agent 解析器实例。
     *
     * @return User-Agent 解析器实例
     */
    UserAgentResolver create();
}
