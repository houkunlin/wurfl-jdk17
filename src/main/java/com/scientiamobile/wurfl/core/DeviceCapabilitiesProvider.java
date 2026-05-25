package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.resource.exc.DeviceNotInModelException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.text.StrBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DeviceCapabilitiesProvider implements CapabilitiesProvider {
   private WURFLModel wurflModel;
   private ModelDevice modelDevice;
   private final Logger log = LoggerFactory.getLogger(DeviceCapabilitiesProvider.class);

   public DeviceCapabilitiesProvider(ModelDevice modelDevice, WURFLModel wurflModel) {
      this.wurflModel = wurflModel;
      this.modelDevice = modelDevice;
   }

   public final Map getAllCapabilities() {
      HashMap capabilities = new HashMap(this.wurflModel.getAllCapabilities().size());

      try {
         for(ModelDevice deviceInHierarchy : this.wurflModel.getDeviceHierarchy(this.modelDevice)) {
            capabilities.putAll(deviceInHierarchy.getCapabilities());
         }
      } catch (DeviceNotInModelException e) {
         if (this.log.isErrorEnabled()) {
            StrBuilder sb;
            (sb = new StrBuilder()).append("Device: ").append(this.modelDevice.getID()).append(" is not in model. ").append("Capabilities will not loaded.");
            this.log.error(sb.toString());
         }
      }

      return capabilities;
   }

   public final String getCapability(Map capabilities, String capabilityName) {
      String capabilityValue;
      if ((capabilityValue = (String)capabilities.get(capabilityName)) != null) {
         return capabilityValue;
      } else {
         ModelDevice currentDevice;
         for(currentDevice = this.modelDevice; currentDevice != null && !currentDevice.defineCapability(capabilityName); currentDevice = currentDevice.getAncestor()) {
         }

         String resolvedValue = currentDevice != null ? currentDevice.getCapability(capabilityName) : null;
         if (resolvedValue != null) {
            capabilities.put(capabilityName, resolvedValue);
         }

         return resolvedValue;
      }
   }
}

