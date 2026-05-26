package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;

public class NullCacheProvider implements CacheProvider {
   @Override
   public void clear() {
   }

   @Override
   public void putDevice(String deviceId, InternalDevice device) {
   }

   @Override
   public InternalDevice getDevice(String deviceId) {
      return null;
   }

   public InternalDevice getInternalDeviceFromDeviceId(String deviceId) {
      return null;
   }
}
