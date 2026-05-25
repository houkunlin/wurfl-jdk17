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

   public final String match(Collection var1, String var2, int var3) {
      return this.match(var1, var2, var3, 0);
   }

   public final String match(Collection var1, String var2, int var3, int var4) {
      String var5 = null;
      int var6 = var3 + 1;
      int var7 = var2.length();
      Iterator var10 = var1.iterator();
      int var8 = var2.length();

      while(var10.hasNext() && var7 > 0) {
         String var9;
         if (Math.abs((var9 = (String)var10.next()).length() - var2.length()) <= var3 && ((var7 = getLevenshteinDistance(var9, var2, var9.length(), var8, var3, var4)) < var6 || var7 == 0)) {
            var6 = var7;
            var5 = var9;
         }
      }

      return var5;
   }

   public static int getLevenshteinDistance(String var0, String var1, int var2, int var3, int var4, int var5) {
      while(true) {
         if (var0 != null && var1 != null) {
            if (var4 == 0) {
               if (var0.equals(var1)) {
                  return 0;
               }

               return Integer.MAX_VALUE;
            }

            if (var2 > var3) {
               String var10000 = var1;
               int var10002 = var3;
               var3 = var2;
               var2 = var10002;
               var1 = var0;
               var0 = var10000;
               continue;
            }

            if (var3 < var5) {
               return Integer.MAX_VALUE;
            }

            if (var2 == 0) {
               return var3;
            }

            int[] var6 = new int[256];

            for(int var7 = var5; var7 < var3; ++var7) {
               ++var6[(char)(var1.charAt(var7) & 255)];
            }

            for(int var20 = var5; var20 < var2; ++var20) {
               --var6[(char)(var0.charAt(var20) & 255)];
            }

            int var21 = 0;
            var4 <<= 1;

            for(char var8 = ' '; var8 < 'z'; ++var8) {
               if ((var21 += Math.abs(var6[var8])) > var4) {
                  return Integer.MAX_VALUE;
               }
            }

            var0 = var0.substring(var5);
            var2 -= var5;
            var1 = var1.substring(var5);
            var3 -= var5;
            int[] var23 = new int[var2 + 1];
            int[] var15 = new int[var2 + 1];

            for(int var16 = 0; var16 <= var2; var23[var16] = var16++) {
            }

            for(int var19 = 1; var19 <= var3; ++var19) {
               var21 = var1.charAt(var19 - 1);
               var15[0] = var19;

               for(int var17 = 1; var17 <= var2; ++var17) {
                  int var9 = var0.charAt(var17 - 1) == var21 ? 0 : 1;
                  var15[var17] = Math.min(Math.min(var15[var17 - 1] + 1, var23[var17] + 1), var23[var17 - 1] + var9);
               }

               int[] var18 = var23;
               var23 = var15;
               var15 = var18;
            }

            return var23[var2];
         }

         throw new IllegalArgumentException("Strings must not be null");
      }
   }

   public final String toString() {
      return this.getName();
   }
}
