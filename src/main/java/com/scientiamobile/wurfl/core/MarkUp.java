package com.scientiamobile.wurfl.core;

public final class MarkUp {
  public static final MarkUp WML = new MarkUp("WML", -1);
  
  public static final MarkUp CHTML = new MarkUp("CHTML", 0);
  
  public static final MarkUp XHTML_SIMPLE = new MarkUp("XHTML_SIMPLE", 1);
  
  public static final MarkUp XHTML_ADVANCED = new MarkUp("XHTML_ADVANCED", 2);
  
  private int a;
  
  private String b;
  
  public final String toString() {
    return this.b;
  }
  
  public final int toValue() {
    return this.a;
  }
  
  private MarkUp(String paramString, int paramInt) {
    this.b = paramString;
    this.a = paramInt;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\MarkUp.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */