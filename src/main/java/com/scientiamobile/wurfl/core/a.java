package com.scientiamobile.wurfl.core;

import java.util.Map;

abstract class a {
  public abstract String a(String paramString);
  
  public final int b(String paramString) {
    String str = a(paramString);
    try {
      return Integer.parseInt(str);
    } catch (NumberFormatException numberFormatException) {
      throw new NumberFormatException("WURFL invalid capability value: " + paramString + " expected an integer, received: \"" + str + "\"");
    } 
  }
  
  public abstract Map a();
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\a.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */