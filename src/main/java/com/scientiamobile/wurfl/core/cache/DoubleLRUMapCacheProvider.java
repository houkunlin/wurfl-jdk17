package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.map.LRUMap;

public class DoubleLRUMapCacheProvider implements CacheProvider {
   private Map<String, String> deviceIdByUserAgent;
   private Map<String, InternalDevice> deviceById;

   public DoubleLRUMapCacheProvider(int userAgentCacheSize, int deviceCacheSize, boolean scanUntilRemovable) {
      this.deviceIdByUserAgent = MapUtils.synchronizedMap(new LRUMap<>(userAgentCacheSize, scanUntilRemovable));
      this.deviceById = MapUtils.synchronizedMap(new LRUMap<>(deviceCacheSize, scanUntilRemovable));
   }

   public DoubleLRUMapCacheProvider(int userAgentCacheSize, int deviceCacheSize) {
      this.deviceIdByUserAgent = MapUtils.synchronizedMap(new LRUMap<>(userAgentCacheSize));
      this.deviceById = MapUtils.synchronizedMap(new LRUMap<>(deviceCacheSize));
   }

   public DoubleLRUMapCacheProvider() {
      this(10000, 2000);
   }

   public void clear() {
      logger.info("UA cache: size " + this.deviceIdByUserAgent.size());
      this.deviceIdByUserAgent.clear();
      logger.info("UA cache cleared: size " + this.deviceIdByUserAgent.size());
      logger.info("device cache: size " + this.deviceById.size());
      this.deviceById.clear();
      logger.info("device cache cleared: size " + this.deviceById.size());
   }

   public InternalDevice getDevice(String userAgent) {
      String deviceId;
      InternalDevice device;
      return (deviceId = this.deviceIdByUserAgent.get(userAgent)) != null && (device = this.deviceById.get(deviceId)) != null ? device : null;
   }

   public void putDevice(String userAgent, InternalDevice device) {
      try {
         this.deviceById.put(device.getId(), device);
         this.deviceIdByUserAgent.put(userAgent, device.getId());
      } catch (RuntimeException e) {
         logger.error("Could not cache " + userAgent);
      }
   }

   public InternalDevice getInternalDeviceFromDeviceId(String deviceId) {
      return this.deviceById.get(deviceId);
   }
}
