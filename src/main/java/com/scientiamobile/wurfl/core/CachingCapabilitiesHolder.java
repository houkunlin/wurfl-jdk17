package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.exc.CapabilityNotDefinedException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
class CachingCapabilitiesHolder extends CapabilitiesHolder implements Serializable {
   private static final long serialVersionUID = 100L;
   private int minCacheSize;
   private transient CapabilitiesProvider capabilitiesProvider;
   private Map<String, String> capabilitiesCache;
   private static boolean assertionsDisabled = !CachingCapabilitiesHolder.class.desiredAssertionStatus();

   public CachingCapabilitiesHolder(CapabilitiesProvider capabilitiesProvider, int minCacheSize) {
      LoggerFactory.getLogger(CachingCapabilitiesHolder.class);
      this.minCacheSize = 39;
      this.capabilitiesProvider = capabilitiesProvider;
      if (minCacheSize > this.minCacheSize) {
         this.minCacheSize = minCacheSize;
      }

      this.capabilitiesCache = new HashMap<>(50);
   }

   @Override
   public String getCapability(String capabilityName) {
      String capabilityValue;
      if ((capabilityValue = this.capabilitiesProvider.getCapability(this.capabilitiesCache, capabilityName)) == null) {
         throw new CapabilityNotDefinedException(capabilityName);
      } else {
         return capabilityValue;
      }
   }

   @Override
   public Map<String, String> getCapabilities() {
      if (this.capabilitiesCache == null || this.capabilitiesCache.size() < this.minCacheSize) {
         if (this.capabilitiesProvider == null) {
            throw new IllegalStateException("The device must be initialized before serialization");
         }

         this.capabilitiesCache = this.capabilitiesProvider.getAllCapabilities();
      }

      if (!assertionsDisabled && this.capabilitiesCache == null) {
         throw new AssertionError();
      } else {
         return this.capabilitiesCache;
      }
   }

   private void writeObject(ObjectOutputStream oos) throws IOException {
      if (this.capabilitiesCache == null) {
         this.capabilitiesCache = this.capabilitiesProvider.getAllCapabilities();
      }

      oos.defaultWriteObject();
   }
}
