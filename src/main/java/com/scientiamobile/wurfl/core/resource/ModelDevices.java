package com.scientiamobile.wurfl.core.resource;

import com.scientiamobile.wurfl.core.utils.CollectionFactory;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ModelDevices implements Serializable, Iterable<ModelDevice> {
   private static final long serialVersionUID = 10L;
   private Map<String, ModelDevice> a;
   private LinkedList<String> b;

   public ModelDevices() {
      this.a = CollectionFactory.createConcurrentHashMap();
      this.b = new LinkedList<>();
   }

   public ModelDevices(ModelDevices var1) {
      this.a = CollectionFactory.createConcurrentHashMap();
      this.b = new LinkedList<>();
      this.a.putAll(var1.a);
      this.b = new LinkedList<>(var1.getDeviceIdsByInsertionOrder());
   }

   @SuppressWarnings("unchecked")
   public ModelDevices(Map var1) {
      this.a = CollectionFactory.createConcurrentHashMap();
      this.b = new LinkedList<>();
      Validate.notNull(var1, "The devicesById is null");
      Validate.noNullElements(var1.values(), "The devicesById contains null value");
      this.a.putAll((Map<String, ModelDevice>)var1);
   }

   public ModelDevices(Collection<ModelDevice> var1) {
      this.a = CollectionFactory.createConcurrentHashMap();
      this.b = new LinkedList<>();
      Validate.notNull(var1, "The devices is null");
      Validate.noNullElements(var1, "The devices contains null value");

      for(ModelDevice device : var1) {
         this.a.put(device.getID(), device);
      }

   }

   public ModelDevices(ModelDevice[] var1) {
      this(Arrays.asList(var1));
   }

   public int size() {
      return this.a.size();
   }

   public boolean contains(ModelDevice var1) {
      return this.a.containsValue(var1);
   }

   public boolean containsId(String var1) {
      return this.a.containsKey(var1);
   }

   public Set<ModelDevice> getDevices() {
      return new HashSet<>(this.a.values());
   }

   public Map<String, ModelDevice> getDevicesById() {
      return Collections.unmodifiableMap(this.a);
   }

   public Iterator<ModelDevice> iterator() {
      return this.getDevicesById().values().iterator();
   }

   public ModelDevice getById(String var1) {
      return this.a.get(var1);
   }

   public void add(ModelDevice var1) {
      this.a.put(var1.getID(), var1);
      if (this.a.size() > this.b.size()) {
         this.b.add(var1.getID());
      }

   }

   public List<String> getDeviceIdsByInsertionOrder() {
      return this.b;
   }

   public void addAll(Collection<ModelDevice> var1) {
      Validate.notNull(var1, "The devices is null");
      Validate.noNullElements(var1, "The devices contains null value");

      for(ModelDevice device : var1) {
         this.a.put(device.getID(), device);
      }

   }

   public void addAll(ModelDevices var1) {
      this.a.putAll(var1.a);
   }

   public void remove(ModelDevice var1) {
      this.a.remove(var1.getID());
   }

   public void removeAll(Collection<ModelDevice> var1) {
      for(ModelDevice device : var1) {
         this.a.remove(device.getID());
      }

   }

   public void removeAll(ModelDevices var1) {
      for(ModelDevice device : var1) {
         this.a.remove(device.getID());
      }

   }

   public void clear() {
      this.a.clear();
   }

   public int hashCode() {
      HashCodeBuilder var1;
      (var1 = new HashCodeBuilder()).append(this.getClass()).append(this.a);
      return var1.toHashCode();
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ModelDevices)) {
         return false;
      } else {
         ModelDevices other = (ModelDevices)var1;
         return (new EqualsBuilder()).append(this.a, other.a).isEquals();
      }
   }
}
