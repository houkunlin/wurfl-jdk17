package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.cache.CacheProvider;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.WURFLRequestFactoryWithPriority;
import com.scientiamobile.wurfl.core.resource.WURFLResource;
import com.scientiamobile.wurfl.core.resource.WURFLResources;
import jakarta.servlet.http.HttpServletRequest;

interface m {
  Device a(HttpServletRequest paramHttpServletRequest);
  
  Device a(WURFLRequest paramWURFLRequest);
  
  Device a(String paramString);
  
  EngineTarget a();
  
  void a(EngineTarget paramEngineTarget);
  
  UserAgentPriority b();
  
  void a(UserAgentPriority paramUserAgentPriority);
  
  void a(CacheProvider paramCacheProvider);
  
  void a(WURFLResource paramWURFLResource, WURFLResources paramWURFLResources, String... paramVarArgs);
  
  void a(WURFLResources paramWURFLResources, String... paramVarArgs);
  
  void a(WURFLRequestFactoryWithPriority paramWURFLRequestFactoryWithPriority);
  
  Device b(String paramString);
  
  Device a(String paramString, HttpServletRequest paramHttpServletRequest);
  
  Device a(String paramString, WURFLRequest paramWURFLRequest);
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\m.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
