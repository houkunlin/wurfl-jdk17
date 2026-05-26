package com.scientiamobile.wurfl.core.resource;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ModelDevice implements Serializable {
   private static final long serialVersionUID = 10L;
   private String userAgent;
   private String id;
   private String fallBack;
   private boolean actualDeviceRoot;
   private Map<String, String> capabilities;
   private Map<String, String> groupsByCapability;
   private ModelDevice ancestor;
   private static boolean ASSERTIONS_DISABLED = !ModelDevice.class.desiredAssertionStatus();

   protected ModelDevice() {
   }

   public ModelDevice(String var1, String var2, String var3, boolean var4, Map<String, String> var5, Map<String, String> var6) {
      Validate.notEmpty(var2, "The id must be not null");
      Validate.notEmpty(var3, "The fallBack must be not null");
      Validate.notEmpty(var1, "The userAgent must be not null");
      Validate.notNull(var5, "The capabilities must be not null");
      Validate.notNull(var6, "The groupsByCapability must be not null");
      Validate.noNullElements(var5.values(), "The capabilities can not contain null value");
      Validate.noNullElements(var6.values(), "The capabilities can not contain null value");
      Validate.isTrue(var5.keySet().equals(var6.keySet()), "The capabilities and groups must be same Set");
      this.userAgent = var1;
      this.id = var2;
      this.fallBack = var3;
      this.actualDeviceRoot = var4;
      this.capabilities = Collections.unmodifiableMap(var5);
      this.groupsByCapability = Collections.unmodifiableMap(var6);
   }

   public String getUserAgent() {
      return this.userAgent;
   }

   public String getID() {
      return this.id;
   }

   public String getFallBack() {
      return this.fallBack;
   }

   public boolean isActualDeviceRoot() {
      return this.actualDeviceRoot;
   }

   public Map<String, String> getCapabilities() {
      return this.capabilities;
   }

   public Map<String, String> getGroupsByCapability() {
      return this.groupsByCapability;
   }

   public boolean defineCapability(String var1) {
      return this.capabilities.containsKey(var1);
   }

   public String getCapability(String var1) {
      if (!ASSERTIONS_DISABLED && !this.defineCapability(var1)) {
         throw new AssertionError(this.id + " do not define " + var1);
      } else {
         return this.capabilities.get(var1);
      }
   }

   public boolean defineGroup(String var1) {
      return this.groupsByCapability.containsValue(var1);
   }

   public Set<String> getGroups() {
      return new HashSet<>(this.groupsByCapability.values());
   }

   public String getGroupForCapability(String var1) {
      if (!ASSERTIONS_DISABLED && !this.defineCapability(var1)) {
         throw new AssertionError();
      } else {
         return this.groupsByCapability.get(var1);
      }
   }

   public Set<String> getCapabilitiesNamesForGroup(String var1) {
      if (!ASSERTIONS_DISABLED && !this.defineGroup(var1)) {
         throw new AssertionError();
      } else {
         HashSet<String> var2 = new HashSet<>();
         Iterator<Map.Entry<String, String>> var3 = this.groupsByCapability.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry<String, String> var4;
            if ((var4 = var3.next()).getValue().equals(var1)) {
               var2.add(var4.getKey());
            }
         }

         return var2;
      }
   }

   public Map<String, String> getCapabilitiesForGroup(String var1) {
      HashMap<String, String> var2 = new HashMap<>();

      for(String capabilityName : this.getCapabilitiesNamesForGroup(var1)) {
         var2.put(capabilityName, this.capabilities.get(capabilityName));
      }

      return var2;
   }

   public ModelDevice getAncestor() {
      return this.ancestor;
   }

   public void setAncestor(ModelDevice var1) {
      this.ancestor = var1;
   }

   public int hashCode() {
      HashCodeBuilder var1;
      (var1 = new HashCodeBuilder(11, 45)).append(this.getClass()).append(this.id);
      return var1.toHashCode();
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ModelDevice)) {
         return false;
      } else {
         ModelDevice other = (ModelDevice)var1;
         return (new EqualsBuilder()).append(this.id, other.id).isEquals();
      }
   }

   public String toString() {
      ToStringBuilder var1;
      (var1 = new ToStringBuilder(this)).append(this.id);
      return var1.toString();
   }

   final void setUserAgent(String var1) {
      this.userAgent = var1;
   }

   final void setId(String var1) {
      this.id = var1;
   }

   final void setFallBack(String var1) {
      this.fallBack = var1;
   }

   final void setActualDeviceRoot(boolean var1) {
      this.actualDeviceRoot = var1;
   }

   final void setCapabilities(Map<String, String> var1) {
      this.capabilities = var1;
   }

   final void setGroupsByCapability(Map<String, String> var1) {
      this.groupsByCapability = var1;
   }
}
