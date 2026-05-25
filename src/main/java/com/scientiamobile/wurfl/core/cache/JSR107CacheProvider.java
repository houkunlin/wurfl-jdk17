package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;
import net.sf.jsr107cache.Cache;

public class JSR107CacheProvider implements CacheProvider {
   private Cache a;

   public JSR107CacheProvider() {
   }

   public JSR107CacheProvider(Cache var1) {
      this.a = var1;
   }

   public void setCache(Cache var1) {
      this.a = var1;
   }

   public InternalDevice getDevice(String var1) {
      return (InternalDevice)this.a.get(var1);
   }

   public void putDevice(String var1, InternalDevice var2) {
      this.a.put(var1, var2);
   }

   public void clear() {
      this.a.clear();
   }

   public InternalDevice getInternalDeviceFromDeviceId(String var1) {
      return null;
   }
}
