package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.map.LRUMap;

public class DoubleLRUMapCacheProvider implements CacheProvider {
   private Map a;
   private Map b;

   public DoubleLRUMapCacheProvider(int var1, int var2, boolean var3) {
      this.a = MapUtils.synchronizedMap(new LRUMap(var1, var3));
      this.b = MapUtils.synchronizedMap(new LRUMap(var2, var3));
   }

   public DoubleLRUMapCacheProvider(int var1, int var2) {
      this.a = MapUtils.synchronizedMap(new LRUMap(var1));
      this.b = MapUtils.synchronizedMap(new LRUMap(var2));
   }

   public DoubleLRUMapCacheProvider() {
      this(10000, 2000);
   }

   public void clear() {
      logger.info("UA cache: size " + this.a.size());
      this.a.clear();
      logger.info("UA cache cleared: size " + this.a.size());
      logger.info("device cache: size " + this.b.size());
      this.b.clear();
      logger.info("device cache cleared: size " + this.b.size());
   }

   public InternalDevice getDevice(String var1) {
      String var2;
      InternalDevice var3;
      return (var2 = (String)this.a.get(var1)) != null && (var3 = (InternalDevice)this.b.get(var2)) != null ? var3 : null;
   }

   public void putDevice(String var1, InternalDevice var2) {
      try {
         this.b.put(var2.getId(), var2);
         this.a.put(var1, var2.getId());
      } catch (Exception var3) {
         logger.error("Could not cache " + var1);
      }
   }

   public InternalDevice getInternalDeviceFromDeviceId(String var1) {
      return (InternalDevice)this.b.get(var1);
   }
}
