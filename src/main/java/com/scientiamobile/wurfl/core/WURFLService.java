package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.cache.CacheProvider;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.WURFLRequestFactoryWithPriority;
import com.scientiamobile.wurfl.core.resource.WURFLResource;
import com.scientiamobile.wurfl.core.resource.WURFLResources;
import jakarta.servlet.http.HttpServletRequest;

interface WURFLService {
   Device getDevice(HttpServletRequest request);

   Device getDevice(WURFLRequest request);

   Device getDevice(String userAgent);

   EngineTarget getEngineTarget();

   void setEngineTarget(EngineTarget engineTarget);

   UserAgentPriority getUserAgentPriority();

   void setUserAgentPriority(UserAgentPriority priority);

   void setCacheProvider(CacheProvider cacheProvider);

   void reload(WURFLResource wurflResource, WURFLResources wurflResources, String... patches);

   void applyPatches(WURFLResources wurflResources, String... patches);

   void setRequestFactory(WURFLRequestFactoryWithPriority requestFactory);

   Device getDeviceById(String deviceId);

   Device getDeviceById(String deviceId, HttpServletRequest request);

   Device getDeviceById(String deviceId, WURFLRequest request);
}

