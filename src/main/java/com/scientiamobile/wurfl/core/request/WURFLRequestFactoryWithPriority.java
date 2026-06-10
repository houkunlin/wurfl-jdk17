package com.scientiamobile.wurfl.core.request;

import com.scientiamobile.wurfl.core.UserAgentPriority;

/**
 * Implementation of WURFL Request Factory With Priority.
 */

public interface WURFLRequestFactoryWithPriority extends WURFLRequestFactory {
    UserAgentPriority getUserAgentPriority();

    void setUserAgentPriority(UserAgentPriority userAgentPriority);
}
