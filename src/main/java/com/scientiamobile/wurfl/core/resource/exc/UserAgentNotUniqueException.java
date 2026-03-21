package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import org.apache.commons.lang3.text.StrBuilder;

public class UserAgentNotUniqueException extends UserAgentConsistencyException {
  private static final long serialVersionUID = 10L;
  
  private ModelDevice a;
  
  public UserAgentNotUniqueException(ModelDevice paramModelDevice1, String paramString, ModelDevice paramModelDevice2) {
    super(paramModelDevice1, paramString, (new StrBuilder("Device: ")).append(paramModelDevice1).append(" define duplicate user-agent: ").append(paramString).append(" defined by device: ").append(paramModelDevice2).toString());
    this.a = paramModelDevice2;
  }
  
  public ModelDevice getDefiningDevice() {
    return this.a;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\exc\UserAgentNotUniqueException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
