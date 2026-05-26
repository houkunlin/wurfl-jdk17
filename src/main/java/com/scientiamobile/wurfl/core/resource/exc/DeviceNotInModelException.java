package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
import com.scientiamobile.wurfl.core.resource.ModelDevice;

public class DeviceNotInModelException extends WURFLRuntimeException {
   private static final long serialVersionUID = 10L;
   private ModelDevice a;

   public DeviceNotInModelException(ModelDevice var1) {
      super((new StringBuilder("Device: ")).append(var1.getID()).append(" is not managed by model").toString());
      this.a = var1;
   }

   public ModelDevice getModelDevice() {
      return this.a;
   }
}
