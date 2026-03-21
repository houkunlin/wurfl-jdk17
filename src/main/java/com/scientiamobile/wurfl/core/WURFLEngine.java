package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.cache.CacheProvider;
import com.scientiamobile.wurfl.core.request.UserAgentResolver;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.WURFLRequestFactory;
import com.scientiamobile.wurfl.core.resource.WURFLResource;
import com.scientiamobile.wurfl.core.resource.WURFLResources;
import java.util.Collection;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

public interface WURFLEngine {
  public static final String API_VERSION = "1.9.0.0";
  
  Device getDeviceForRequest(HttpServletRequest paramHttpServletRequest);
  
  Device getDeviceForRequest(WURFLRequest paramWURFLRequest);
  
  Device getDeviceForRequest(String paramString);
  
  void load();
  
  void reload(String paramString);
  
  void reload(String paramString, String[] paramArrayOfString);
  
  void reload(WURFLResource paramWURFLResource, WURFLResource... paramVarArgs);
  
  void reload(WURFLResource paramWURFLResource, WURFLResources paramWURFLResources);
  
  boolean replaceRoot(String paramString);
  
  void applyPatches(String... paramVarArgs);
  
  void applyPatches(WURFLResource... paramVarArgs);
  
  void applyPatches(WURFLResources paramWURFLResources);
  
  void setMarkupResolver(MarkupResolver paramMarkupResolver);
  
  void setCapabilitiesHolderFactory(CapabilitiesHolderFactory paramCapabilitiesHolderFactory);
  
  void setWurflRequestFactory(WURFLRequestFactory paramWURFLRequestFactory);
  
  void setUserAgentResolver(UserAgentResolver paramUserAgentResolver);
  
  void setDeviceProvider(DeviceProvider paramDeviceProvider);
  
  void setCacheProvider(CacheProvider paramCacheProvider);
  
  void setCapabilityFilter(String... paramVarArgs);
  
  void setCapabilityFilter(Collection paramCollection);
  
  EngineTarget getEngineTarget();
  
  void setEngineTarget(EngineTarget paramEngineTarget);
  
  UserAgentPriority getUserAgentPriority();
  
  void setUserAgentPriority(UserAgentPriority paramUserAgentPriority);
  
  WURFLUtils getWURFLUtils();
  
  Set getAllVirtualCapabilities();
  
  Device getDeviceById(String paramString);
  
  Device getDeviceById(String paramString, WURFLRequest paramWURFLRequest);
  
  Device getDeviceById(String paramString, HttpServletRequest paramHttpServletRequest);
  
  String getAPIVersion();
  
  Set getAllMandatoryCapabilities();
  
  Set getAllCapabilities();
  
  String getRootPath();
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\WURFLEngine.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */