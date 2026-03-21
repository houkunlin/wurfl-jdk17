package com.scientiamobile.wurfl.core.exc;

import org.apache.commons.lang.text.StrBuilder;

public class CapabilityNotDefinedException extends WURFLRuntimeException {
  private static final long serialVersionUID = 1L;
  
  private String a;
  
  public CapabilityNotDefinedException(String paramString) {
    this(paramString, (new StrBuilder("Capability: ")).append(paramString).append(" is not defined in WURFL").toString());
  }
  
  public CapabilityNotDefinedException(String paramString1, String paramString2) {
    super((new StrBuilder("Capability: ")).append(paramString1).append(" - ").append(paramString2).toString());
    this.a = paramString1;
  }
  
  public String getCapabilityName() {
    return this.a;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\exc\CapabilityNotDefinedException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */