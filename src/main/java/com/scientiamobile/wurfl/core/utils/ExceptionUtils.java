package com.scientiamobile.wurfl.core.utils;

import java.math.BigInteger;
import org.apache.commons.lang3.StringUtils;

public class ExceptionUtils {
   public static String getFirstAvailableMessage(Throwable var0) {
      return a(var0, new BigInteger("0"));
   }

   private static String a(Throwable var0, BigInteger var1) {
      while(true) {
         String var2 = "";
         if (var1.intValue() > 0) {
            return var2;
         }

         if (var0 == null) {
            return var2;
         }

         if (StringUtils.isNotEmpty(var2 = var0.getMessage())) {
            return var2;
         }

         var1 = var1.add(BigInteger.ONE);
         var0 = var0.getCause();
      }
   }
}
