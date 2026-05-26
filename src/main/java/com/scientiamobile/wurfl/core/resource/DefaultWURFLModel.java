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

   public DefaultWURFLModel(WURFLResource var1, String... var2) {
      this(var1, new WURFLResources(), var2);
   }

   public DefaultWURFLModel(WURFLResource var1, WURFLResources var2, String... var3) {
      this.deviceIdToAncestorIdCache = CollectionFactory.createConcurrentHashMap();
      this.familyDeviceIds = new HashSet<>();
      this.log = LoggerFactory.getLogger(this.getClass());
      this.loadFromRootResource(var1, var2, var3);
   }

   private final synchronized void loadFromRootResource(WURFLResource var1, WURFLResources var2, String... var3) {
      Validate.notNull(var1, "The root resource must be not null.");
      if (var2 == null) {
         var2 = new WURFLResources();
      }

      this.deviceIdToAncestorIdCache.clear();
      this.genericDevice = null;
      this.capabilityCount = null;
      ModelDevicesSnapshot var5 = var1.getData(var3);
      this.version = var5.getSnapshotKey();
      this.smid = var5.getSmid();
      ModelDevices var6 = var5.copyDevices();
      ModelDevices var4 = new ModelDevices(var6);
      this.deviceIdsByInsertionOrder = var6.getDeviceIdsByInsertionOrder();
      ModelDevicesConsistencyVerifier.verifyModelDevices(var4);
      this.applyPatchesAndRebuild(var2, var4, var3);
      if (var2.size() == 0) {
         setAncestors(var4);
      }

   }

   private final synchronized void applyPatchesAndRebuild(WURFLResources var1, ModelDevices var2, String... var3) {
      int var4 = 0;
      int var5 = 0;

      for(int var6 = 0; var1 != null && var6 < var1.size(); ++var6) {
         ModelDevices var7;
         ModelDevicesSnapshot var8;
         ModelDevicesConsistencyVerifier.verifyNoRedefinedDevices(var7 = (var8 = var1.get(var6).getData(var3)).copyDevices(), var2);
         String var9 = new StringBuilder().append(StringUtils.defaultString(this.version)).append("; ").append(var8.getSnapshotKey()).toString();
         var2 = ModelDevicesPatchMerger.merge(var2, var7);
         this.deviceIdsByInsertionOrder = var2.getDeviceIdsByInsertionOrder();
         ModelDevicesConsistencyVerifier.verifyModelDevices(var2);
         this.version = var9;
      }

      ModelDevicesConsistencyVerifier.verifyModelDevices(var2);
      this.devicesById = CollectionFactory.createConcurrentHashMap();
      this.devicesById.putAll(var2.getDevicesById());
      setAncestors(var2);
      Iterator<ModelDevice> var10 = this.devicesById.values().iterator();

      while(var10.hasNext()) {
         ModelDevice var11;
         if ((var11 = var10.next()).isActualDeviceRoot()) {
            ++var5;
         } else {
            String var12;
            if ((var12 = var11.getFallBack()).equals("generic") || var12.equals("generic_mobile")) {
               this.familyDeviceIds.add(var11.getID());
            }
         }

         if (this.getDeviceAncestor(var11).getID().equals("generic")) {
            ++var4;
         }
      }

      this.genericDevice = this.devicesById.get("generic");
      if (this.log.isInfoEnabled()) {
         this.log.info("WURFLModel version: " + this.version + "; devices: " + this.devicesById.size() + " root devices: " + var5 + "; families: " + this.familyDeviceIds.size() + "; generic devices: " + var4);
      }

   }

   private static void setAncestors(ModelDevices var0) {
      if (var0 != null) {
         Iterator var1 = var0.getDevices().iterator();

         while(var1.hasNext()) {
            ModelDevice var2;
            if ((var2 = (ModelDevice)var1.next()).getFallBack() != null && var0.containsId(var2.getFallBack())) {
               var2.setAncestor(var0.getById(var2.getFallBack()));
            }
         }

      }
   }

   public String getVersion() {
      return this.version;
   }

   public ModelDevice getDeviceById(String var1) {
      Validate.notEmpty(var1, "The id must be not null");
      ModelDevice var2;
      if ((var2 = this.devicesById.get(var1)) == null) {
         throw new DeviceNotDefinedException(var1);
      } else {
         return var2;
      }
   }

   public Set getDevices(Set var1) {
      Validate.notNull(var1, "The devicesIds must be not null Set");
      Validate.noNullElements(var1, "The devicesIds must not containing null elements");
      for(Object id : var1) {
         Validate.isTrue(id instanceof String, "The devicesIds must containing right devicesById id");
      }
      HashSet<ModelDevice> var2 = new HashSet<>();
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         var2.add(this.getDeviceById((String)var3.next()));
      }

      return var2;
   }

   public Set getAllDevices() {
      TreeSet<ModelDevice> var1;
      (var1 = new TreeSet<>(ModelDeviceUserAgentComparator.INSTANCE)).addAll(this.devicesById.values());
      return var1;
   }

   public List getAllDevicesAsList() {
      ArrayList<ModelDevice> var1 = new ArrayList<>(this.deviceIdsByInsertionOrder.size());
      Iterator var2 = this.deviceIdsByInsertionOrder.iterator();

      while(var2.hasNext()) {
         var1.add(this.devicesById.get(var2.next()));
      }

      return var1;
   }

   public Set<String> getAllDevicesId() {
      HashSet<String> var1;
      (var1 = new HashSet<>()).addAll(this.devicesById.keySet());
      return var1;
   }

   public List<ModelDevice> getDeviceHierarchy(ModelDevice var1) {
      Validate.notNull(var1, "The device must be not null");

      LinkedList<ModelDevice> var2;
      for(var2 = new LinkedList<>(); !"generic".equals(var1.getID()); var1 = this.getDeviceFallback(var1)) {
         var2.addFirst(var1);
      }

      var2.addFirst(var1);
      return var2;
   }

   public ModelDevice getDeviceFallback(ModelDevice var1) {
      Validate.notNull(var1, "The device must be not null");

      try {
         ModelDevice var2 = this.getDeviceById(var1.getFallBack());
         return var2;
      } catch (DeviceNotDefinedException var3) {
         throw new DeviceNotInModelException(var1);
      }
   }

   public ModelDevice getDeviceAncestor(ModelDevice var1) {
      Validate.notNull(var1, "The device must be not null");
      String var2 = var1.getID();
      String var3;
      if ((var3 = this.deviceIdToAncestorIdCache.get(var2)) != null) {
         return this.getDeviceById(var3);
      } else {
         ModelDevice var4 = var1;
         ModelDevice var7 = this.getGenericDevice();
         List<ModelDevice> var5 = this.getDeviceHierarchy(var1);
         for(int var6 = var5.size() - 1; var6 >= 0 && !var4.isActualDeviceRoot() && !var7.equals(var4); --var6) {
            var4 = var5.get(var6);
         }

         if (!var4.isActualDeviceRoot() && !var7.equals(var4)) {
            throw new RuntimeException("Hierarchy is invalid");
         } else {
            String var8 = var4.getID();
            this.deviceIdToAncestorIdCache.put(var2, var8);
            return var4;
         }
      }
   }

   public boolean isDeviceDefined(String var1) {
      Validate.notEmpty(var1, "The deviceId must be not null");
      return this.devicesById.containsKey(var1);
   }

   public int size() {
      return this.devicesById.size();
   }

   public Set getAllGroups() {
      return this.getGenericDevice().getGroups();
   }

   public boolean isGroupDefined(String var1) {
      Validate.notEmpty(var1, "The groupId must be not null");
      return this.getGenericDevice().defineGroup(var1);
   }

   public String getGroupByCapability(String var1) {
      Validate.notEmpty(var1, "The capabilityName must be not null");
      ModelDevice var2;
      if (!(var2 = this.getGenericDevice()).defineCapability(var1)) {
         throw new CapabilityNotDefinedException(var1);
      } else {
         return var2.getGroupForCapability(var1);
      }
   }

   public void reload(WURFLResource var1, WURFLResources var2, String... var3) {
      this.log.info("about to reload the WURFL Model");
      this.loadFromRootResource(var1, var2, var3);
   }

   public void applyPatches(WURFLResources var1, String... var2) {
      this.applyPatchesAndRebuild(var1, new ModelDevices(this.devicesById), var2);
   }

   public Set getAllCapabilities() {
      ModelDevice var1 = this.getGenericDevice();
      return new HashSet<>(var1.getCapabilities().keySet());
   }

   public Integer getCapabilityCount() {
      if (this.capabilityCount == null || this.capabilityCount == 0) {
         this.capabilityCount = this.getAllCapabilities().size();
      }

      return this.capabilityCount;
   }

   public boolean isCapabilityDefined(String var1) {
      Validate.notEmpty(var1, "The capability must be not null");
      return this.getGenericDevice().defineCapability(var1);
   }

   public Set getCapabilitiesForGroup(String var1) {
      Validate.notEmpty(var1, "The groupId must be not null");
      ModelDevice var2;
      if (!(var2 = this.getGenericDevice()).defineGroup(var1)) {
         throw new GroupNotDefinedException(var1);
      } else {
         return var2.getCapabilitiesNamesForGroup(var1);
      }
   }

   public ModelDevice getDeviceWhereCapabilityIsDefined(ModelDevice var1, String var2) {
      Validate.notNull(var1, "The rootDevice must be not null");
      Validate.notEmpty(var2, "The name must be not null");
      List var5 = this.getDeviceHierarchy(var1);
      for(int var3 = var5.size() - 1; var3 >= 0; --var3) {
         ModelDevice var4 = (ModelDevice)var5.get(var3);
         if (var4.defineCapability(var2)) {
            return var4;
         }

         if ("generic_mobile".equals(var4.getID())) {
            throw new CapabilityNotDefinedException(var2);
         }
      }

      throw new RuntimeException(new OrphanHierarchyException(var5));
   }

   public Set getRootDevicesIds() {
      HashSet<String> var1 = new HashSet<>();
      Iterator<ModelDevice> var2 = this.devicesById.values().iterator();

      while(var2.hasNext()) {
         ModelDevice var3;
         if ((var3 = var2.next()).isActualDeviceRoot()) {
            var1.add(var3.getID());
         }
      }

      return var1;
   }

   private ModelDevice getGenericDevice() {
      if (this.genericDevice != null) {
         return this.genericDevice;
      } else {
         ModelDevice var1;
         if ((var1 = this.devicesById.get("generic")) == null && this.devicesById.size() > 0) {
            throw new RuntimeException(new GenericNotDefinedException());
         } else {
            this.genericDevice = var1;
            return var1;
         }
      }
   }

   public String toString() {
      ToStringBuilder var1;
      (var1 = new ToStringBuilder(this)).append(this.version);
      return var1.toString();
   }
}
