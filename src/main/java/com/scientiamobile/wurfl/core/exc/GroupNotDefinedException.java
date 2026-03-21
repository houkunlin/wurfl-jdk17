package com.scientiamobile.wurfl.core.exc;

import org.apache.commons.lang3.text.StrBuilder;

public class GroupNotDefinedException extends WURFLRuntimeException {
  private static final long serialVersionUID = 1L;
  
  private String a;
  
  public GroupNotDefinedException(String paramString1, String paramString2) {
    super(paramString2);
    this.a = paramString1;
  }
  
  public GroupNotDefinedException(String paramString) {
    this(paramString, (new StrBuilder("Group: ")).append(paramString).append(" is not defined in WURFL").toString());
  }
  
  public String getGroupId() {
    return this.a;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\exc\GroupNotDefinedException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
