package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

public abstract class DeviceConsistencyException extends WURFLConsistencyException {
   private static final long serialVersionUID = 10L;
   private ModelDevice device;

   public DeviceConsistencyException(ModelDevice device, String message) {
      super(message);
      this.device = device;
   }

   public DeviceConsistencyException(ModelDevice device) {
      super((new StringBuilder("Device: ")).append(device.getID()).append(" consistency error").toString());
      this.device = device;
   }

   public ModelDevice getDevice() {
      return this.device;
   }
}
