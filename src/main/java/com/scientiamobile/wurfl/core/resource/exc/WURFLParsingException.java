package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;

public class WURFLParsingException extends WURFLRuntimeException {
  private static final long serialVersionUID = 10L;
  
  public WURFLParsingException() {}
  
  public WURFLParsingException(String paramString) {
    super(paramString);
  }
  
  public WURFLParsingException(Throwable paramThrowable) {
    super(paramThrowable);
  }
  
  public WURFLParsingException(String paramString, Throwable paramThrowable) {
    super(paramString, paramThrowable);
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\exc\WURFLParsingException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */