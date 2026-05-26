package com.scientiamobile.wurfl.core.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public final class WURFLResources {
   private final List<WURFLResource> a = new ArrayList<>();

   public WURFLResources() {
   }

   public WURFLResources(WURFLResource... var1) {
      Validate.notNull(var1, "The resources is null");
      this.a.addAll(Arrays.asList(var1));
   }

   public WURFLResources(Collection<WURFLResource> var1) {
      Validate.notNull(var1, "The resources is null");
      Validate.noNullElements(var1, "The resources contains null value");
      this.a.addAll(var1);
   }

   public final int size() {
      return this.a.size();
   }

   public final WURFLResource get(int var1) {
      return this.a.get(var1);
   }

   public final int indexOf(WURFLResource var1) {
      Validate.notNull(var1, "The resource is null");
      return this.a.indexOf(var1);
   }

   public final void release() {
      Iterator<WURFLResource> var1 = this.a.iterator();

      while(var1.hasNext()) {
         var1.next().release();
      }

   }

   public final void add(WURFLResource var1) {
      Validate.notNull(var1, "The resource must be not null");
      this.a.add(var1);
   }

   public final void remove(WURFLResource var1) {
      Validate.notNull(var1, "The resource must be not null");
      this.a.remove(var1);
   }

   public final Iterator<WURFLResource> iterator() {
      return this.a.iterator();
   }

   public final String toString() {
      StringBuilder var1 = new StringBuilder("[");

      for(int var2 = 0; var2 < this.a.size(); ++var2) {
         WURFLResource var3 = this.a.get(var2);
         var1.append(var3).append("(").append(var3.getInfo()).append(" version: ").append(var3.getVersion()).append(")");
         if (var2 < this.a.size() - 1) {
            var1.append(" - ");
         }
      }

      var1.append("]");
      return var1.toString();
   }

   public final boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof WURFLResources)) {
         return false;
      } else {
         WURFLResources other = (WURFLResources)var1;
         return (new EqualsBuilder()).append(this.a, other.a).isEquals();
      }
   }

   public final int hashCode() {
      HashCodeBuilder var1;
      (var1 = new HashCodeBuilder(53, 79)).append(this.getClass()).append(this.a);
      return var1.toHashCode();
   }
}
