package com.scientiamobile.wurfl.core.strategy;

import java.util.Collection;
import java.util.Iterator;
import org.slf4j.LoggerFactory;

public final class LDMatcher {
  public static final LDMatcher INSTANCE = new LDMatcher();
  
  private LDMatcher() {
    LoggerFactory.getLogger(LDMatcher.class);
  }
  
  public final String getName() {
    return "LD";
  }
  
  public final String match(Collection paramCollection, String paramString, int paramInt) {
    return match(paramCollection, paramString, paramInt, 0);
  }
  
  public final String match(Collection paramCollection, String paramString, int paramInt1, int paramInt2) {
    String str = null;
    int i = paramInt1 + 1;
    int j = paramString.length();
    Iterator<String> iterator = paramCollection.iterator();
    int k = paramString.length();
    while (iterator.hasNext() && j > 0) {
      String str1;
      if (Math.abs((str1 = iterator.next()).length() - paramString.length()) <= paramInt1 && ((j = getLevenshteinDistance(str1, paramString, str1.length(), k, paramInt1, paramInt2)) < i || j == 0)) {
        i = j;
        str = str1;
      } 
    } 
    return str;
  }
  
  public static int getLevenshteinDistance(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    while (true) {
      if (paramString1 == null || paramString2 == null)
        throw new IllegalArgumentException("Strings must not be null"); 
      if (paramInt3 == 0)
        return paramString1.equals(paramString2) ? 0 : Integer.MAX_VALUE; 
      if (paramInt1 > paramInt2) {
        paramInt2 = paramInt1;
        paramInt1 = paramInt2;
        paramString2 = paramString1;
        paramString1 = paramString2;
        continue;
      } 
      if (paramInt2 < paramInt4)
        return Integer.MAX_VALUE; 
      if (paramInt1 == 0)
        return paramInt2; 
      int[] arrayOfInt2 = new int[256];
      int i;
      for (i = paramInt4; i < paramInt2; i++)
        arrayOfInt2[(char)(paramString2.charAt(i) & 0xFF)] = arrayOfInt2[(char)(paramString2.charAt(i) & 0xFF)] + 1; 
      for (i = paramInt4; i < paramInt1; i++)
        arrayOfInt2[(char)(paramString1.charAt(i) & 0xFF)] = arrayOfInt2[(char)(paramString1.charAt(i) & 0xFF)] - 1; 
      i = 0;
      paramInt3 <<= 1;
      char c;
      for (c = ' '; c < 'z'; c = (char)(c + 1)) {
        if ((i += Math.abs(arrayOfInt2[c])) > paramInt3)
          return Integer.MAX_VALUE; 
      } 
      paramString1 = paramString1.substring(paramInt4);
      paramInt1 -= paramInt4;
      paramString2 = paramString2.substring(paramInt4);
      paramInt2 -= paramInt4;
      int[] arrayOfInt3 = new int[paramInt1 + 1];
      int[] arrayOfInt1 = new int[paramInt1 + 1];
      for (paramInt4 = 0; paramInt4 <= paramInt1; paramInt4++)
        arrayOfInt3[paramInt4] = paramInt4; 
      for (byte b = 1; b <= paramInt2; b++) {
        i = paramString2.charAt(b - 1);
        arrayOfInt1[0] = b;
        for (paramInt4 = 1; paramInt4 <= paramInt1; paramInt4++) {
          byte b1 = (paramString1.charAt(paramInt4 - 1) == i) ? 0 : 1;
          arrayOfInt1[paramInt4] = Math.min(Math.min(arrayOfInt1[paramInt4 - 1] + 1, arrayOfInt3[paramInt4] + 1), arrayOfInt3[paramInt4 - 1] + b1);
        } 
        int[] arrayOfInt = arrayOfInt3;
        arrayOfInt3 = arrayOfInt1;
        arrayOfInt1 = arrayOfInt;
      } 
      return arrayOfInt3[paramInt1];
    } 
  }
  
  public final String toString() {
    return getName();
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\strategy\LDMatcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */