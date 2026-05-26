package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface CacheProvider {
   Logger logger = LoggerFactory.getLogger(CacheProvider.class);

   InternalDevice getDevice(String deviceId);

   InternalDevice getInternalDeviceFromDeviceId(String deviceId);

   void putDevice(String deviceId, InternalDevice device);

   void clear();
}
