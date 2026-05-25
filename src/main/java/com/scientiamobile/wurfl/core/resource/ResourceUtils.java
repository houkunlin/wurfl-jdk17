package com.scientiamobile.wurfl.core.resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceUtils {
   private ResourceUtils() {
   }

   public static String getBuildId() {
      String var0;
      String var1 = var0 = getFullBuildId();
      if (var0 != null && !"unknown".equals(var0)) {
         int var2;
         var1 = (var2 = var0.indexOf(":")) != -1 ? var0.substring(var2 + 1) : var0;
      }

      return var1;
   }

   public static String getFullBuildId() {
      String var0 = "unknown";
      InputStream var1;
      if ((var1 = ResourceUtils.class.getResourceAsStream("/ca")) != null) {
         BufferedReader var12 = new BufferedReader(new InputStreamReader(var1));

         try {
            String var2 = (var0 = var12.readLine()) != null ? var0 : "unknown";
            return var2;
         } catch (IOException var9) {
         } finally {
            try {
               var12.close();
            } catch (IOException var8) {
            }

         }

         return var0;
      } else {
         return var0;
      }
   }
}
