package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import org.apache.commons.lang.text.StrBuilder;

public abstract class GroupConsistencyException extends DeviceConsistencyException {
  private static final long serialVersionUID = 10L;
  
  private String a;
  
  public GroupConsistencyException(ModelDevice paramModelDevice, String paramString) {
    super(paramModelDevice, (new StrBuilder("Group: ")).append(paramString).append(" in device: ").append(paramModelDevice.getID()).append(" consistency exception").toString());
    this.a = paramString;
  }
  
  public GroupConsistencyException(ModelDevice paramModelDevice, String paramString1, String paramString2) {
    super(paramModelDevice, paramString2);
    this.a = paramString1;
  }
  
  public String getGroup() {
    return this.a;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\exc\GroupConsistencyException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */