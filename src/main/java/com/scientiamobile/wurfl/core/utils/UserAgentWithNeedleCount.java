package com.scientiamobile.wurfl.core.utils;

public class UserAgentWithNeedleCount {
  private String a;
  
  private int b;
  
  private int c;
  
  private boolean d;
  
  UserAgentWithNeedleCount(String paramString, int paramInt1, int paramInt2, boolean paramBoolean) {
    this.a = paramString;
    this.b = paramInt1;
    this.c = paramInt2;
    this.d = paramBoolean;
  }
  
  public String getAsciiPrintableUserAgent() {
    return this.a;
  }
  
  public int getPlusCharCount() {
    return this.b;
  }
  
  public int getPercentageCharCount() {
    return this.c;
  }
  
  public boolean hasSpaceChars() {
    return this.d;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\cor\\utils\UserAgentWithNeedleCount.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */