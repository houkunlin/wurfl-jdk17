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
   private final List<WURFLResource> resources = new ArrayList<>();

   public WURFLResources() {
   }

   public WURFLResources(WURFLResource... resources) {
      Validate.notNull(resources, "The resources is null");
      this.resources.addAll(Arrays.asList(resources));
   }

   public WURFLResources(Collection<WURFLResource> resources) {
      Validate.notNull(resources, "The resources is null");
      Validate.noNullElements(resources, "The resources contains null value");
      this.resources.addAll(resources);
   }

   public final int size() {
      return this.resources.size();
   }

   public final WURFLResource get(int index) {
      return this.resources.get(index);
   }

   public final int indexOf(WURFLResource resource) {
      Validate.notNull(resource, "The resource is null");
      return this.resources.indexOf(resource);
   }

   public final void release() {
      Iterator<WURFLResource> iterator = this.resources.iterator();

      while(iterator.hasNext()) {
         iterator.next().release();
      }

   }

   public final void add(WURFLResource resource) {
      Validate.notNull(resource, "The resource must be not null");
      this.resources.add(resource);
   }

   public final void remove(WURFLResource resource) {
      Validate.notNull(resource, "The resource must be not null");
      this.resources.remove(resource);
   }

   public final Iterator<WURFLResource> iterator() {
      return this.resources.iterator();
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder("[");

      for(int i = 0; i < this.resources.size(); ++i) {
         WURFLResource resource = this.resources.get(i);
         builder.append(resource).append("(").append(resource.getInfo()).append(" version: ").append(resource.getVersion()).append(")");
         if (i < this.resources.size() - 1) {
            builder.append(" - ");
         }
      }

      builder.append("]");
      return builder.toString();
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (!(object instanceof WURFLResources)) {
         return false;
      } else {
         WURFLResources other = (WURFLResources)object;
         return (new EqualsBuilder()).append(this.resources, other.resources).isEquals();
      }
   }

   @Override
   public int hashCode() {
      HashCodeBuilder hashCodeBuilder;
      hashCodeBuilder = new HashCodeBuilder(53, 79);
      hashCodeBuilder.append(this.getClass()).append(this.resources);
      return hashCodeBuilder.toHashCode();
   }
}
