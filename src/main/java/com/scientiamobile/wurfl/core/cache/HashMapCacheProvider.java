package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;
import com.scientiamobile.wurfl.core.utils.CollectionFactory;
import java.util.Map;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HashMapCacheProvider implements CacheProvider {
   private Map<String, InternalDevice> cache;
   private int initialCapacity;
   private float loadFactor;
   private int concurrentWrites;
   private static final Logger logger = LoggerFactory.getLogger(HashMapCacheProvider.class);

   public HashMapCacheProvider() {
      this(60000);
   }

   public HashMapCacheProvider(int initialCapacity) {
      this(initialCapacity, 0.75F);
   }

   public HashMapCacheProvider(int initialCapacity, float loadFactor) {
      this(initialCapacity, loadFactor, 16);
   }

   public HashMapCacheProvider(int initialCapacity, float loadFactor, int concurrentWrites) {
      this.initialCapacity = 6000;
      this.loadFactor = 0.75F;
      this.concurrentWrites = 16;
      
      this.initialCapacity = initialCapacity;
      this.loadFactor = loadFactor;
      this.concurrentWrites = concurrentWrites;
      this.cache = CollectionFactory.createConcurrentHashMap(initialCapacity, loadFactor, concurrentWrites);
      if (logger.isInfoEnabled()) {
         StringBuffer builder;
         (builder = new StringBuffer("Created HashMapCacheProvider with initial capacity: ")).append(initialCapacity);
         builder.append(" load factor: ");
         builder.append(loadFactor);
         builder.append(" concurrent writes: ");
         builder.append(concurrentWrites);
         logger.info(builder.toString());
      }

   }

   public int getInitialCapacity() {
      return this.initialCapacity;
   }

   public float getLoadFactor() {
      return this.loadFactor;
   }

   public int getConcurrentWrites() {
      return this.concurrentWrites;
   }

   @Override
   public void clear() {
      logger.info("Cache size: {}", this.cache.size());
      this.cache.clear();
      logger.info("Cache erased");
   }

   @Override
   public InternalDevice getDevice(String key) {
      Validate.notNull(key, "The key is null");
      return this.cache.get(key);
   }

   @Override
   public void putDevice(String key, InternalDevice device) {
      Validate.notNull(key, "The key is null");
      this.cache.put(key, device);
   }

   @Override
   public String toString() {
      return (new ToStringBuilder(this))
         .append("initialCapacity", this.initialCapacity)
         .append("loadFactor", this.loadFactor)
         .append("concurrentWrites", this.concurrentWrites)
         .toString();
   }

   public InternalDevice getInternalDeviceFromDeviceId(String deviceId) {
      return null;
   }
}
