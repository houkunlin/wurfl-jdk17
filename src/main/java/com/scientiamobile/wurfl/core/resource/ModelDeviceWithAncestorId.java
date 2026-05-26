package com.scientiamobile.wurfl.core.resource;

public class ModelDeviceWithAncestorId {
   private final ModelDevice modelDevice;
   private final String ancestorId;

   public ModelDeviceWithAncestorId(ModelDevice modelDevice, String ancestorId) {
      this.modelDevice = modelDevice;
      this.ancestorId = ancestorId;
   }

   public ModelDevice getModelDevice() {
      return this.modelDevice;
   }

   public String getAncestorId() {
      return this.ancestorId;
   }
}
