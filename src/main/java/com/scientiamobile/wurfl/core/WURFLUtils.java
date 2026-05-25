package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.Validate;

public class WURFLUtils {
   private final WURFLModel a;
   private final DeviceProvider b;
   private final WURFLService c;

   WURFLUtils(WURFLModel var1, DeviceProvider var2, WURFLService var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   public String getVersion() {
      return this.a.getVersion();
   }

   public boolean isDeviceDefined(String var1) {
      Validate.notEmpty(var1, "deviceId must be not null");
      return this.a.isDeviceDefined(var1);
   }

   public ModelDevice getModelDeviceById(String var1) {
      Validate.notEmpty(var1, "The id must be not null Set");
      return this.a.getDeviceById(var1);
   }

   public Set getModelDevices(Set var1) {
      Validate.notNull(var1, "The ids must be not null Set");
      Validate.noNullElements(var1, "The ids must not containing null elements");
      Validate.allElementsOfType(var1, String.class, "The ids must containing right devices id");
      return this.a.getDevices(var1);
   }

   public Set getAllDevicesId() {
      return this.a.getAllDevicesId();
   }

   public Set getAllModelDevices() {
      return this.a.getAllDevices();
   }

   public List getModelDeviceHierarchy(ModelDevice var1) {
      Validate.notNull(var1, "The root ModelDevice must be not null");
      return this.a.getDeviceHierarchy(var1);
   }

   public ModelDevice getModelDeviceFallback(ModelDevice var1) {
      Validate.notNull(var1, "The target ModelDevice must be not null");
      return this.a.getDeviceFallback(var1);
   }

   public ModelDevice getModelDeviceAncestor(ModelDevice var1) {
      Validate.notNull(var1, "The root ModelDevice must be not null");
      return this.a.getDeviceAncestor(var1);
   }

   public boolean isCapabilityDefined(String var1) {
      Validate.notEmpty(var1, "The capabilityName must be not null");
      return this.a.isCapabilityDefined(var1);
   }

   public Set getAllCapabilities() {
      return this.a.getAllCapabilities();
   }

   public String getGroupByCapability(String var1) {
      Validate.notEmpty(var1, "The capabilityName must be not null");
      return this.a.getGroupByCapability(var1);
   }

   public ModelDevice getModelDeviceWhereCapabilityIsDefined(ModelDevice var1, String var2) {
      Validate.notNull(var1, "The rootDevice must be not null Set");
      Validate.notEmpty(var2, "The capabilityName must be not null");
      return this.a.getDeviceWhereCapabilityIsDefined(var1, var2);
   }

   public boolean isGroupDefined(String var1) {
      Validate.notEmpty(var1, "The groupName must be not null");
      return this.a.isGroupDefined(var1);
   }

   public Set getAllGroups() {
      return this.a.getAllGroups();
   }

   public Set getCapabilitiesForGroup(String var1) {
      Validate.notEmpty(var1, "The groupName must be not null");
      return this.a.getCapabilitiesForGroup(var1);
   }

   public InternalDevice getInternalDeviceById(String var1) {
      return this.b.getInternalDevice(var1);
   }

   public Device getDeviceById(String var1) {
      return this.c.getDeviceById(var1);
   }

   public Device getDeviceById(String var1, WURFLRequest var2) {
      return this.c.getDeviceById(var1, var2);
   }

   public Device getDeviceById(String var1, HttpServletRequest var2) {
      return this.c.getDeviceById(var1, var2);
   }

   public Set getAllDevices() {
      Set var1 = this.getAllDevicesId();
      HashSet var2 = new HashSet(var1.size());

      for(String var3 : var1) {
         Device var5 = this.getDeviceById(var3);
         var2.add(var5);
      }

      return var2;
   }
}
