package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

public abstract class DeviceConsistencyException extends WURFLConsistencyException {
   private static final long serialVersionUID = 10L;
   private ModelDevice device;

   protected DeviceConsistencyException(ModelDevice device, String message) {
      super(message);
      this.device = device;
   }

   protected DeviceConsistencyException(ModelDevice device) {
      super("Device: " + device.getID() + " consistency error");
      this.device = device;
   }

   public ModelDevice getDevice() {
      return this.device;
   }
}
