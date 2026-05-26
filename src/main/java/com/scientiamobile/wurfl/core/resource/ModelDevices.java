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

@SuppressWarnings("serial")
public class ModelDevices implements Serializable, Iterable<ModelDevice> {
   private static final long serialVersionUID = 10L;
   @SuppressWarnings("serial")
   private Map<String, ModelDevice> devicesById;
   private LinkedList<String> deviceIdsByInsertionOrder;

   public ModelDevices() {
      this.devicesById = CollectionFactory.createConcurrentHashMap();
      this.deviceIdsByInsertionOrder = new LinkedList<>();
   }

   public ModelDevices(ModelDevices modelDevices) {
      this.devicesById = CollectionFactory.createConcurrentHashMap();
      this.deviceIdsByInsertionOrder = new LinkedList<>();
      this.devicesById.putAll(modelDevices.devicesById);
      this.deviceIdsByInsertionOrder = new LinkedList<>(modelDevices.getDeviceIdsByInsertionOrder());
   }

   @SuppressWarnings({"unchecked", "rawtypes"})
   public ModelDevices(Map devicesById) {
      this.devicesById = CollectionFactory.createConcurrentHashMap();
      this.deviceIdsByInsertionOrder = new LinkedList<>();
      Validate.notNull(devicesById, "The devicesById is null");
      Validate.noNullElements(devicesById.values(), "The devicesById contains null value");
      this.devicesById.putAll((Map<String, ModelDevice>)devicesById);
   }

   public ModelDevices(Collection<ModelDevice> devices) {
      this.devicesById = CollectionFactory.createConcurrentHashMap();
      this.deviceIdsByInsertionOrder = new LinkedList<>();
      Validate.notNull(devices, "The devices is null");
      Validate.noNullElements(devices, "The devices contains null value");

      for(ModelDevice device : devices) {
         this.devicesById.put(device.getID(), device);
      }

   }

   public ModelDevices(ModelDevice[] devices) {
      this(Arrays.asList(devices));
   }

   public int size() {
      return this.devicesById.size();
   }

   public boolean contains(ModelDevice device) {
      return this.devicesById.containsValue(device);
   }

   public boolean containsId(String deviceId) {
      return this.devicesById.containsKey(deviceId);
   }

   public Set<ModelDevice> getDevices() {
      return new HashSet<>(this.devicesById.values());
   }

   public Map<String, ModelDevice> getDevicesById() {
      return Collections.unmodifiableMap(this.devicesById);
   }

   public Iterator<ModelDevice> iterator() {
      return this.getDevicesById().values().iterator();
   }

   public ModelDevice getById(String deviceId) {
      return this.devicesById.get(deviceId);
   }

   public void add(ModelDevice device) {
      this.devicesById.put(device.getID(), device);
      if (this.devicesById.size() > this.deviceIdsByInsertionOrder.size()) {
         this.deviceIdsByInsertionOrder.add(device.getID());
      }

   }

   public List<String> getDeviceIdsByInsertionOrder() {
      return this.deviceIdsByInsertionOrder;
   }

   public void addAll(Collection<ModelDevice> devices) {
      Validate.notNull(devices, "The devices is null");
      Validate.noNullElements(devices, "The devices contains null value");

      for(ModelDevice device : devices) {
         this.devicesById.put(device.getID(), device);
      }

   }

   public void addAll(ModelDevices devices) {
      this.devicesById.putAll(devices.devicesById);
   }

   public void remove(ModelDevice device) {
      this.devicesById.remove(device.getID());
   }

   public void removeAll(Collection<ModelDevice> devices) {
      for(ModelDevice device : devices) {
         this.devicesById.remove(device.getID());
      }

   }

   public void removeAll(ModelDevices devices) {
      for(ModelDevice device : devices) {
         this.devicesById.remove(device.getID());
      }

   }

   public void clear() {
      this.devicesById.clear();
   }

   public int hashCode() {
      HashCodeBuilder hashCodeBuilder;
      (hashCodeBuilder = new HashCodeBuilder()).append(this.getClass()).append(this.devicesById);
      return hashCodeBuilder.toHashCode();
   }

   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (!(object instanceof ModelDevices)) {
         return false;
      } else {
         ModelDevices other = (ModelDevices)object;
         return (new EqualsBuilder()).append(this.devicesById, other.devicesById).isEquals();
      }
   }
}
