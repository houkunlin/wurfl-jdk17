package com.scientiamobile.wurfl.core.web.introspector;

final class b implements Comparable {
  private String a;
  
  private String b;
  
  private String c;
  
  private String d;
  
  private b(String paramString1, String paramString2, String paramString3, String paramString4) {
    this.a = paramString1;
    this.b = paramString2;
    this.c = paramString3;
    this.d = paramString4;
  }
  
  public final String toString() {
    String str;
    int i;
    if ((i = (str = this.a).indexOf("Matcher")) > 0)
      str = str.substring(0, i); 
    return str + "\t" + this.b + "\t" + this.c + "\t" + this.d;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\web\introspector\b.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */