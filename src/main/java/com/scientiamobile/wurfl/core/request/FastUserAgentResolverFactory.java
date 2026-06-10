package com.scientiamobile.wurfl.core.request;

/**
 * {@link FastUserAgentResolver} 的工厂实现。
 * <p>每次调用 {@link #create()} 都返回一个新的 {@link FastUserAgentResolver} 实例。</p>
 */
public class FastUserAgentResolverFactory implements UserAgentResolverFactory {
    @Override
    public UserAgentResolver create() {
        return new FastUserAgentResolver();
    }
}
