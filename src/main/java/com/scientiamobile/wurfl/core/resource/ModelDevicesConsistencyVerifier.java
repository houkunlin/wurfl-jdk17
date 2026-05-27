package com.scientiamobile.wurfl.core.resource;

import com.scientiamobile.wurfl.core.resource.exc.*;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

final class ModelDevicesConsistencyVerifier {
    private ModelDevicesConsistencyVerifier() {
    }

    public static void verifyModelDevices(ModelDevices devices) {
        if (!devices.containsId("generic")) {
            throw new GenericNotDefinedException();
        }
        HashMap<String, ModelDevice> deviceByUserAgent = new HashMap<>();
        HashSet<String> visitedDeviceIds = new HashSet<>();

        for (ModelDevice device : devices) {
            assert device != null : "device is null";

            ModelDevice existing = deviceByUserAgent.put(device.getUserAgent(), device);
            if (existing != null) {
                throw new UserAgentNotUniqueException(device, device.getUserAgent(), existing);
            }
            verifyHierarchy(device, devices, visitedDeviceIds);
            visitedDeviceIds.add(device.getID());
            verifyGroups(device, devices);
            verifyCapabilities(device, devices);
        }
    }

    private static void verifyHierarchy(ModelDevice device, ModelDevices devices, Set<String> visited) {
        String currentDeviceId = device.getID();
        assert !StringUtils.isEmpty(currentDeviceId);

        ArrayList<ModelDevice> hierarchy = new ArrayList<>(10);
        hierarchy.add(devices.getById(currentDeviceId));

        while (!"generic".equals(currentDeviceId)) {
            currentDeviceId = devices.getById(currentDeviceId).getFallBack();
            assert !StringUtils.isEmpty(currentDeviceId);

            if (visited.contains(currentDeviceId)) {
                return;
            }
            if (!devices.containsId(currentDeviceId)) {
                throw new OrphanHierarchyException(hierarchy);
            }

            int circularIndex = hierarchy.indexOf(devices.getById(currentDeviceId));
            if (circularIndex != -1) {
                LinkedList<ModelDevice> circularHierarchy = new LinkedList<>(hierarchy.subList(circularIndex, hierarchy.size()));
                throw new CircularHierarchyException(circularHierarchy);
            }
            hierarchy.add(devices.getById(currentDeviceId));
        }
    }

    private static void verifyGroups(ModelDevice device, ModelDevices devices) {
        ModelDevice genericDevice = devices.getById("generic");
        Set<String> genericGroups = genericDevice.getGroups();
        for (String group : device.getGroups()) {
            if (!genericGroups.contains(group)) {
                throw new InexistentGroupException(device, group);
            }
        }
    }

    private static void verifyCapabilities(ModelDevice device, ModelDevices devices) {
        assert devices.containsId("generic") : "device do not containing generic";

        ModelDevice genericDevice = devices.getById("generic");
        Map<String, String> genericCapabilities = genericDevice.getCapabilities();
        for (String capabilityName : device.getCapabilities().keySet()) {
            if (!genericCapabilities.containsKey(capabilityName)) {
                throw new InexistentCapabilityException(device, capabilityName);
            }
            String deviceGroup = device.getGroupForCapability(capabilityName);
            String genericGroup = genericDevice.getGroupForCapability(capabilityName);
            if (!deviceGroup.equals(genericGroup)) {
                throw new BadCapabilityGroupException(device, capabilityName, deviceGroup, genericGroup);
            }
        }
    }

    public static void verifyNoRedefinedDevices(ModelDevices patchDevices, ModelDevices baseDevices) {
        for (ModelDevice patchDevice : patchDevices.getDevices()) {
            String patchUserAgent = patchDevice.getUserAgent();
            String patchDeviceId = patchDevice.getID();
            String patchFallBack = patchDevice.getFallBack();
            ModelDevice baseDevice = baseDevices.getById(patchDeviceId);
            if (baseDevice == null) {
                return;
            }

            if (StringUtils.isEmpty(patchFallBack)) {
                throw new RedefinedDeviceException("Patched device with id " + patchDeviceId + "does not provide a value for fall_back");
            }

            if (!patchUserAgent.equals(baseDevice.getUserAgent())) {
                throw new RedefinedDeviceException(patchDevice, baseDevice, "user agent");
            }

            if (!patchFallBack.equals(baseDevice.getFallBack())) {
                throw new RedefinedDeviceException(patchDevice, baseDevice, "fall_back");
            }
        }

    }
}
