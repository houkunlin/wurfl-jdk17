package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import java.util.Set;

public interface CapabilitiesHolderFactory {
  a create(ModelDevice paramModelDevice);
  
  Set getModelCapabilities();
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\CapabilitiesHolderFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */