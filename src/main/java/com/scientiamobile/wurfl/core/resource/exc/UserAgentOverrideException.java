package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import org.apache.commons.lang3.text.StrBuilder;

public class UserAgentOverrideException extends UserAgentConsistencyException {
  private static final long serialVersionUID = 10L;
  
  private String a;
  
  public UserAgentOverrideException(ModelDevice paramModelDevice, String paramString1, String paramString2) {
    super(paramModelDevice, paramString1, (new StrBuilder("Device: ")).append(paramModelDevice).append(" override defined user-agent: ").append(paramString2).append(" with overriding user-agent:").append(paramString1).toString());
    this.a = paramString2;
  }
  
  public String getExistUserAgent() {
    return this.a;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\exc\UserAgentOverrideException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
