package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.map.LRUMap;

public class LRUMapCacheProvider implements CacheProvider {
   private Map<String, InternalDevice> cache;

   public LRUMapCacheProvider(int cacheSize, boolean scanUntilRemovable) {
      this.cache = MapUtils.synchronizedMap(new LRUMap<>(cacheSize, scanUntilRemovable));
   }

   public LRUMapCacheProvider(int cacheSize) {
      this.cache = MapUtils.synchronizedMap(new LRUMap<>(cacheSize));
   }

   public LRUMapCacheProvider() {
      this.cache = MapUtils.synchronizedMap(new LRUMap<>());
   }

   public void clear() {
      logger.info("cache: size " + this.cache.size());
      this.cache.clear();
      logger.info("cache cleared: size " + this.cache.size());
   }

   public InternalDevice getDevice(String key) {
      return this.cache.get(key);
   }

   public void putDevice(String key, InternalDevice device) {
      this.cache.put(key, device);
   }

   public InternalDevice getInternalDeviceFromDeviceId(String deviceId) {
      return null;
   }
}
