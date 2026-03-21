package com.scientiamobile.wurfl.core.exc;

public abstract class WURFLException extends Exception {
  private static final long serialVersionUID = 1L;
  
  public WURFLException() {
    super("Generic Exception in WURFL.");
  }
  
  public WURFLException(String paramString) {
    super(paramString);
  }
  
  public WURFLException(Throwable paramThrowable) {
    super(paramThrowable);
  }
  
  public WURFLException(String paramString, Throwable paramThrowable) {
    super(paramString, paramThrowable);
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\exc\WURFLException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */