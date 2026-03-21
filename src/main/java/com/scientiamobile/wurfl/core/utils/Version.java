package com.scientiamobile.wurfl.core.utils;

import java.util.StringTokenizer;

public class Version implements Comparable {
  private int[] a;
  
  private final char b;
  
  private Version(int[] paramArrayOfint, char paramChar) {
    this.a = paramArrayOfint;
    this.b = paramChar;
  }
  
  public int compareTo(Version paramVersion) {
    int j = Math.min(this.a.length, paramVersion.a.length);
    int k = Math.max(this.a.length, paramVersion.a.length);
    byte b;
    for (b = 0; b < j; b++) {
      int m;
      if ((m = Integer.valueOf(this.a[b]).compareTo(Integer.valueOf(paramVersion.a[b]))) != 0)
        return m; 
    } 
    Version version = ((b = (this.a.length > paramVersion.a.length) ? 1 : 0) != 0) ? this : paramVersion;
    for (int i = j + 1; i < k; i++) {
      if ((j = version.a[i]) > 0)
        return (b != 0) ? j : -j; 
    } 
    return 0;
  }
  
  public int compareTo(String paramString) {
    return compareTo(valueOf(paramString));
  }
  
  public int getDigitAtOrZero(int paramInt) {
    return (paramInt < this.a.length) ? getDigitAtOrThrow(paramInt) : 0;
  }
  
  public int getDigitAtOrThrow(int paramInt) {
    return this.a[paramInt];
  }
  
  public String toString() {
    StringBuilder stringBuilder;
    (stringBuilder = new StringBuilder()).append(this.a[0]);
    for (byte b = 1; b < this.a.length; b++) {
      stringBuilder.append(this.b);
      stringBuilder.append(this.a[b]);
    } 
    return stringBuilder.toString();
  }
  
  public static Version valueOf(String paramString) {
    return valueOf(paramString, '.');
  }
  
  public static Version valueOf(String paramString, char paramChar) {
    if (paramString == null || paramString.length() == 0)
      throw new IllegalArgumentException("Input String cannot be null or empty"); 
    String str = new String(new char[] { paramChar });
    StringTokenizer stringTokenizer;
    int[] arrayOfInt = new int[(stringTokenizer = new StringTokenizer(paramString, str)).countTokens()];
    byte b = 0;
    while (stringTokenizer.hasMoreTokens())
      arrayOfInt[b++] = Integer.valueOf(stringTokenizer.nextToken()).intValue(); 
    return new Version(arrayOfInt, paramChar);
  }
  
  public boolean equals(Object paramObject) {
    return (paramObject == null || !(paramObject instanceof Version)) ? false : ((compareTo((Version)paramObject) == 0));
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\cor\\utils\Version.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */