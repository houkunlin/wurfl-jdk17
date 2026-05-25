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
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class ModelDevices implements Serializable {
   private static final long serialVersionUID = 10L;
   private Map a;
   private LinkedList b;

   public ModelDevices() {
      this.a = CollectionFactory.createConcurrentHashMap();
      this.b = new LinkedList();
   }

   public ModelDevices(ModelDevices var1) {
      this.a = CollectionFactory.createConcurrentHashMap();
      this.b = new LinkedList();
      this.a.putAll(var1.a);
      this.b = (LinkedList)var1.getDeviceIdsByInsertionOrder();
   }

   public ModelDevices(Map var1) {
      this.a = CollectionFactory.createConcurrentHashMap();
      this.b = new LinkedList();
      Validate.notNull(var1);
      Validate.noNullElements(var1.values());
      this.a.putAll(var1);
   }

   public ModelDevices(Collection var1) {
      this.a = CollectionFactory.createConcurrentHashMap();
      this.b = new LinkedList();
      Validate.notNull(var1);
      Validate.noNullElements(var1);

      for(ModelDevice var2 : var1) {
         this.a.put(var2.getID(), var2);
      }

   }

   public ModelDevices(ModelDevice[] var1) {
      this((Collection)Arrays.asList(var1));
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

   public Set getDevices() {
      return new HashSet(this.a.values());
   }

   public Map getDevicesById() {
      return Collections.unmodifiableMap(this.a);
   }

   public Iterator iterator() {
      return this.getDevicesById().values().iterator();
   }

   public ModelDevice getById(String var1) {
      return (ModelDevice)this.a.get(var1);
   }

   public void add(ModelDevice var1) {
      this.a.put(var1.getID(), var1);
      if (this.a.size() > this.b.size()) {
         this.b.add(var1.getID());
      }

   }

   public List getDeviceIdsByInsertionOrder() {
      return this.b;
   }

   public void addAll(Collection var1) {
      Validate.notNull(var1);
      Validate.noNullElements(var1);

      for(ModelDevice var2 : var1) {
         this.a.put(var2.getID(), var2);
      }

   }

   public void addAll(ModelDevices var1) {
      this.a.putAll(var1.a);
   }

   public void remove(ModelDevice var1) {
      this.a.remove(var1.getID());
   }

   public void removeAll(Collection var1) {
      for(ModelDevice var2 : var1) {
         this.a.remove(var2.getID());
      }

   }

   public void removeAll(ModelDevices var1) {
      for(ModelDevice var2 : var1) {
         this.a.remove(var2.getID());
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
      EqualsBuilder var2;
      (var2 = new EqualsBuilder()).appendSuper(this.getClass().isInstance(var1));
      if (var2.isEquals()) {
         var1 = var1;
         var2.append(this.a, var1.a);
      }

      return var2.isEquals();
   }
}
