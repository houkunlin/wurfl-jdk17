package com.scientiamobile.wurfl.core.request;

public class FastUserAgentResolverFactory implements UserAgentResolverFactory {
  public UserAgentResolver create() {
    return new FastUserAgentResolver();
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\FastUserAgentResolverFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */