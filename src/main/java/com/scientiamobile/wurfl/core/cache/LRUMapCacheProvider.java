package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.map.LRUMap;

public class LRUMapCacheProvider implements CacheProvider {
   private Map a;

   public LRUMapCacheProvider(int var1, boolean var2) {
      this.a = MapUtils.synchronizedMap(new LRUMap(var1, var2));
   }

   public LRUMapCacheProvider(int var1) {
      this.a = MapUtils.synchronizedMap(new LRUMap(var1));
   }

   public LRUMapCacheProvider() {
      this.a = MapUtils.synchronizedMap(new LRUMap());
   }

   public void clear() {
      logger.info("cache: size " + this.a.size());
      this.a.clear();
      logger.info("cache cleared: size " + this.a.size());
   }

   public InternalDevice getDevice(String var1) {
      return (InternalDevice)this.a.get(var1);
   }

   public void putDevice(String var1, InternalDevice var2) {
      this.a.put(var1, var2);
   }

   public InternalDevice getInternalDeviceFromDeviceId(String var1) {
      return null;
   }
}
