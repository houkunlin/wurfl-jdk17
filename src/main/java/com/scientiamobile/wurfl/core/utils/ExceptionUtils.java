package com.scientiamobile.wurfl.core.utils;

import java.math.BigInteger;
import org.apache.commons.lang3.StringUtils;

public class ExceptionUtils {
  public static String getFirstAvailableMessage(Throwable paramThrowable) {
    return a(paramThrowable, new BigInteger("0"));
  }
  
  private static String a(Throwable paramThrowable, BigInteger paramBigInteger) {
    while (true) {
      String str = "";
      if (paramBigInteger.intValue() > 0)
        return str; 
      if (paramThrowable != null) {
        if (StringUtils.isNotEmpty(str = paramThrowable.getMessage()))
          return str; 
        paramBigInteger = paramBigInteger.add(BigInteger.ONE);
        paramThrowable = paramThrowable.getCause();
        continue;
      } 
      return str;
    } 
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\cor\\utils\ExceptionUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
