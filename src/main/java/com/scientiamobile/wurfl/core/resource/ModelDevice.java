package com.scientiamobile.wurfl.core.resource;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ModelDevice implements Serializable {
   private static final long serialVersionUID = 10L;
   private String a;
   private String b;
   private String c;
   private boolean d;
   private Map e;
   private Map f;
   private ModelDevice g;
   // $FF: synthetic field
   private static boolean h = !ModelDevice.class.desiredAssertionStatus();

   protected ModelDevice() {
   }

   public ModelDevice(String var1, String var2, String var3, boolean var4, Map var5, Map var6) {
      Validate.notEmpty(var2, "The id must be not null");
      Validate.notEmpty(var3, "The fallBack must be not null");
      Validate.notEmpty(var1, "The userAgent must be not null");
      Validate.notNull(var5, "The capabilities must be not null");
      Validate.notNull(var6, "The groupsByCapability must be not null");
      Validate.noNullElements(var5.values(), "The capabilities can not contain null value");
      Validate.noNullElements(var6.values(), "The capabilities can not contain null value");
      Validate.allElementsOfType(var5.values(), String.class, "The capabilities must be a <String,String> map");
      Validate.allElementsOfType(var5.keySet(), String.class, "The capabilities must be a <String,String> map");
      Validate.allElementsOfType(var6.values(), String.class, "The capabilities must be a <String,String> map");
      Validate.allElementsOfType(var6.keySet(), String.class, "The capabilities must be a <String,String> map");
      Validate.isTrue(var5.keySet().equals(var6.keySet()), "The capabilities and groups must be same Set");
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
      this.e = Collections.unmodifiableMap(var5);
      this.f = Collections.unmodifiableMap(var6);
   }

   public String getUserAgent() {
      return this.a;
   }

   public String getID() {
      return this.b;
   }

   public String getFallBack() {
      return this.c;
   }

   public boolean isActualDeviceRoot() {
      return this.d;
   }

   public Map getCapabilities() {
      return this.e;
   }

   public Map getGroupsByCapability() {
      return this.f;
   }

   public boolean defineCapability(String var1) {
      return this.e.containsKey(var1);
   }

   public String getCapability(String var1) {
      if (!h && !this.defineCapability(var1)) {
         throw new AssertionError(this.b + " do not define " + var1);
      } else {
         return (String)this.e.get(var1);
      }
   }

   public boolean defineGroup(String var1) {
      return this.f.containsValue(var1);
   }

   public Set getGroups() {
      return new HashSet(this.f.values());
   }

   public String getGroupForCapability(String var1) {
      if (!h && !this.defineCapability(var1)) {
         throw new AssertionError();
      } else {
         return (String)this.f.get(var1);
      }
   }

   public Set getCapabilitiesNamesForGroup(String var1) {
      if (!h && !this.defineGroup(var1)) {
         throw new AssertionError();
      } else {
         HashSet var2 = new HashSet();
         Iterator var3 = this.f.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry var4;
            if (((String)(var4 = (Map.Entry)var3.next()).getValue()).equals(var1)) {
               var2.add(var4.getKey());
            }
         }

         return var2;
      }
   }

   public Map getCapabilitiesForGroup(String var1) {
      HashMap var2 = new HashMap();

      for(String var3 : this.getCapabilitiesNamesForGroup(var1)) {
         var2.put(var3, this.e.get(var3));
      }

      return var2;
   }

   public ModelDevice getAncestor() {
      return this.g;
   }

   public void setAncestor(ModelDevice var1) {
      this.g = var1;
   }

   public int hashCode() {
      HashCodeBuilder var1;
      (var1 = new HashCodeBuilder(11, 45)).append(this.getClass()).append(this.b);
      return var1.toHashCode();
   }

   public boolean equals(Object var1) {
      EqualsBuilder var2 = new EqualsBuilder();
      if (var1 instanceof ModelDevice) {
         var1 = var1;
         var2.append(this.b, var1.b);
      } else {
         var2.append(true, false);
      }

      return var2.isEquals();
   }

   public String toString() {
      ToStringBuilder var1;
      (var1 = new ToStringBuilder(this)).append(this.b);
      return var1.toString();
   }

   // $FF: synthetic method
   static String a(ModelDevice var0, String var1) {
      return var0.b = var1;
   }

   // $FF: synthetic method
   static String b(ModelDevice var0, String var1) {
      return var0.a = var1;
   }

   // $FF: synthetic method
   static String c(ModelDevice var0, String var1) {
      return var0.c = var1;
   }

   // $FF: synthetic method
   static boolean a(ModelDevice var0, boolean var1) {
      return var0.d = var1;
   }

   // $FF: synthetic method
   static Map a(ModelDevice var0, Map var1) {
      return var0.e = var1;
   }

   // $FF: synthetic method
   static Map b(ModelDevice var0, Map var1) {
      return var0.f = var1;
   }

   // $FF: synthetic method
   static ModelDevice a(ModelDevice var0, ModelDevice var1) {
      return var0.g = var1;
   }
}
