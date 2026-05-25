package com.scientiamobile.wurfl.core.request;

public class FastUserAgentResolverFactory implements UserAgentResolverFactory {
   public UserAgentResolver create() {
      return new FastUserAgentResolver();
   }
}
