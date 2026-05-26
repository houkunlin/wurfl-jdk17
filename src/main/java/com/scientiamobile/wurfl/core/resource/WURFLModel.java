package com.scientiamobile.wurfl.core.resource;

import java.util.List;
import java.util.Set;

public interface WURFLModel {
   String getVersion();

   ModelDevice getDeviceById(String deviceId);

   boolean isDeviceDefined(String deviceId);

   Set getAllDevices();

   List getAllDevicesAsList();

   Set getAllDevicesId();

   Set getDevices(Set deviceIds);

   List getDeviceHierarchy(ModelDevice device);

   ModelDevice getDeviceFallback(ModelDevice device);

   ModelDevice getDeviceAncestor(ModelDevice device);

   int size();

   Integer getCapabilityCount();

   void reload(WURFLResource resource, WURFLResources resources, String... params);

   void applyPatches(WURFLResources resources, String... params);

   Set getAllCapabilities();

   boolean isCapabilityDefined(String capabilityName);

   String getGroupByCapability(String capabilityName);

   ModelDevice getDeviceWhereCapabilityIsDefined(ModelDevice device, String capabilityName);

   Set getAllGroups();

   boolean isGroupDefined(String groupId);

   Set getCapabilitiesForGroup(String groupId);

   Set getRootDevicesIds();
}
