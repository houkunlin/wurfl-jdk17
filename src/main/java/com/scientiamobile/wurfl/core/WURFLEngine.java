package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.cache.CacheProvider;
import com.scientiamobile.wurfl.core.request.UserAgentResolver;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.WURFLRequestFactory;
import com.scientiamobile.wurfl.core.resource.WURFLResource;
import com.scientiamobile.wurfl.core.resource.WURFLResources;
import java.util.Collection;
import java.util.Set;
import jakarta.servlet.http.HttpServletRequest;

public interface WURFLEngine {
   String API_VERSION = "1.9.1.0";

   Device getDeviceForRequest(HttpServletRequest var1);

   Device getDeviceForRequest(WURFLRequest var1);

   Device getDeviceForRequest(String var1);

   void load();

   void reload(String var1);

   void reload(String var1, String[] var2);

   void reload(WURFLResource var1, WURFLResource... var2);

   void reload(WURFLResource var1, WURFLResources var2);

   boolean replaceRoot(String var1);

   void applyPatches(String... var1);

   void applyPatches(WURFLResource... var1);

   void applyPatches(WURFLResources var1);

   void setMarkupResolver(MarkupResolver var1);

   void setCapabilitiesHolderFactory(CapabilitiesHolderFactory var1);

   void setWurflRequestFactory(WURFLRequestFactory var1);

   void setUserAgentResolver(UserAgentResolver var1);

   void setDeviceProvider(DeviceProvider var1);

   void setCacheProvider(CacheProvider var1);

   void setCapabilityFilter(String... var1);

   void setCapabilityFilter(Collection var1);

   EngineTarget getEngineTarget();

   void setEngineTarget(EngineTarget var1);

   UserAgentPriority getUserAgentPriority();

   void setUserAgentPriority(UserAgentPriority var1);

   WURFLUtils getWURFLUtils();

   Set getAllVirtualCapabilities();

   Device getDeviceById(String var1);

   Device getDeviceById(String var1, WURFLRequest var2);

   Device getDeviceById(String var1, HttpServletRequest var2);

   String getAPIVersion();

   Set getAllMandatoryCapabilities();

   Set getAllCapabilities();

   String getRootPath();
}
