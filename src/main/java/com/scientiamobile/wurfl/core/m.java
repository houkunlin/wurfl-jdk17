package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.cache.CacheProvider;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.WURFLRequestFactoryWithPriority;
import com.scientiamobile.wurfl.core.resource.WURFLResource;
import com.scientiamobile.wurfl.core.resource.WURFLResources;
import jakarta.servlet.http.HttpServletRequest;

interface m {
  Device getDeviceForRequest(HttpServletRequest paramHttpServletRequest);

  Device getDeviceForRequest(WURFLRequest paramWURFLRequest);

  Device getDeviceForRequest(String paramString);

  EngineTarget getDeviceForRequest();

  void getDeviceForRequest(EngineTarget paramEngineTarget);

  UserAgentPriority b();

  void getDeviceForRequest(UserAgentPriority paramUserAgentPriority);

  void getDeviceForRequest(CacheProvider paramCacheProvider);

  void getDeviceForRequest(WURFLResource paramWURFLResource, WURFLResources paramWURFLResources, String... paramVarArgs);

  void getDeviceForRequest(WURFLResources paramWURFLResources, String... paramVarArgs);

  void getDeviceForRequest(WURFLRequestFactoryWithPriority paramWURFLRequestFactoryWithPriority);

  Device b(String paramString);

  Device getDeviceForRequest(String paramString, HttpServletRequest paramHttpServletRequest);

  Device getDeviceForRequest(String paramString, WURFLRequest paramWURFLRequest);
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\m.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
