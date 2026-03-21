package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import org.apache.commons.lang3.text.StrBuilder;

public abstract class UserAgentConsistencyException extends DeviceConsistencyException {
  private static final long serialVersionUID = 10L;
  
  private String a;
  
  public UserAgentConsistencyException(ModelDevice paramModelDevice, String paramString1, String paramString2) {
    super(paramModelDevice, paramString2);
    this.a = paramString1;
  }
  
  public UserAgentConsistencyException(ModelDevice paramModelDevice, String paramString) {
    super(paramModelDevice, (new StrBuilder("Device: ")).append(paramModelDevice.getID()).append(" user-agent: ").append(paramString).append(" consistency exception").toString());
    this.a = paramString;
  }
  
  public String getUserAgent() {
    return this.a;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\exc\UserAgentConsistencyException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
