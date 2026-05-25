package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;

public class NullCacheProvider implements CacheProvider {
   public void clear() {
   }

   public void putDevice(String var1, InternalDevice var2) {
   }

   public InternalDevice getDevice(String var1) {
      return null;
   }

   public InternalDevice getInternalDeviceFromDeviceId(String var1) {
      return null;
   }
}
