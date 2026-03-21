package com.scientiamobile.wurfl.core.utils;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.strategy.RISMatcher;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

public final class StringMatchUtils {
  public static final String NULL_STRING = null;
  
  public static final String EMPTY_STRING = "";
  
  public static final int INDEX_NOT_FOUND = -1;
  
  public static int firstSlash(String paramString) {
    return ordinalIndexOfOrNotFound(paramString, "/", 1);
  }
  
  public static int secondSlash(String paramString) {
    return ordinalIndexOfOrNotFound(paramString, "/", 2);
  }
  
  public static int firstCloseParenthesis(String paramString) {
    return ordinalIndexOfOrNotFound(paramString, ")", 1);
  }
  
  public static int firstOpenParenthesis(String paramString) {
    return ordinalIndexOfOrNotFound(paramString, "(", 1);
  }
  
  public static int firstSpace(String paramString) {
    return ordinalIndexOfOrNotFound(paramString, " ", 1);
  }
  
  public static int ordinalIndexOfOrNotFound(String paramString1, String paramString2, int paramInt) {
    int i;
    return (paramString2 == null) ? -1 : (((i = StringUtils.ordinalIndexOf(paramString1, paramString2, paramInt)) == -1) ? -1 : (i + paramString2.length()));
  }
  
  public static int firstSemiColon(String paramString) {
    return ordinalIndexOfOrLength(paramString, ";", 1);
  }
  
  public static int indexOfOrLength(String paramString1, String paramString2) {
    return indexOfOrLength(paramString1, paramString2, 0);
  }
  
  public static int indexOfOrLength(String paramString1, String paramString2, int paramInt) {
    return ordinalIndexOfOrLength(paramString1, paramString2, 1, paramInt);
  }
  
  public static int ordinalIndexOfOrLength(String paramString1, String paramString2, int paramInt) {
    return ordinalIndexOfOrLength(paramString1, paramString2, paramInt, 0);
  }
  
  public static int ordinalIndexOfOrLength(String paramString1, String paramString2, int paramInt1, int paramInt2) {
    String str = StringUtils.defaultString(paramString1);
    if (paramInt2 < 0)
      return str.length(); 
    int i;
    if ((i = StringUtils.ordinalIndexOf(StringUtils.substring(str, paramInt2), paramString2, paramInt1)) >= 0) {
      i += paramInt2;
    } else {
      i = paramString1.length();
    } 
    return i;
  }
  
  public static String risMatch(Collection paramCollection, String paramString, int paramInt) {
    return RISMatcher.INSTANCE.match(paramCollection, paramString, paramInt);
  }
  
  public static String hierarchyAsString(List paramList) {
    StringBuilder stringBuilder = new StringBuilder();
    Iterator<ModelDevice> iterator = paramList.iterator();
    while (iterator.hasNext()) {
      stringBuilder.append(((ModelDevice)iterator.next()).getID());
      if (iterator.hasNext())
        stringBuilder.append(" -> "); 
    } 
    return stringBuilder.toString();
  }
  
  public static int indexOf(String paramString1, String paramString2) {
    return StringUtils.indexOf(paramString1, paramString2);
  }
  
  public static int indexOf(String paramString1, String paramString2, int paramInt) {
    return StringUtils.indexOf(paramString1, paramString2, paramInt);
  }
  
  public static String removeSubstringBefore(String paramString1, String paramString2) {
    int i;
    return ((i = paramString1.indexOf(paramString2)) > 0) ? paramString1.substring(i) : paramString1;
  }
  
  public static boolean containsAnyOf(String paramString, String... paramVarArgs) {
    for (byte b = 0; b < paramVarArgs.length; b++) {
      if (paramString.indexOf(paramVarArgs[b]) != -1)
        return true; 
    } 
    return false;
  }
  
  public static boolean containsAnyOfIgnoreCase(String paramString, String... paramVarArgs) {
    return containsAnyOf(paramString.toLowerCase(), paramVarArgs);
  }
  
  public static boolean startsWithAnyOf(String paramString, String... paramVarArgs) {
    int i = (paramVarArgs = paramVarArgs).length;
    for (byte b = 0; b < i; b++) {
      String str = paramVarArgs[b];
      if (paramString.startsWith(str))
        return true; 
    } 
    return false;
  }
  
  public static int indexOfAnyOrLength(String paramString, String... paramVarArgs) {
    return indexOfAnyOrLength(paramString, paramVarArgs, 0);
  }
  
  public static int indexOfAnyOrLength(String paramString, String[] paramArrayOfString, int paramInt) {
    int i;
    return (paramInt == -1) ? paramString.length() : (((i = StringUtils.indexOfAny((paramInt > 0) ? paramString.substring(paramInt) : paramString, paramArrayOfString)) >= 0) ? (i + paramInt) : paramString.length());
  }
  
  public static boolean containsAllOf(String paramString, String... paramVarArgs) {
    for (byte b = 0; b < paramVarArgs.length; b++) {
      if (paramString.indexOf(paramVarArgs[b]) == -1)
        return false; 
    } 
    return true;
  }
  
  public static boolean containsAllOf(String paramString, List<String> paramList) {
    for (byte b = 0; b < paramList.size(); b++) {
      if (paramString.indexOf(paramList.get(b)) == -1)
        return false; 
    } 
    return true;
  }
  
  public static String format(Set paramSet) {
    StringBuilder stringBuilder = new StringBuilder(10);
    for (String str : paramSet)
      stringBuilder.append(str).append('\n'); 
    return stringBuilder.toString();
  }
  
  public static String rtrim(String paramString, char... paramVarArgs) {
    int i;
    for (i = paramString.length(); i > 0 && ArrayUtils.contains(paramVarArgs, paramString.charAt(i - 1)); i--);
    return paramString.substring(0, i);
  }
  
  public static Integer firstChar(String paramString, char paramChar) {
    int i;
    return Integer.valueOf(((i = paramString.indexOf(paramChar)) != -1) ? (i + 1) : -1);
  }
  
  public static String rawdecode(String paramString1, String paramString2) {
    if (StringUtils.isEmpty(paramString1))
      return paramString1; 
    int i = paramString1.length();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(i);
    try {
      for (byte b = 0; b < i; b++) {
        if (paramString1.charAt(b) == '%' && b + 2 < i) {
          char c1 = paramString1.charAt(b + 1);
          char c2 = paramString1.charAt(b + 2);
          int j = Character.digit(c1, 16);
          int k = Character.digit(c2, 16);
          if (j == -1 || k == -1) {
            a(byteArrayOutputStream, paramString1, b, paramString2);
          } else {
            j = (char)((j << 4) + k);
            byteArrayOutputStream.write(j);
            b += 2;
          } 
        } else {
          a(byteArrayOutputStream, paramString1, b, paramString2);
        } 
      } 
    } finally {
      try {
        byteArrayOutputStream.close();
      } catch (IOException iOException) {}
    } 
    return new String(byteArrayOutputStream.toByteArray(), paramString2);
  }
  
  private static void a(ByteArrayOutputStream paramByteArrayOutputStream, String paramString1, int paramInt, String paramString2) {
    char c = paramString1.charAt(paramInt);
    String str = new String(new char[] { c });
    try {
      paramByteArrayOutputStream.write(str.getBytes(paramString2));
      return;
    } catch (IOException iOException) {
      paramByteArrayOutputStream.write(c);
      return;
    } 
  }
  
  public static byte[] charToBytesUTFCustom(char paramChar) {
    byte[] arrayOfByte;
    (arrayOfByte = new byte[2])[0] = (byte)(paramChar >> 8);
    arrayOfByte[1] = (byte)paramChar;
    return arrayOfByte;
  }
  
  public static String rawdecode(String paramString) {
    return rawdecode(paramString, "UTF-8");
  }
  
  static {
    LoggerFactory.getLogger(StringMatchUtils.class);
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\cor\\utils\StringMatchUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */