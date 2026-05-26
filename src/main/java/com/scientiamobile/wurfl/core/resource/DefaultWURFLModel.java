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
import org.apache.commons.collections4.iterators.ReverseListIterator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultWURFLModel implements WURFLModel {
   private Map<String, ModelDevice> a;
   private List<String> b;
   private final Map<String, String> c;
   private final Set<String> d;
   private ModelDevice e;
   private String f;
   private String g;
   private Integer h;
   private final Logger i;

   public DefaultWURFLModel(WURFLResource var1, String... var2) {
      this(var1, new WURFLResources(), var2);
   }

   public DefaultWURFLModel(WURFLResource var1, WURFLResources var2, String... var3) {
      this.c = CollectionFactory.createConcurrentHashMap();
      this.d = new HashSet<>();
      this.i = LoggerFactory.getLogger(this.getClass());
      this.a(var1, var2, var3);
   }

   private final synchronized void a(WURFLResource var1, WURFLResources var2, String... var3) {
      Validate.notNull(var1, "The root resource must be not null.");
      if (var2 == null) {
         var2 = new WURFLResources();
      }

      this.c.clear();
      this.e = null;
      this.h = null;
      ModelDevicesSnapshot var5 = var1.getData(var3);
      this.f = var5.getSnapshotKey();
      this.g = var5.getSmid();
      ModelDevices var6 = var5.copyDevices();
      ModelDevices var4 = new ModelDevices(var6);
      this.b = var6.getDeviceIdsByInsertionOrder();
      ModelDevicesConsistencyVerifier.verifyModelDevices(var4);
      this.a(var2, var4, var3);
      if (var2.size() == 0) {
         a(var4);
      }

   }

   private final synchronized void a(WURFLResources var1, ModelDevices var2, String... var3) {
      int var4 = 0;
      int var5 = 0;

      for(int var6 = 0; var1 != null && var6 < var1.size(); ++var6) {
         ModelDevices var7;
         ModelDevicesSnapshot var8;
         ModelDevicesConsistencyVerifier.verifyNoRedefinedDevices(var7 = (var8 = var1.get(var6).getData(var3)).copyDevices(), var2);
         String var9 = new StringBuilder().append(StringUtils.defaultString(this.f)).append("; ").append(var8.getSnapshotKey()).toString();
         var2 = ModelDevicesPatchMerger.merge(var2, var7);
         this.b = var2.getDeviceIdsByInsertionOrder();
         ModelDevicesConsistencyVerifier.verifyModelDevices(var2);
         this.f = var9;
      }

      ModelDevicesConsistencyVerifier.verifyModelDevices(var2);
      this.a = CollectionFactory.createConcurrentHashMap();
      this.a.putAll(var2.getDevicesById());
      a(var2);
      Iterator<ModelDevice> var10 = this.a.values().iterator();

      while(var10.hasNext()) {
         ModelDevice var11;
         if ((var11 = var10.next()).isActualDeviceRoot()) {
            ++var5;
         } else {
            String var12;
            if ((var12 = var11.getFallBack()).equals("generic") || var12.equals("generic_mobile")) {
               this.d.add(var11.getID());
            }
         }

         if (this.getDeviceAncestor(var11).getID().equals("generic")) {
            ++var4;
         }
      }

      this.e = this.a.get("generic");
      if (this.i.isInfoEnabled()) {
         this.i.info("WURFLModel version: " + this.f + "; devices: " + this.a.size() + " root devices: " + var5 + "; families: " + this.d.size() + "; generic devices: " + var4);
      }

   }

   private static void a(ModelDevices var0) {
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
      return this.f;
   }

   public ModelDevice getDeviceById(String var1) {
      Validate.notEmpty(var1, "The id must be not null");
      ModelDevice var2;
      if ((var2 = this.a.get(var1)) == null) {
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
      (var1 = new TreeSet<>(ModelDeviceUserAgentComparator.INSTANCE)).addAll(this.a.values());
      return var1;
   }

   public List getAllDevicesAsList() {
      ArrayList<ModelDevice> var1 = new ArrayList<>(this.b.size());
      Iterator var2 = this.b.iterator();

      while(var2.hasNext()) {
         var1.add(this.a.get(var2.next()));
      }

      return var1;
   }

   public Set getAllDevicesId() {
      HashSet<String> var1;
      (var1 = new HashSet<>()).addAll(this.a.keySet());
      return var1;
   }

   public List getDeviceHierarchy(ModelDevice var1) {
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
      if ((var3 = (String)this.c.get(var2)) != null) {
         return this.getDeviceById(var3);
      } else {
         ModelDevice var4 = var1;
         ModelDevice var7 = this.a();
         List var5 = this.getDeviceHierarchy(var1);

         for(ReverseListIterator var6 = new ReverseListIterator(var5); var6.hasNext() && !var4.isActualDeviceRoot() && !var7.equals(var4); var4 = (ModelDevice)var6.next()) {
         }

         if (!var4.isActualDeviceRoot() && !var7.equals(var4)) {
            throw new RuntimeException("Hierarchy is invalid");
         } else {
            String var8 = var4.getID();
            this.c.put(var2, var8);
            return var4;
         }
      }
   }

   public boolean isDeviceDefined(String var1) {
      Validate.notEmpty(var1, "The deviceId must be not null");
      return this.a.containsKey(var1);
   }

   public int size() {
      return this.a.size();
   }

   public Set getAllGroups() {
      return this.a().getGroups();
   }

   public boolean isGroupDefined(String var1) {
      Validate.notEmpty(var1, "The groupId must be not null");
      return this.a().defineGroup(var1);
   }

   public String getGroupByCapability(String var1) {
      Validate.notEmpty(var1, "The capabilityName must be not null");
      ModelDevice var2;
      if (!(var2 = this.a()).defineCapability(var1)) {
         throw new CapabilityNotDefinedException(var1);
      } else {
         return var2.getGroupForCapability(var1);
      }
   }

   public void reload(WURFLResource var1, WURFLResources var2, String... var3) {
      this.i.info("about to reload the WURFL Model");
      this.a(var1, var2, var3);
   }

   public void applyPatches(WURFLResources var1, String... var2) {
      this.a(var1, new ModelDevices(this.a), var2);
   }

   public Set getAllCapabilities() {
      ModelDevice var1 = this.a();
      return new HashSet<>(var1.getCapabilities().keySet());
   }

   public Integer getCapabilityCount() {
      if (this.h == null || this.h == 0) {
         this.h = this.getAllCapabilities().size();
      }

      return this.h;
   }

   public boolean isCapabilityDefined(String var1) {
      Validate.notEmpty(var1, "The capability must be not null");
      return this.a().defineCapability(var1);
   }

   public Set getCapabilitiesForGroup(String var1) {
      Validate.notEmpty(var1, "The groupId must be not null");
      ModelDevice var2;
      if (!(var2 = this.a()).defineGroup(var1)) {
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
      Iterator<ModelDevice> var2 = this.a.values().iterator();

      while(var2.hasNext()) {
         ModelDevice var3;
         if ((var3 = var2.next()).isActualDeviceRoot()) {
            var1.add(var3.getID());
         }
      }

      return var1;
   }

   private ModelDevice a() {
      if (this.e != null) {
         return this.e;
      } else {
         ModelDevice var1;
         if ((var1 = this.a.get("generic")) == null && this.a.size() > 0) {
            throw new RuntimeException(new GenericNotDefinedException());
         } else {
            this.e = var1;
            return var1;
         }
      }
   }

   public String toString() {
      ToStringBuilder var1;
      (var1 = new ToStringBuilder(this)).append(this.f);
      return var1.toString();
   }
}
