package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.matchers.MatchType;

public class DeviceInfo {
  private final String a;
  
  private final MatchType b;
  
  private final String c;
  
  private final String d;
  
  public DeviceInfo(String paramString1, MatchType paramMatchType, String paramString2, String paramString3, String paramString4, String paramString5) {
    this.a = paramString1;
    this.b = paramMatchType;
    this.c = paramString2;
    this.d = paramString3;
  }
  
  public String getId() {
    return this.a;
  }
  
  public MatchType getMatchType() {
    return this.b;
  }
  
  final String a() {
    return this.c;
  }
  
  final String b() {
    return this.d;
  }
  
  public String toString() {
    StringBuilder stringBuilder;
    (stringBuilder = new StringBuilder()).append("{id='").append(this.a).append('\'');
    stringBuilder.append(", match=").append(this.b);
    stringBuilder.append(", matcher=").append(this.c);
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\DeviceInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */