package com.scientiamobile.wurfl.core.resource;

public class ModelDeviceWithAncestorId {
  private final ModelDevice a;
  
  private final String b;
  
  public ModelDeviceWithAncestorId(ModelDevice paramModelDevice, String paramString) {
    this.a = paramModelDevice;
    this.b = paramString;
  }
  
  public ModelDevice getModelDevice() {
    return this.a;
  }
  
  public String getAncestorId() {
    return this.b;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\ModelDeviceWithAncestorId.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */