package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

public class EhCacheProvider implements CacheProvider {
   private Cache cache;

   public EhCacheProvider() {
   }

   public EhCacheProvider(Cache cache) {
      this.cache = cache;
   }

   public EhCacheProvider(EhCacheManager ehCacheManager) {
      this(ehCacheManager.getDefaultCache());
   }

   public Cache getCache() {
      return this.cache;
   }

   public void setCache(Cache cache) {
      this.cache = cache;
   }

   @Override
   public void clear() {
      logger.info("Cache size: " + this.cache.getSize());
      this.cache.removeAll();
      logger.info("Cache erased. size: " + this.cache.getSize());
   }

   @Override
   public InternalDevice getDevice(String key) {
      Element element = this.cache.get(key);
      InternalDevice device = null;
      if (element != null) {
         device = (InternalDevice)element.getObjectValue();
      }

      return device;
   }

   @Override
   public void putDevice(String key, InternalDevice device) {
      Element element = new Element(key, device);
      this.cache.put(element);
   }

   public InternalDevice getInternalDeviceFromDeviceId(String deviceId) {
      return null;
   }
}
