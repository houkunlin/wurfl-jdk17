package com.scientiamobile.wurfl.core.resource;

import java.util.Map;

public final class ModelDeviceBuilder {
   private final ModelDevice device = new ModelDevice();

   public ModelDeviceBuilder(String var1, String var2, String var3) {
      this.device.setUserAgent(var1);
      this.device.setId(var2);
      this.device.setFallBack(var3);
   }

   public final ModelDeviceBuilder setActualDeviceRoot(boolean var1) {
      this.device.setActualDeviceRoot(var1);
      return this;
   }

   public final ModelDeviceBuilder setCapabilities(Map<String, String> var1) {
      this.device.setCapabilities(var1);
      return this;
   }

   public final ModelDeviceBuilder setCapabilitiesByGroup(Map<String, String> var1) {
      this.device.setGroupsByCapability(var1);
      return this;
   }

   public final ModelDeviceBuilder setAncestor(ModelDevice var1) {
      this.device.setAncestor(var1);
      return this;
   }

   public final ModelDevice build() {
      return this.device;
   }
}
