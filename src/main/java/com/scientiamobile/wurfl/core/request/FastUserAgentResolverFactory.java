package com.scientiamobile.wurfl.core.request;

public class FastUserAgentResolverFactory implements UserAgentResolverFactory {
   @Override
   public UserAgentResolver create() {
      return new FastUserAgentResolver();
   }
}
