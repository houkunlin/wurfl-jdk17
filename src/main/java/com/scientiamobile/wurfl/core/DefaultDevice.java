package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.exc.CapabilityNotDefinedException;
import com.scientiamobile.wurfl.core.exc.VirtualCapabilityNotDefinedException;
import com.scientiamobile.wurfl.core.matchers.MatchType;
import com.scientiamobile.wurfl.core.vcap.VirtualCapabilityHandler;
import java.io.Serializable;
import java.util.Map;
import org.apache.commons.lang.Validate;
import org.slf4j.LoggerFactory;

public class DefaultDevice implements EnrichedDevice, Serializable {
   private static final long serialVersionUID = 11L;
   private InternalDevice a;
   private final MatchType b;
   private final String c;
   private final String d;
   private final String e;
   private final MarkupResolver f;
   private transient MarkUp g;
   private VirtualCapabilityHandler h;

   public DefaultDevice(InternalDevice var1, VirtualCapabilityHandler var2, MarkupResolver var3, MatchType var4, String var5, String var6, String var7) {
      Validate.notNull(this.h, "The capabilitiesHandler must be not null");
      Validate.notNull(var3, "The markupResolver must be not null");
      this.a = var1;
      this.f = var3;
      this.b = var4;
      this.d = var5;
      this.c = var6;
      this.e = var7;
      this.h = var2;
   }

   public DefaultDevice(InternalDevice var1, MarkupResolver var2, MatchType var3, String var4, String var5, String var6, VirtualCapabilityHandler var7) {
      Validate.notNull(var7, "The capabilitiesHandler must be not null");
      Validate.notNull(var2, "The markupResolver must be not null");
      this.a = var1;
      this.f = var2;
      this.b = var3;
      this.d = var4;
      this.c = var5;
      this.e = var6;
      this.h = var7;
   }

   public Map getVirtualCapabilities() {
      return this.h.getAllVirtualCapabilities(this);
   }

   public String getVirtualCapability(String var1) {
      return this.h.getVirtualCapability(var1, this);
   }

   public int getVirtualCapabilityAsInt(String var1) {
      return this.h.getVirtualCapabilityAsInt(var1, this);
   }

   public boolean getVirtualCapabilityAsBool(String var1) {
      return this.h.getVirtualCapabilityAsBool(var1, this);
   }

   public MatchType getMatchType() {
      return this.b;
   }

   public String getBucketMatcherName() {
      return this.c;
   }

   public String getMatcherName() {
      return this.d;
   }

   public MarkUp getMarkUp() {
      if (this.g == null) {
         this.g = this.f.getMarkupForDevice(this);
      }

      return this.g;
   }

   public String toString() {
      return "[" + this.getId() + ", match=" + this.getMatchType() + ']';
   }

   public String getCapability(String var1) {
      try {
         return this.a.getCapability(var1);
      } catch (CapabilityNotDefinedException var4) {
         try {
            return this.getVirtualCapability(var1);
         } catch (VirtualCapabilityNotDefinedException var3) {
            throw var4;
         }
      }
   }

   public String getId() {
      return this.a.getId();
   }

   public String getWURFLUserAgent() {
      return this.a.getWURFLUserAgent();
   }

   public int getCapabilityAsInt(String var1) {
      try {
         return this.a.getCapabilityAsInt(var1);
      } catch (CapabilityNotDefinedException var4) {
         try {
            return this.getVirtualCapabilityAsInt(var1);
         } catch (VirtualCapabilityNotDefinedException var3) {
            throw var4;
         }
      }
   }

   public boolean getCapabilityAsBool(String var1) {
      try {
         return this.a.getCapabilityAsBool(var1);
      } catch (CapabilityNotDefinedException var4) {
         try {
            return this.getVirtualCapabilityAsBool(var1);
         } catch (VirtualCapabilityNotDefinedException var3) {
            throw var4;
         }
      }
   }

   public Map getCapabilities() {
      return this.a.getCapabilities();
   }

   public boolean isActualDeviceRoot() {
      return this.a.isActualDeviceRoot();
   }

   public String getDeviceRootId() {
      return this.a.getDeviceRootId();
   }

   public InternalDevice getInternalDevice() {
      return this.a;
   }

   public String getNormalizedUserAgent() {
      return this.e;
   }

   static {
      LoggerFactory.getLogger(h.class);
   }
}
