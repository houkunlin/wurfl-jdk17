package com.scientiamobile.wurfl.core.resource.exc;

import org.apache.commons.lang3.text.StrBuilder;

public class GenericNotDefinedException extends WURFLConsistencyException {
  private static final long serialVersionUID = 10L;
  
  public GenericNotDefinedException() {
    super((new StrBuilder("Device: ")).append("generic").append(" is not defined").toString());
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\exc\GenericNotDefinedException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
