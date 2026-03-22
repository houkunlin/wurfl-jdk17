package com.scientiamobile.wurfl.core.resource;

import java.util.List;
import java.util.Set;

public interface WURFLModel {
  String getVersion();

  ModelDevice getDeviceById(String paramString);

  boolean isDeviceDefined(String paramString);

  Set getAllDevices();

  List getAllDevicesAsList();

  Set getAllDevicesId();

  Set<ModelDevice> getDevices(Set<String> paramSet);

  List getDeviceHierarchy(ModelDevice paramModelDevice);

  ModelDevice getDeviceFallback(ModelDevice paramModelDevice);

  ModelDevice getDeviceAncestor(ModelDevice paramModelDevice);

  int size();

  Integer getCapabilityCount();

  void reload(WURFLResource paramWURFLResource, WURFLResources paramWURFLResources, String... paramVarArgs);

  void applyPatches(WURFLResources paramWURFLResources, String... paramVarArgs);

  Set getAllCapabilities();

  boolean isCapabilityDefined(String paramString);

  String getGroupByCapability(String paramString);

  ModelDevice getDeviceWhereCapabilityIsDefined(ModelDevice paramModelDevice, String paramString);

  Set getAllGroups();

  boolean isGroupDefined(String paramString);

  Set getCapabilitiesForGroup(String paramString);

  Set getRootDevicesIds();
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\WURFLModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
