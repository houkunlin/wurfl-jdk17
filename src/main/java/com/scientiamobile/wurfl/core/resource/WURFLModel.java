package com.scientiamobile.wurfl.core.resource;

import java.util.List;
import java.util.Set;

public interface WURFLModel {
   String getVersion();

   ModelDevice getDeviceById(String var1);

   boolean isDeviceDefined(String var1);

   Set getAllDevices();

   List getAllDevicesAsList();

   Set getAllDevicesId();

   Set getDevices(Set var1);

   List getDeviceHierarchy(ModelDevice var1);

   ModelDevice getDeviceFallback(ModelDevice var1);

   ModelDevice getDeviceAncestor(ModelDevice var1);

   int size();

   Integer getCapabilityCount();

   void reload(WURFLResource var1, WURFLResources var2, String... var3);

   void applyPatches(WURFLResources var1, String... var2);

   Set getAllCapabilities();

   boolean isCapabilityDefined(String var1);

   String getGroupByCapability(String var1);

   ModelDevice getDeviceWhereCapabilityIsDefined(ModelDevice var1, String var2);

   Set getAllGroups();

   boolean isGroupDefined(String var1);

   Set getCapabilitiesForGroup(String var1);

   Set getRootDevicesIds();
}
