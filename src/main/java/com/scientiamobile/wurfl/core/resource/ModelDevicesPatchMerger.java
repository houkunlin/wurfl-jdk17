package com.scientiamobile.wurfl.core.resource;

import com.scientiamobile.wurfl.core.resource.exc.UserAgentOverrideException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class ModelDevicesPatchMerger {
   private static final Logger LOG = LoggerFactory.getLogger(ModelDevicesPatchMerger.class);
   private static boolean ASSERTIONS_DISABLED = !ModelDevicesPatchMerger.class.desiredAssertionStatus();

   private ModelDevicesPatchMerger() {
   }

   public static ModelDevices merge(ModelDevices baseDevices, ModelDevices patchDevices) {
      ModelDevices mergedDevices = new ModelDevices(baseDevices);

      for(Object patchDeviceObj : patchDevices) {
         ModelDevice patchDevice = (ModelDevice)patchDeviceObj;
         ModelDevice mergedDevice = patchDevice;
         if (baseDevices.containsId(patchDevice.getID())) {
            ModelDevice baseDevice = baseDevices.getById(patchDevice.getID());
            if (patchDevice != null && patchDevice.getUserAgent() != null) {
               HashMap<String, String> mergedCapabilities = new HashMap<>(baseDevice.getCapabilities());
               mergedCapabilities.putAll(patchDevice.getCapabilities());
               Map<String, String> baseGroupsByCapability = baseDevice.getGroupsByCapability();
               Map<String, String> patchGroupsByCapability = patchDevice.getGroupsByCapability();
               HashMap<String, String> mergedGroupsByCapability = new HashMap<>();
               mergedGroupsByCapability.putAll(baseGroupsByCapability);
               mergedGroupsByCapability.putAll(patchGroupsByCapability);
               if (!patchDevice.getUserAgent().equals(baseDevice.getUserAgent())) {
                  throw new UserAgentOverrideException(baseDevice, patchDevice.getUserAgent(), baseDevice.getUserAgent());
               }

               mergedDevice = (new ModelDeviceBuilder(baseDevice.getID(), baseDevice.getUserAgent(), patchDevice.getFallBack())).setActualDeviceRoot(patchDevice.isActualDeviceRoot()).setCapabilitiesByGroup(mergedGroupsByCapability).setCapabilities(mergedCapabilities).build();
            } else {
               LOG.error("invalid patching device, not patched");
               mergedDevice = baseDevice;
            }

            if (mergedDevice != null) {
               mergedDevices.remove(baseDevice);
            }
         }

         if (!ASSERTIONS_DISABLED && mergedDevice == null) {
            throw new AssertionError("patchedDevice is null");
         }

         mergedDevices.add(mergedDevice);
      }

      return mergedDevices;
   }
}
