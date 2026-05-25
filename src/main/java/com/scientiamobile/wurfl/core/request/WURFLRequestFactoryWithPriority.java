package com.scientiamobile.wurfl.core.request;

import com.scientiamobile.wurfl.core.UserAgentPriority;

public interface WURFLRequestFactoryWithPriority extends WURFLRequestFactory {
   UserAgentPriority getUserAgentPriority();

   void setUserAgentPriority(UserAgentPriority var1);
}
