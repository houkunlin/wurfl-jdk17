package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.resource.exc.DeviceNotInModelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides Device Capabilities functionality.
 */

class DeviceCapabilitiesProvider implements CapabilitiesProvider {
    private static final Logger log = LoggerFactory.getLogger(DeviceCapabilitiesProvider.class);
    private final WURFLModel wurflModel;
    private final ModelDevice modelDevice;

    public DeviceCapabilitiesProvider(ModelDevice modelDevice, WURFLModel wurflModel) {
        this.wurflModel = wurflModel;
        this.modelDevice = modelDevice;
    }

    @Override
/**
 * Returns the al lapabilities.
 */

    public Map<String, String> getAllCapabilities() {
        HashMap<String, String> capabilities = new HashMap<>(this.wurflModel.getAllCapabilities().size());

        try {
            for (ModelDevice deviceInHierarchy : this.wurflModel.getDeviceHierarchy(this.modelDevice)) {
                capabilities.putAll(deviceInHierarchy.getCapabilities());
            }
        } catch (DeviceNotInModelException e) {
            if (log.isErrorEnabled()) {
                log.error("Device: {} is not in model. Capabilities will not loaded.", this.modelDevice.getID());
            }
        }

        return capabilities;
    }

    @Override
/**
 * Returns the capability.
 */

    public String getCapability(Map<String, String> capabilities, String capabilityName) {
        Map<String, String> capabilitiesMap = capabilities;
        String capabilityValue = capabilitiesMap.get(capabilityName);
        if (capabilityValue != null) {
            return capabilityValue;
        } else {
            ModelDevice currentDevice;
            currentDevice = this.modelDevice;
            for (; currentDevice != null && !currentDevice.defineCapability(capabilityName); currentDevice = currentDevice.getAncestor()) {
            }

            String resolvedValue = currentDevice != null ? currentDevice.getCapability(capabilityName) : null;
            if (resolvedValue != null) {
                capabilitiesMap.put(capabilityName, resolvedValue);
            }

            return resolvedValue;
        }
    }
}
