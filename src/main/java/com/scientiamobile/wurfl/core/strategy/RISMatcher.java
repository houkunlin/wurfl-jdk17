package com.scientiamobile.wurfl.core.strategy;

import java.util.ArrayList;
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

   public final String match(Collection var1, String var2, int var3) {
      String var4 = null;
      int var5 = var2.length();
      ArrayList var13 = (ArrayList)var1;
      int var6 = -1;
      int var7 = -1;
      int var8 = 0;
      int var9 = var13.size() - 1;

      while(var8 <= var9 && var7 < var5) {
         int var10 = (var8 + var9) / 2;
         String var11 = (String)var13.get(var10);
         int var12;
         if ((var12 = a(var2, var11)) > var7) {
            var6 = var10;
            var7 = var12;
         }

         int var21;
         if ((var21 = var11.compareTo(var2)) < 0) {
            var8 = var10 + 1;
         } else {
            if (var21 <= 0) {
               break;
            }

            var9 = var10 - 1;
         }
      }

      if (var7 >= var3) {
         int var17 = var7;
         var3 = var6;
         String var14 = var2;
         var5 = var7;
         ListIterator var20 = var13.listIterator(var6);

         while(var20.hasPrevious() && var5 == var17) {
            String var19 = (String)var20.previous();
            if ((var5 = a(var14, var19)) == var17) {
               --var3;
            }
         }

         var4 = (String)var13.get(var3);
      }

      return var4;
   }

   private static int a(String var0, String var1) {
      int var2 = Math.min(var0.length(), var1.length());

      int var3;
      for(var3 = 0; var3 < var2 && var0.charAt(var3) == var1.charAt(var3); ++var3) {
      }

      return var3;
   }

   public final String toString() {
      return this.getName();
   }
}
