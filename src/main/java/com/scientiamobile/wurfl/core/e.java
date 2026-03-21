package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import java.util.Set;
import org.apache.commons.lang.Validate;

class e implements CapabilitiesHolderFactory {
  private WURFLModel a;
  
  public e(WURFLModel paramWURFLModel) {
    if (!b && paramWURFLModel == null)
      throw new AssertionError(); 
    this.a = paramWURFLModel;
  }
  
  public a create(ModelDevice paramModelDevice) {
    Validate.notNull(paramModelDevice, "modelDevice is null");
    return new d(new f(paramModelDevice, this.a), this.a.getCapabilityCount().intValue());
  }
  
  public Set getModelCapabilities() {
    return this.a.getAllCapabilities();
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\e.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */