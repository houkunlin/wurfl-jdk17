package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class l implements Serializable {
  private static final long serialVersionUID = 4934582187956400034L;
  
  private String a = null;
  
  private String b = null;
  
  private String[] c;
  
  public final String a() {
    return this.a;
  }
  
  public final void a(String paramString) {
    this.a = paramString;
  }
  
  public final String b() {
    return this.b;
  }
  
  public final void b(String paramString) {
    this.b = paramString;
  }
  
  public final boolean a(String paramString1, Pattern paramPattern, String paramString2, String paramString3) {
    if (a(paramPattern, paramString1) != null) {
      this.a = paramString2.trim();
      if (paramString3 != null)
        this.b = paramString3.trim(); 
      return true;
    } 
    this.c = null;
    return false;
  }
  
  public final boolean a(String paramString, Pattern paramPattern, int paramInt) {
    Matcher matcher;
    if ((matcher = a(paramPattern, paramString)) != null) {
      this.a = (matcher.group(paramInt) == null) ? "" : matcher.group(paramInt).trim();
      return true;
    } 
    this.c = null;
    return false;
  }
  
  public final boolean a(String paramString1, Pattern paramPattern, String paramString2, int paramInt) {
    Matcher matcher;
    if ((matcher = a(paramPattern, paramString1)) != null) {
      if (paramString2 != null)
        this.a = paramString2.trim(); 
      String str = matcher.group(paramInt);
      this.b = (str == null) ? "" : str.trim();
      return true;
    } 
    this.c = null;
    return false;
  }
  
  public final boolean b(String paramString, Pattern paramPattern, int paramInt) {
    Matcher matcher;
    if ((matcher = a(paramPattern, paramString)) != null) {
      this.a = (matcher.group(1) == null) ? "" : matcher.group(1).trim();
      this.b = (matcher.group(paramInt) == null) ? "" : matcher.group(paramInt).trim();
      return true;
    } 
    this.c = null;
    return false;
  }
  
  public final boolean a(String paramString1, String paramString2, String paramString3) {
    if (StringMatchUtils.indexOf(paramString1, paramString2) >= 0) {
      this.a = paramString3.trim();
      return true;
    } 
    return false;
  }
  
  private Matcher a(Pattern paramPattern, String paramString) {
    Matcher matcher;
    if ((matcher = paramPattern.matcher(paramString)).find()) {
      this.c = new String[matcher.groupCount() + 1];
      for (byte b = 0; b < this.c.length; b++)
        this.c[b] = matcher.group(b); 
      return matcher;
    } 
    this.c = null;
    return null;
  }
  
  public final String a(int paramInt) {
    return (this.c == null) ? null : this.c[paramInt];
  }
  
  public final String toString() {
    return "[name: " + this.a + " - version: " + this.b + "]";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\l.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */