package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

class h implements InternalDevice, Serializable {
   private static final long serialVersionUID = 101L;
   private final String a;
   private final String b;
   private final boolean c;
   private final String d;
   private ModelDevice e;
   private final a f;

   protected h(ModelDevice var1, String var2, a var3) {
      this(var1.getID(), var1.getUserAgent(), var1.isActualDeviceRoot(), var2, var3);
      this.e = var1.getAncestor();
   }

   private h(String var1, String var2, boolean var3, String var4, a var5) {
      Validate.notNull(var5, "The capabilitiesHolder must be not null");
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
      this.f = var5;
   }

   public String getId() {
      return this.a;
   }

   public String getWURFLUserAgent() {
      return this.b;
   }

   public String getCapability(String var1) {
      return this.f.a(var1);
   }

   public final ModelDevice a() {
      return this.e;
   }

   public int getCapabilityAsInt(String var1) {
      return this.f.b(var1);
   }

   public boolean getCapabilityAsBool(String var1) {
      String var2 = var1;
      if ((var1 = this.f.a(var1)) != null && var1.toLowerCase().equals("true")) {
         return true;
      } else if (var1 != null && var1.toLowerCase().equals("false")) {
         return false;
      } else {
         throw new NumberFormatException("WURFL invalid capability value: " + var2 + " expected \"true\" or \"false\", received: \"" + var1 + "\"");
      }
   }

   public Map getCapabilities() {
      Map var1 = this.f.a();
      HashMap var2 = new HashMap(var1.size());
      Iterator var3 = var1.keySet().iterator();

      while(var3.hasNext()) {
         String var4;
         if (!(var4 = (String)var3.next()).startsWith("controlcap_")) {
            var2.put(var4, var1.get(var4));
         }
      }

      return var2;
   }

   public boolean isActualDeviceRoot() {
      return this.c;
   }

   public String getDeviceRootId() {
      String var1 = this.d;
      if (this.d.equals("generic")) {
         var1 = "";
      }

      return var1;
   }

   public boolean equals(Object var1) {
      EqualsBuilder var2;
      (var2 = new EqualsBuilder()).appendSuper(this.getClass().isInstance(var1));
      if (var2.isEquals()) {
         var1 = var1;
         var2.append(this.a, var1.a);
      }

      return var2.isEquals();
   }

   public int hashCode() {
      return (new HashCodeBuilder(63, 89)).append(this.getClass()).append(this.a).toHashCode();
   }

   public String toString() {
      return "[" + this.a + ", match=, matcher=]";
   }
}
