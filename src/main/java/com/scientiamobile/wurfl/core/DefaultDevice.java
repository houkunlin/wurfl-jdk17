package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.exc.CapabilityNotDefinedException;
import com.scientiamobile.wurfl.core.exc.VirtualCapabilityNotDefinedException;
import com.scientiamobile.wurfl.core.matchers.MatchType;
import com.scientiamobile.wurfl.core.vcap.VirtualCapabilityHandler;
import java.io.Serializable;
import java.util.Map;
import org.apache.commons.lang3.Validate;
import org.slf4j.LoggerFactory;

public class DefaultDevice implements EnrichedDevice, Serializable {
   private static final long serialVersionUID = 11L;
   private transient InternalDevice internalDevice;
   private final MatchType matchType;
   private final String bucketMatcherName;
   private final String matcherName;
   private final String normalizedUserAgent;
   private final transient MarkupResolver markupResolver;
   private transient MarkUp markUp;
   private transient VirtualCapabilityHandler virtualCapabilityHandler;

   public DefaultDevice(InternalDevice internalDevice, VirtualCapabilityHandler virtualCapabilityHandler, MarkupResolver markupResolver, MatchType matchType, String matcherName, String bucketMatcherName, String normalizedUserAgent) {
      Validate.notNull(virtualCapabilityHandler, "The capabilitiesHandler must be not null");
      Validate.notNull(markupResolver, "The markupResolver must be not null");
      this.internalDevice = internalDevice;
      this.markupResolver = markupResolver;
      this.matchType = matchType;
      this.matcherName = matcherName;
      this.bucketMatcherName = bucketMatcherName;
      this.normalizedUserAgent = normalizedUserAgent;
      this.virtualCapabilityHandler = virtualCapabilityHandler;
   }

   public DefaultDevice(InternalDevice internalDevice, MarkupResolver markupResolver, MatchType matchType, String matcherName, String bucketMatcherName, String normalizedUserAgent, VirtualCapabilityHandler virtualCapabilityHandler) {
      Validate.notNull(virtualCapabilityHandler, "The capabilitiesHandler must be not null");
      Validate.notNull(markupResolver, "The markupResolver must be not null");
      this.internalDevice = internalDevice;
      this.markupResolver = markupResolver;
      this.matchType = matchType;
      this.matcherName = matcherName;
      this.bucketMatcherName = bucketMatcherName;
      this.normalizedUserAgent = normalizedUserAgent;
      this.virtualCapabilityHandler = virtualCapabilityHandler;
   }

   public Map<String, String> getVirtualCapabilities() {
      return this.virtualCapabilityHandler.getAllVirtualCapabilities(this);
   }

   public String getVirtualCapability(String virtualCapabilityName) {
      return this.virtualCapabilityHandler.getVirtualCapability(virtualCapabilityName, this);
   }

   public int getVirtualCapabilityAsInt(String virtualCapabilityName) {
      return this.virtualCapabilityHandler.getVirtualCapabilityAsInt(virtualCapabilityName, this);
   }

   public boolean getVirtualCapabilityAsBool(String virtualCapabilityName) {
      return this.virtualCapabilityHandler.getVirtualCapabilityAsBool(virtualCapabilityName, this);
   }

   public MatchType getMatchType() {
      return this.matchType;
   }

   public String getBucketMatcherName() {
      return this.bucketMatcherName;
   }

   public String getMatcherName() {
      return this.matcherName;
   }

   public MarkUp getMarkUp() {
      if (this.markUp == null) {
         this.markUp = this.markupResolver.getMarkupForDevice(this);
      }

      return this.markUp;
   }

   public String toString() {
      return "[" + this.getId() + ", match=" + this.getMatchType() + ']';
   }

   public String getCapability(String capabilityName) {
      try {
         return this.internalDevice.getCapability(capabilityName);
      } catch (CapabilityNotDefinedException capabilityNotDefinedException) {
         try {
            return this.getVirtualCapability(capabilityName);
         } catch (VirtualCapabilityNotDefinedException virtualCapabilityNotDefinedException) {
            throw capabilityNotDefinedException;
         }
      }
   }

   public String getId() {
      return this.internalDevice.getId();
   }

   public String getWURFLUserAgent() {
      return this.internalDevice.getWURFLUserAgent();
   }

   public int getCapabilityAsInt(String capabilityName) {
      try {
         return this.internalDevice.getCapabilityAsInt(capabilityName);
      } catch (CapabilityNotDefinedException capabilityNotDefinedException) {
         try {
            return this.getVirtualCapabilityAsInt(capabilityName);
         } catch (VirtualCapabilityNotDefinedException virtualCapabilityNotDefinedException) {
            throw capabilityNotDefinedException;
         }
      }
   }

   public boolean getCapabilityAsBool(String capabilityName) {
      try {
         return this.internalDevice.getCapabilityAsBool(capabilityName);
      } catch (CapabilityNotDefinedException capabilityNotDefinedException) {
         try {
            return this.getVirtualCapabilityAsBool(capabilityName);
         } catch (VirtualCapabilityNotDefinedException virtualCapabilityNotDefinedException) {
            throw capabilityNotDefinedException;
         }
      }
   }

   public Map<String, String> getCapabilities() {
      return this.internalDevice.getCapabilities();
   }

   public boolean isActualDeviceRoot() {
      return this.internalDevice.isActualDeviceRoot();
   }

   public String getDeviceRootId() {
      return this.internalDevice.getDeviceRootId();
   }

   public InternalDevice getInternalDevice() {
      return this.internalDevice;
   }

   public String getNormalizedUserAgent() {
      return this.normalizedUserAgent;
   }

   static {
      LoggerFactory.getLogger(DefaultDevice.class);
   }
}
