package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;
import com.scientiamobile.wurfl.core.utils.CollectionFactory;
import java.util.Map;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HashMapCacheProvider implements CacheProvider {
   private Map a;
   private int b;
   private float c;
   private int d;
   private final transient Logger e;

   public HashMapCacheProvider() {
      this(60000);
   }

   public HashMapCacheProvider(int var1) {
      this(var1, 0.75F);
   }

   public HashMapCacheProvider(int var1, float var2) {
      this(var1, var2, 16);
   }

   public HashMapCacheProvider(int var1, float var2, int var3) {
      this.b = 6000;
      this.c = 0.75F;
      this.d = 16;
      this.e = LoggerFactory.getLogger(this.getClass());
      this.b = var1;
      this.c = var2;
      this.d = var3;
      this.a = CollectionFactory.createConcurrentHashMap(var1, var2, var3);
      if (this.e.isInfoEnabled()) {
         StringBuffer var4;
         (var4 = new StringBuffer("Created HashMapCacheProvider with initial capacity: ")).append(var1);
         var4.append(" load factor: ");
         var4.append(var2);
         var4.append(" concurrent writes: ");
         var4.append(var3);
         this.e.info(var4.toString());
      }

   }

   public int getInitialCapacity() {
      return this.b;
   }

   public float getLoadFactor() {
      return this.c;
   }

   public int getConcurrentWrites() {
      return this.d;
   }

   public void clear() {
      this.e.info("Cache size: " + this.a.size());
      this.a.clear();
      this.e.info("Cache erased");
   }

   public InternalDevice getDevice(String var1) {
      Validate.notNull(var1, "The key is null");
      return (InternalDevice)this.a.get(var1);
   }

   public void putDevice(String var1, InternalDevice var2) {
      Validate.notNull(var1, "The key is null");
      this.a.put(var1, var2);
   }

   public String toString() {
      return (new ToStringBuilder(this)).append("initialCapacity", this.b).append("loadFactor", this.c).append("concurrentWrites", this.d).toString();
   }

   public InternalDevice getInternalDeviceFromDeviceId(String var1) {
      return null;
   }
}
