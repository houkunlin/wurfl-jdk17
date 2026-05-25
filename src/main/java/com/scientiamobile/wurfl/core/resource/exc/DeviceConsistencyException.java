package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import org.apache.commons.lang3.text.StrBuilder;

public abstract class DeviceConsistencyException extends WURFLConsistencyException {
   private static final long serialVersionUID = 10L;
   private ModelDevice a;

   public DeviceConsistencyException(ModelDevice var1, String var2) {
      super(var2);
      this.a = var1;
   }

   public DeviceConsistencyException(ModelDevice var1) {
      super((new StrBuilder("Device: ")).append(var1.getID()).append(" consistency error").toString());
      this.a = var1;
   }

   public ModelDevice getDevice() {
      return this.a;
   }
}
