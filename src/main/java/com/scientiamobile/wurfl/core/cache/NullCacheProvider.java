package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;

public class NullCacheProvider implements CacheProvider {
   public void clear() {
   }

   public void putDevice(String deviceId, InternalDevice device) {
   }

   public InternalDevice getDevice(String deviceId) {
      return null;
   }

   public InternalDevice getInternalDeviceFromDeviceId(String deviceId) {
      return null;
   }
}
