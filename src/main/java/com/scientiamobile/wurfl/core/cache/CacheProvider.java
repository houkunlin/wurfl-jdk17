package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface CacheProvider {
   Logger logger = LoggerFactory.getLogger(CacheProvider.class);

   InternalDevice getDevice(String var1);

   InternalDevice getInternalDeviceFromDeviceId(String var1);

   void putDevice(String var1, InternalDevice var2);

   void clear();
}
