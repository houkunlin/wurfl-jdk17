package com.scientiamobile.wurfl.core.request;

import com.scientiamobile.wurfl.core.UserAgentPriority;

public interface WURFLRequestFactoryWithPriority extends WURFLRequestFactory {
  UserAgentPriority getUserAgentPriority();
  
  void setUserAgentPriority(UserAgentPriority paramUserAgentPriority);
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\WURFLRequestFactoryWithPriority.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */