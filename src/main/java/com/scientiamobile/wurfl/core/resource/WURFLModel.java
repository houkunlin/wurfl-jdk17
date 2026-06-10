package com.scientiamobile.wurfl.core.resource;

import java.util.List;
import java.util.Set;

/**
 * Implementation of WURFL Model.
 */

public interface WURFLModel {
    String getVersion();

    ModelDevice getDeviceById(String deviceId);

    boolean isDeviceDefined(String deviceId);

    Set<ModelDevice> getAllDevices();

    List<ModelDevice> getAllDevicesAsList();

    Set<String> getAllDevicesId();

    Set<ModelDevice> getDevices(Set<String> deviceIds);

    List<ModelDevice> getDeviceHierarchy(ModelDevice device);

    ModelDevice getDeviceFallback(ModelDevice device);

    ModelDevice getDeviceAncestor(ModelDevice device);

    int size();

    Integer getCapabilityCount();

    void reload(WURFLResource resource, WURFLResources resources, String... params);

    void applyPatches(WURFLResources resources, String... params);

    Set<String> getAllCapabilities();

    boolean isCapabilityDefined(String capabilityName);

    String getGroupByCapability(String capabilityName);

    ModelDevice getDeviceWhereCapabilityIsDefined(ModelDevice device, String capabilityName);

    Set<String> getAllGroups();

    boolean isGroupDefined(String groupId);

    Set<String> getCapabilitiesForGroup(String groupId);

    Set<String> getRootDevicesIds();
}
