package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import java.util.Set;
import org.apache.commons.lang.Validate;

class DefaultCapabilitiesHolderFactory implements CapabilitiesHolderFactory {
   private WURFLModel wurflModel;
   private static boolean assertionsDisabled = !DefaultCapabilitiesHolderFactory.class.desiredAssertionStatus();

   public DefaultCapabilitiesHolderFactory(WURFLModel wurflModel) {
      if (!assertionsDisabled && wurflModel == null) {
         throw new AssertionError();
      } else {
         this.wurflModel = wurflModel;
      }
   }

   public CapabilitiesHolder create(ModelDevice modelDevice) {
      Validate.notNull(modelDevice, "modelDevice is null");
      return new CachingCapabilitiesHolder(new DeviceCapabilitiesProvider(modelDevice, this.wurflModel), this.wurflModel.getCapabilityCount());
   }

   public Set getModelCapabilities() {
      return this.wurflModel.getAllCapabilities();
   }
}

