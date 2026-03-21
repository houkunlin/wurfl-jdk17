package com.scientiamobile.wurfl.core.exc;

import org.apache.commons.lang.text.StrBuilder;

public class DeviceNotDefinedException extends WURFLRuntimeException {
  private static final long serialVersionUID = 1L;
  
  private String a;
  
  public DeviceNotDefinedException(String paramString1, String paramString2) {
    super(paramString2);
    this.a = paramString1;
  }
  
  public DeviceNotDefinedException(String paramString) {
    this(paramString, (new StrBuilder("Device: ")).append(paramString).append(" is not defined in WURFL").toString());
  }
  
  public String getDeviceId() {
    return this.a;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\exc\DeviceNotDefinedException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */