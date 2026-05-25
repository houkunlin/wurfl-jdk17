package com.scientiamobile.wurfl.core.resource;

import java.util.Map;

public final class ModelDevice$Builder {
   private ModelDevice a = new ModelDevice();

   public ModelDevice$Builder(String var1, String var2, String var3) {
      ModelDevice.a(this.a, var1);
      ModelDevice.b(this.a, var2);
      ModelDevice.c(this.a, var3);
   }

   public final ModelDevice$Builder setActualDeviceRoot(boolean var1) {
      ModelDevice.a(this.a, var1);
      return this;
   }

   public final ModelDevice$Builder setCapabilities(Map var1) {
      ModelDevice.a(this.a, var1);
      return this;
   }

   public final ModelDevice$Builder setCapabilitiesByGroup(Map var1) {
      ModelDevice.b(this.a, var1);
      return this;
   }

   public final ModelDevice$Builder setAncestor(ModelDevice var1) {
      ModelDevice.a(this.a, var1);
      return this;
   }

   public final ModelDevice build() {
      return this.a;
   }
}
