package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import org.apache.commons.lang3.text.StrBuilder;

public class BadCapabilityGroupException extends CapabilityConsistencyException {
  private static final long serialVersionUID = 10L;
  
  private String a;
  
  private String b;
  
  public BadCapabilityGroupException(ModelDevice paramModelDevice, String paramString1, String paramString2, String paramString3) {
    super(paramModelDevice, paramString1, (new StrBuilder("Capability: ")).append(paramString1).append(" is defined in group: ").append(paramString2).append(" istead in group:").append(paramString3).append(" in Device: ").append(paramModelDevice.getID()).toString());
    this.b = paramString3;
    this.a = paramString2;
  }
  
  public String getRightGroup() {
    return this.b;
  }
  
  public String getBadGroup() {
    return this.a;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\exc\BadCapabilityGroupException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
