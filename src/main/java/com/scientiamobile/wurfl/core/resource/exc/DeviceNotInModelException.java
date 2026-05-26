package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
import com.scientiamobile.wurfl.core.resource.ModelDevice;

public class DeviceNotInModelException extends WURFLRuntimeException {
   private static final long serialVersionUID = 10L;
   private ModelDevice modelDevice;

   public DeviceNotInModelException(ModelDevice modelDevice) {
      super((new StringBuilder("Device: ")).append(modelDevice.getID()).append(" is not managed by model").toString());
      this.modelDevice = modelDevice;
   }

   public ModelDevice getModelDevice() {
      return this.modelDevice;
   }
}
