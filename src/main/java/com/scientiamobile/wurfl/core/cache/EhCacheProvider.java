package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

public class EhCacheProvider implements CacheProvider {
   private Cache a;

   public EhCacheProvider() {
   }

   public EhCacheProvider(Cache var1) {
      this.a = var1;
   }

   public EhCacheProvider(EhCacheManager var1) {
      this(var1.getDefaultCache());
   }

   public Cache getCache() {
      return this.a;
   }

   public void setCache(Cache var1) {
      this.a = var1;
   }

   public void clear() {
      logger.info("Cache size: " + this.a.getSize());
      this.a.removeAll();
      logger.info("Cache erased. size: " + this.a.getSize());
   }

   public InternalDevice getDevice(String var1) {
      Element var3 = this.a.get(var1);
      InternalDevice var2 = null;
      if (var3 != null) {
         var2 = (InternalDevice)var3.getObjectValue();
      }

      return var2;
   }

   public void putDevice(String var1, InternalDevice var2) {
      Element var3 = new Element(var1, var2);
      this.a.put(var3);
   }

   public InternalDevice getInternalDeviceFromDeviceId(String var1) {
      return null;
   }
}
