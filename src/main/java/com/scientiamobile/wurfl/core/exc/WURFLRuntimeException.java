package com.scientiamobile.wurfl.core.exc;

public class WURFLRuntimeException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  
  public WURFLRuntimeException() {
    super("WURFL unexpected exception");
  }
  
  public WURFLRuntimeException(String paramString) {
    super(paramString);
  }
  
  public WURFLRuntimeException(String paramString, Throwable paramThrowable) {
    super(paramString, paramThrowable);
  }
  
  public WURFLRuntimeException(Throwable paramThrowable) {
    super("WURFL unexpected exception", paramThrowable);
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\exc\WURFLRuntimeException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */