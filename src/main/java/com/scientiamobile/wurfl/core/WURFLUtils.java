package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.Validate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WURFLUtils {
    private final WURFLModel wurflModel;
    private final DeviceProvider deviceProvider;
    private final WURFLService wurflService;

    WURFLUtils(WURFLModel wurflModel, DeviceProvider deviceProvider, WURFLService wurflService) {
        this.wurflModel = wurflModel;
        this.deviceProvider = deviceProvider;
        this.wurflService = wurflService;
    }

    public String getVersion() {
        return this.wurflModel.getVersion();
    }

    public boolean isDeviceDefined(String deviceId) {
        Validate.notEmpty(deviceId, "deviceId must be not null");
        return this.wurflModel.isDeviceDefined(deviceId);
    }

    public ModelDevice getModelDeviceById(String deviceId) {
        Validate.notEmpty(deviceId, "The id must be not null Set");
        return this.wurflModel.getDeviceById(deviceId);
    }

    public Set<ModelDevice> getModelDevices(Set<String> deviceIds) {
        Validate.notNull(deviceIds, "The ids must be not null Set");
        Validate.noNullElements(deviceIds, "The ids must not containing null elements");
        return this.wurflModel.getDevices(deviceIds);
    }

    public Set<String> getAllDevicesId() {
        return this.wurflModel.getAllDevicesId();
    }

    public Set<ModelDevice> getAllModelDevices() {
        return this.wurflModel.getAllDevices();
    }

    public List<ModelDevice> getModelDeviceHierarchy(ModelDevice rootDevice) {
        Validate.notNull(rootDevice, "The root ModelDevice must be not null");
        return this.wurflModel.getDeviceHierarchy(rootDevice);
    }

    public ModelDevice getModelDeviceFallback(ModelDevice targetDevice) {
        Validate.notNull(targetDevice, "The target ModelDevice must be not null");
        return this.wurflModel.getDeviceFallback(targetDevice);
    }

    public ModelDevice getModelDeviceAncestor(ModelDevice rootDevice) {
        Validate.notNull(rootDevice, "The root ModelDevice must be not null");
        return this.wurflModel.getDeviceAncestor(rootDevice);
    }

    public boolean isCapabilityDefined(String capabilityName) {
        Validate.notEmpty(capabilityName, "The capabilityName must be not null");
        return this.wurflModel.isCapabilityDefined(capabilityName);
    }

    public Set<String> getAllCapabilities() {
        return this.wurflModel.getAllCapabilities();
    }

    public String getGroupByCapability(String capabilityName) {
        Validate.notEmpty(capabilityName, "The capabilityName must be not null");
        return this.wurflModel.getGroupByCapability(capabilityName);
    }

    public ModelDevice getModelDeviceWhereCapabilityIsDefined(ModelDevice rootDevice, String capabilityName) {
        Validate.notNull(rootDevice, "The rootDevice must be not null Set");
        Validate.notEmpty(capabilityName, "The capabilityName must be not null");
        return this.wurflModel.getDeviceWhereCapabilityIsDefined(rootDevice, capabilityName);
    }

    public boolean isGroupDefined(String groupName) {
        Validate.notEmpty(groupName, "The groupName must be not null");
        return this.wurflModel.isGroupDefined(groupName);
    }

    public Set<String> getAllGroups() {
        return this.wurflModel.getAllGroups();
    }

    public Set<String> getCapabilitiesForGroup(String groupName) {
        Validate.notEmpty(groupName, "The groupName must be not null");
        return this.wurflModel.getCapabilitiesForGroup(groupName);
    }

    public InternalDevice getInternalDeviceById(String deviceId) {
        return this.deviceProvider.getInternalDevice(deviceId);
    }

    public Device getDeviceById(String deviceId) {
        return this.wurflService.getDeviceById(deviceId);
    }

    public Device getDeviceById(String deviceId, WURFLRequest request) {
        return this.wurflService.getDeviceById(deviceId, request);
    }

    public Device getDeviceById(String deviceId, HttpServletRequest request) {
        return this.wurflService.getDeviceById(deviceId, request);
    }

    public Set<Device> getAllDevices() {
        Set<String> allDeviceIds = this.getAllDevicesId();
        HashSet<Device> devices = new HashSet<>(allDeviceIds.size());

        for (String deviceId : allDeviceIds) {
            Device device = this.getDeviceById(deviceId);
            devices.add(device);
        }

        return devices;
    }
}
