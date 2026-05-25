package com.scientiamobile.wurfl.core.resource;

public class ModelDeviceWithAncestorId {
   private final ModelDevice a;
   private final String b;

   public ModelDeviceWithAncestorId(ModelDevice var1, String var2) {
      this.a = var1;
      this.b = var2;
   }

   public ModelDevice getModelDevice() {
      return this.a;
   }

   public String getAncestorId() {
      return this.b;
   }
}
