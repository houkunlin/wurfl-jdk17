package com.scientiamobile.wurfl.core.resource;

import java.util.Map;

public final class ModelDevice$Builder {
  private ModelDevice a = new ModelDevice();
  
  public ModelDevice$Builder(String paramString1, String paramString2, String paramString3) {
    ModelDevice.a(this.a, paramString1);
    ModelDevice.b(this.a, paramString2);
    ModelDevice.c(this.a, paramString3);
  }
  
  public final ModelDevice$Builder setActualDeviceRoot(boolean paramBoolean) {
    ModelDevice.a(this.a, paramBoolean);
    return this;
  }
  
  public final ModelDevice$Builder setCapabilities(Map paramMap) {
    ModelDevice.a(this.a, paramMap);
    return this;
  }
  
  public final ModelDevice$Builder setCapabilitiesByGroup(Map paramMap) {
    ModelDevice.b(this.a, paramMap);
    return this;
  }
  
  public final ModelDevice$Builder setAncestor(ModelDevice paramModelDevice) {
    ModelDevice.a(this.a, paramModelDevice);
    return this;
  }
  
  public final ModelDevice build() {
    return this.a;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\ModelDevice$Builder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */