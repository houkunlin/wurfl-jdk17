package com.scientiamobile.wurfl.core.resource;

import com.scientiamobile.wurfl.core.resource.exc.UserAgentOverrideException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

final class ModelDevicesPatchMerger {
    private static final Logger LOG = LoggerFactory.getLogger(ModelDevicesPatchMerger.class);

    private ModelDevicesPatchMerger() {
    }

    public static ModelDevices merge(ModelDevices baseDevices, ModelDevices patchDevices) {
        ModelDevices mergedDevices = new ModelDevices(baseDevices);
        for (ModelDevice patchDevice : patchDevices) {
            if (baseDevices.containsId(patchDevice.getID())) {
                ModelDevice mergedDevice = mergePatchDevice(baseDevices.getById(patchDevice.getID()), patchDevice);
                mergedDevices.remove(baseDevices.getById(patchDevice.getID()));
                mergedDevices.add(mergedDevice);
            } else {
                mergedDevices.add(patchDevice);
            }
        }
        return mergedDevices;
    }

    private static ModelDevice mergePatchDevice(ModelDevice baseDevice, ModelDevice patchDevice) {
        if (patchDevice.getUserAgent() == null) {
            LOG.error("invalid patching device, not patched");
            return baseDevice;
        }
        if (!patchDevice.getUserAgent().equals(baseDevice.getUserAgent())) {
            throw new UserAgentOverrideException(baseDevice, patchDevice.getUserAgent(), baseDevice.getUserAgent());
        }
        HashMap<String, String> mergedCapabilities = new HashMap<>(baseDevice.getCapabilities());
        mergedCapabilities.putAll(patchDevice.getCapabilities());
        HashMap<String, String> mergedGroupsByCapability = new HashMap<>(baseDevice.getGroupsByCapability());
        mergedGroupsByCapability.putAll(patchDevice.getGroupsByCapability());
        return new ModelDeviceBuilder(baseDevice.getID(), baseDevice.getUserAgent(), patchDevice.getFallBack())
                .setActualDeviceRoot(patchDevice.isActualDeviceRoot())
                .setCapabilitiesByGroup(mergedGroupsByCapability)
                .setCapabilities(mergedCapabilities)
                .build();
    }
}
