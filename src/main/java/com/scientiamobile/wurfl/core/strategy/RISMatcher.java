package com.scientiamobile.wurfl.core.strategy;

import java.util.Collection;
import java.util.ListIterator;
import org.slf4j.LoggerFactory;

public final class RISMatcher {
  public static final RISMatcher INSTANCE = new RISMatcher();
  
  private RISMatcher() {
    LoggerFactory.getLogger(RISMatcher.class);
  }
  
  public final String getName() {
    return "RIS";
  }
  
  public final String match(Collection<String> paramCollection, String paramString, int paramInt) {
    String str = null;
    int i = paramString.length();
    paramCollection = paramCollection;
    int j = -1;
    int k = -1;
    int m = 0;
    int n = paramCollection.size() - 1;
    while (m <= n && k < i) {
      int i1 = (m + n) / 2;
      String str1 = paramCollection.get(i1);
      int i3;
      if ((i3 = a(paramString, str1)) > k) {
        j = i1;
        k = i3;
      } 
      int i2;
      if ((i2 = str1.compareTo(paramString)) < 0) {
        m = i1 + 1;
        continue;
      } 
      if (i2 > 0)
        n = i1 - 1; 
    } 
    if (k >= paramInt) {
      int i1 = k;
      paramInt = j;
      Collection<String> collection = paramCollection;
      String str1 = paramString;
      i = i1;
      ListIterator<String> listIterator = collection.listIterator(paramInt);
      while (listIterator.hasPrevious() && i == i1) {
        String str2 = listIterator.previous();
        int i2;
        if ((i2 = a(str1, str2)) == i1)
          paramInt--; 
      } 
      str = collection.get(paramInt);
    } 
    return str;
  }
  
  private static int a(String paramString1, String paramString2) {
    int i = Math.min(paramString1.length(), paramString2.length());
    byte b;
    for (b = 0; b < i && paramString1.charAt(b) == paramString2.charAt(b); b++);
    return b;
  }
  
  public final String toString() {
    return getName();
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\strategy\RISMatcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */