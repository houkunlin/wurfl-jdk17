package com.scientiamobile.wurfl.core.resource;

import java.io.Serializable;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.text.StrBuilder;

final class ModelDevicesSnapshot implements Serializable, Comparable {
   private static final long serialVersionUID = 1L;
   private String a;
   private String b;
   private boolean c;
   private ModelDevices d;
   private transient String e;
   private String f;

   public ModelDevicesSnapshot(String var1, String var2, boolean var3, ModelDevices var4, String var5) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
      this.f = var5;
   }

   public final String a() {
      if (this.e == null) {
         StrBuilder var1;
         (var1 = new StrBuilder()).append(this.c ? "Patch" : "Root").append(":").append(this.a);
         if (StringUtils.isNotBlank(this.b)) {
            var1.append(":").append(this.b);
         }

         this.e = var1.toString();
      }

      return this.e;
   }

   public final ModelDevices b() {
      return new ModelDevices(this.d);
   }

   public final int hashCode() {
      HashCodeBuilder var1;
      (var1 = new HashCodeBuilder(33, 55)).append(this.a).append(this.b);
      return var1.toHashCode();
   }

   public final boolean equals(Object var1) {
      EqualsBuilder var2;
      (var2 = new EqualsBuilder()).appendSuper(this.getClass().isInstance(var1));
      if (var2.isEquals()) {
         var1 = var1;
         var2.append(this.a(), var1.a());
      }

      return var2.isEquals();
   }

   public final String toString() {
      ToStringBuilder var1;
      (var1 = new ToStringBuilder(this)).append(this.a).append(this.b);
      return var1.toString();
   }

   final String c() {
      return this.f;
   }

   // $FF: synthetic method
   public final int compareTo(Object var1) {
      ModelDevicesSnapshot var2 = (ModelDevicesSnapshot)var1;
      CompareToBuilder var3;
      (var3 = new CompareToBuilder()).append(this.a, var2.a).append(this.b, var2.b);
      return var3.toComparison();
   }
}
