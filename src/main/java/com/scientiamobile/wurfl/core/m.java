package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.cache.CacheProvider;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.WURFLRequestFactoryWithPriority;
import com.scientiamobile.wurfl.core.resource.WURFLResource;
import com.scientiamobile.wurfl.core.resource.WURFLResources;
import javax.servlet.http.HttpServletRequest;

interface m {
   Device a(HttpServletRequest var1);

   Device a(WURFLRequest var1);

   Device a(String var1);

   EngineTarget a();

   void a(EngineTarget var1);

   UserAgentPriority b();

   void a(UserAgentPriority var1);

   void a(CacheProvider var1);

   void a(WURFLResource var1, WURFLResources var2, String... var3);

   void a(WURFLResources var1, String... var2);

   void a(WURFLRequestFactoryWithPriority var1);

   Device b(String var1);

   Device a(String var1, HttpServletRequest var2);

   Device a(String var1, WURFLRequest var2);
}
