package com.scientiamobile.wurfl.core.resource;

import com.scientiamobile.wurfl.core.exc.CapabilityNotDefinedException;
import com.scientiamobile.wurfl.core.exc.DeviceNotDefinedException;
import com.scientiamobile.wurfl.core.exc.GroupNotDefinedException;
import com.scientiamobile.wurfl.core.resource.exc.DeviceNotInModelException;
import com.scientiamobile.wurfl.core.resource.exc.GenericNotDefinedException;
import com.scientiamobile.wurfl.core.resource.exc.OrphanHierarchyException;
import com.scientiamobile.wurfl.core.utils.CollectionFactory;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultWURFLModel implements WURFLModel {
   private Map<String, ModelDevice> devicesById;
   private List<String> deviceIdsByInsertionOrder;
   private final Map<String, String> deviceIdToAncestorIdCache;
   private final Set<String> familyDeviceIds;
   private ModelDevice genericDevice;
   private String version;
   private String smid;
   private Integer capabilityCount;
   private final Logger log;

   public DefaultWURFLModel(WURFLResource rootResource, String... includedCapabilities) {
      this(rootResource, new WURFLResources(), includedCapabilities);
   }

   public DefaultWURFLModel(WURFLResource rootResource, WURFLResources patchResources, String... includedCapabilities) {
      this.deviceIdToAncestorIdCache = CollectionFactory.createConcurrentHashMap();
      this.familyDeviceIds = new HashSet<>();
      this.log = LoggerFactory.getLogger(this.getClass());
      this.loadFromRootResource(rootResource, patchResources, includedCapabilities);
   }

   private final synchronized void loadFromRootResource(WURFLResource rootResource, WURFLResources patchResources, String... includedCapabilities) {
      Validate.notNull(rootResource, "The root resource must be not null.");
      if (patchResources == null) {
         patchResources = new WURFLResources();
      }

      this.deviceIdToAncestorIdCache.clear();
      this.genericDevice = null;
      this.capabilityCount = null;
      ModelDevicesSnapshot snapshot = rootResource.getData(includedCapabilities);
      this.version = snapshot.getSnapshotKey();
      this.smid = snapshot.getSmid();
      ModelDevices devices = snapshot.copyDevices();
      ModelDevices devicesCopyForVerification = new ModelDevices(devices);
      this.deviceIdsByInsertionOrder = devices.getDeviceIdsByInsertionOrder();
      ModelDevicesConsistencyVerifier.verifyModelDevices(devicesCopyForVerification);
      this.applyPatchesAndRebuild(patchResources, devicesCopyForVerification, includedCapabilities);
      if (patchResources.size() == 0) {
         setAncestors(devicesCopyForVerification);
      }

   }

   private final synchronized void applyPatchesAndRebuild(WURFLResources patchResources, ModelDevices devices, String... includedCapabilities) {
      int genericDevicesCount = 0;
      int rootDevicesCount = 0;

      for(int i = 0; patchResources != null && i < patchResources.size(); ++i) {
         ModelDevices patchDevices;
         ModelDevicesSnapshot patchSnapshot;
         ModelDevicesConsistencyVerifier.verifyNoRedefinedDevices(patchDevices = (patchSnapshot = patchResources.get(i).getData(includedCapabilities)).copyDevices(), devices);
         String mergedVersion = new StringBuilder().append(StringUtils.defaultString(this.version)).append("; ").append(patchSnapshot.getSnapshotKey()).toString();
         devices = ModelDevicesPatchMerger.merge(devices, patchDevices);
         this.deviceIdsByInsertionOrder = devices.getDeviceIdsByInsertionOrder();
         ModelDevicesConsistencyVerifier.verifyModelDevices(devices);
         this.version = mergedVersion;
      }

      ModelDevicesConsistencyVerifier.verifyModelDevices(devices);
      this.devicesById = CollectionFactory.createConcurrentHashMap();
      this.devicesById.putAll(devices.getDevicesById());
      setAncestors(devices);
      Iterator<ModelDevice> iterator = this.devicesById.values().iterator();

      while(iterator.hasNext()) {
         ModelDevice device = iterator.next();
         if (device.isActualDeviceRoot()) {
            ++rootDevicesCount;
         } else {
            String fallbackId;
            if ((fallbackId = device.getFallBack()).equals("generic") || fallbackId.equals("generic_mobile")) {
               this.familyDeviceIds.add(device.getID());
            }
         }

         if (this.getDeviceAncestor(device).getID().equals("generic")) {
            ++genericDevicesCount;
         }
      }

      this.genericDevice = this.devicesById.get("generic");
      if (this.log.isInfoEnabled()) {
         this.log.info("WURFLModel version: {}; devices: {} root devices: {}; families: {}; generic devices: {}", this.version, this.devicesById.size(), rootDevicesCount, this.familyDeviceIds.size(), genericDevicesCount);
      }

   }

   private static void setAncestors(ModelDevices devices) {
      if (devices != null) {
         Iterator iterator = devices.getDevices().iterator();

         while(iterator.hasNext()) {
            ModelDevice device = (ModelDevice)iterator.next();
            if (device.getFallBack() != null && devices.containsId(device.getFallBack())) {
               device.setAncestor(devices.getById(device.getFallBack()));
            }
         }

      }
   }

   public String getVersion() {
      return this.version;
   }

   public ModelDevice getDeviceById(String deviceId) {
      Validate.notEmpty(deviceId, "The id must be not null");
      ModelDevice device;
      if ((device = this.devicesById.get(deviceId)) == null) {
         throw new DeviceNotDefinedException(deviceId);
      } else {
         return device;
      }
   }

   public Set getDevices(Set deviceIds) {
      Validate.notNull(deviceIds, "The devicesIds must be not null Set");
      Validate.noNullElements(deviceIds, "The devicesIds must not containing null elements");
      for(Object id : deviceIds) {
         Validate.isTrue(id instanceof String, "The devicesIds must containing right devicesById id");
      }
      HashSet<ModelDevice> devices = new HashSet<>();
      Iterator deviceIdIterator = deviceIds.iterator();

      while(deviceIdIterator.hasNext()) {
         devices.add(this.getDeviceById((String)deviceIdIterator.next()));
      }

      return devices;
   }

   public Set getAllDevices() {
      TreeSet<ModelDevice> devices;
      (devices = new TreeSet<>(ModelDeviceUserAgentComparator.INSTANCE)).addAll(this.devicesById.values());
      return devices;
   }

   public List getAllDevicesAsList() {
      ArrayList<ModelDevice> devices = new ArrayList<>(this.deviceIdsByInsertionOrder.size());
      Iterator deviceIdIterator = this.deviceIdsByInsertionOrder.iterator();

      while(deviceIdIterator.hasNext()) {
         devices.add(this.devicesById.get(deviceIdIterator.next()));
      }

      return devices;
   }

   public Set<String> getAllDevicesId() {
      HashSet<String> deviceIds = new HashSet<>();
      deviceIds.addAll(this.devicesById.keySet());
      return deviceIds;
   }

   public List<ModelDevice> getDeviceHierarchy(ModelDevice device) {
      Validate.notNull(device, "The device must be not null");

      LinkedList<ModelDevice> hierarchy = new LinkedList<>();
      for(; !"generic".equals(device.getID()); device = this.getDeviceFallback(device)) {
         hierarchy.addFirst(device);
      }

      hierarchy.addFirst(device);
      return hierarchy;
   }

   public ModelDevice getDeviceFallback(ModelDevice device) {
      Validate.notNull(device, "The device must be not null");

      try {
         ModelDevice fallbackDevice = this.getDeviceById(device.getFallBack());
         return fallbackDevice;
      } catch (DeviceNotDefinedException e) {
         throw new DeviceNotInModelException(device);
      }
   }

   public ModelDevice getDeviceAncestor(ModelDevice device) {
      Validate.notNull(device, "The device must be not null");
      String deviceId = device.getID();
      String ancestorId;
      if ((ancestorId = this.deviceIdToAncestorIdCache.get(deviceId)) != null) {
         return this.getDeviceById(ancestorId);
      } else {
         ModelDevice deviceOrAncestor = device;
         ModelDevice genericDevice = this.getGenericDevice();
         List<ModelDevice> deviceHierarchy = this.getDeviceHierarchy(device);
         for(int i = deviceHierarchy.size() - 1; i >= 0 && !deviceOrAncestor.isActualDeviceRoot() && !genericDevice.equals(deviceOrAncestor); --i) {
            deviceOrAncestor = deviceHierarchy.get(i);
         }

         if (!deviceOrAncestor.isActualDeviceRoot() && !genericDevice.equals(deviceOrAncestor)) {
            throw new RuntimeException("Hierarchy is invalid");
         } else {
            String computedAncestorId = deviceOrAncestor.getID();
            this.deviceIdToAncestorIdCache.put(deviceId, computedAncestorId);
            return deviceOrAncestor;
         }
      }
   }

   public boolean isDeviceDefined(String deviceId) {
      Validate.notEmpty(deviceId, "The deviceId must be not null");
      return this.devicesById.containsKey(deviceId);
   }

   public int size() {
      return this.devicesById.size();
   }

   public Set getAllGroups() {
      return this.getGenericDevice().getGroups();
   }

   public boolean isGroupDefined(String groupId) {
      Validate.notEmpty(groupId, "The groupId must be not null");
      return this.getGenericDevice().defineGroup(groupId);
   }

   public String getGroupByCapability(String capabilityName) {
      Validate.notEmpty(capabilityName, "The capabilityName must be not null");
      ModelDevice genericDevice = this.getGenericDevice();
      if (!genericDevice.defineCapability(capabilityName)) {
         throw new CapabilityNotDefinedException(capabilityName);
      } else {
         return genericDevice.getGroupForCapability(capabilityName);
      }
   }

   public void reload(WURFLResource rootResource, WURFLResources patchResources, String... includedCapabilities) {
      this.log.info("about to reload the WURFL Model");
      this.loadFromRootResource(rootResource, patchResources, includedCapabilities);
   }

   public void applyPatches(WURFLResources patchResources, String... includedCapabilities) {
      this.applyPatchesAndRebuild(patchResources, new ModelDevices(this.devicesById), includedCapabilities);
   }

   public Set getAllCapabilities() {
      ModelDevice genericDevice = this.getGenericDevice();
      return new HashSet<>(genericDevice.getCapabilities().keySet());
   }

   public Integer getCapabilityCount() {
      if (this.capabilityCount == null || this.capabilityCount == 0) {
         this.capabilityCount = this.getAllCapabilities().size();
      }

      return this.capabilityCount;
   }

   public boolean isCapabilityDefined(String capabilityName) {
      Validate.notEmpty(capabilityName, "The capability must be not null");
      return this.getGenericDevice().defineCapability(capabilityName);
   }

   public Set getCapabilitiesForGroup(String groupId) {
      Validate.notEmpty(groupId, "The groupId must be not null");
      ModelDevice genericDevice = this.getGenericDevice();
      if (!genericDevice.defineGroup(groupId)) {
         throw new GroupNotDefinedException(groupId);
      } else {
         return genericDevice.getCapabilitiesNamesForGroup(groupId);
      }
   }

   public ModelDevice getDeviceWhereCapabilityIsDefined(ModelDevice rootDevice, String capabilityName) {
      Validate.notNull(rootDevice, "The rootDevice must be not null");
      Validate.notEmpty(capabilityName, "The name must be not null");
      List deviceHierarchy = this.getDeviceHierarchy(rootDevice);
      for(int i = deviceHierarchy.size() - 1; i >= 0; --i) {
         ModelDevice device = (ModelDevice)deviceHierarchy.get(i);
         if (device.defineCapability(capabilityName)) {
            return device;
         }

         if ("generic_mobile".equals(device.getID())) {
            throw new CapabilityNotDefinedException(capabilityName);
         }
      }

      throw new RuntimeException(new OrphanHierarchyException(deviceHierarchy));
   }

   public Set getRootDevicesIds() {
      HashSet<String> rootDeviceIds = new HashSet<>();
      Iterator<ModelDevice> iterator = this.devicesById.values().iterator();

      while(iterator.hasNext()) {
         ModelDevice device = iterator.next();
         if (device.isActualDeviceRoot()) {
            rootDeviceIds.add(device.getID());
         }
      }

      return rootDeviceIds;
   }

   private ModelDevice getGenericDevice() {
      if (this.genericDevice != null) {
         return this.genericDevice;
      } else {
         ModelDevice genericDevice;
         if ((genericDevice = this.devicesById.get("generic")) == null && this.devicesById.size() > 0) {
            throw new RuntimeException(new GenericNotDefinedException());
         } else {
            this.genericDevice = genericDevice;
            return genericDevice;
         }
      }
   }

   public String toString() {
      ToStringBuilder out;
      (out = new ToStringBuilder(this)).append(this.version);
      return out.toString();
   }
}
