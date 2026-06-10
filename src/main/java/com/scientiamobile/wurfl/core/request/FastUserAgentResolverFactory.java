package com.scientiamobile.wurfl.core.request;

/**
 * Factory for creating Fast User Agent Resolver instances.
 */

public class FastUserAgentResolverFactory implements UserAgentResolverFactory {
    @Override
    public UserAgentResolver create() {
        return new FastUserAgentResolver();
    }
}
