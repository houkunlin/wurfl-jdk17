package com.scientiamobile.wurfl.core.utils;

import java.math.BigInteger;
import org.apache.commons.lang3.StringUtils;

public class ExceptionUtils {
   public static String getFirstAvailableMessage(Throwable throwable) {
      return getFirstAvailableMessage(throwable, new BigInteger("0"));
   }

   private static String getFirstAvailableMessage(Throwable throwable, BigInteger depth) {
      while(true) {
         String message = "";
         if (depth.intValue() > 0) {
            return message;
         }

         if (throwable == null) {
            return message;
         }

         message = throwable.getMessage();
      if (StringUtils.isNotEmpty(message)) {
            return message;
         }

         depth = depth.add(BigInteger.ONE);
         throwable = throwable.getCause();
      }
   }
}
