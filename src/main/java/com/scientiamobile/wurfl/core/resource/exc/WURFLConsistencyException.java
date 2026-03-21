package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;

public abstract class WURFLConsistencyException extends WURFLRuntimeException {
  private static final long serialVersionUID = 10L;
  
  public WURFLConsistencyException() {
    super("WURFL consistency exception");
  }
  
  public WURFLConsistencyException(Throwable paramThrowable) {
    super("WURFL consistency exception", paramThrowable);
  }
  
  public WURFLConsistencyException(String paramString) {
    super(paramString);
  }
  
  public WURFLConsistencyException(String paramString, Throwable paramThrowable) {
    super(paramString, paramThrowable);
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\exc\WURFLConsistencyException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */