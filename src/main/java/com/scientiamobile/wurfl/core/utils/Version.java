package com.scientiamobile.wurfl.core.utils;

import java.util.StringTokenizer;

public class Version implements Comparable {
   private int[] a;
   private final char b;

   private Version(int[] var1, char var2) {
      this.a = var1;
      this.b = var2;
   }

   public int compareTo(Version var1) {
      int var2 = Math.min(this.a.length, var1.a.length);
      int var3 = Math.max(this.a.length, var1.a.length);

      for(int var4 = 0; var4 < var2; ++var4) {
         int var5;
         if ((var5 = Integer.valueOf(this.a[var4]).compareTo(var1.a[var4])) != 0) {
            return var5;
         }
      }

      boolean var8;
      Version var9 = (var8 = this.a.length > var1.a.length) ? this : var1;

      for(int var6 = var2 + 1; var6 < var3; ++var6) {
         if ((var2 = var9.a[var6]) > 0) {
            if (var8) {
               return var2;
            }

            return -var2;
         }
      }

      return 0;
   }

   public int compareTo(String var1) {
      return this.compareTo(valueOf(var1));
   }

   public int getDigitAtOrZero(int var1) {
      return var1 < this.a.length ? this.getDigitAtOrThrow(var1) : 0;
   }

   public int getDigitAtOrThrow(int var1) {
      return this.a[var1];
   }

   public String toString() {
      StringBuilder var1;
      (var1 = new StringBuilder()).append(this.a[0]);

      for(int var2 = 1; var2 < this.a.length; ++var2) {
         var1.append(this.b);
         var1.append(this.a[var2]);
      }

      return var1.toString();
   }

   public static Version valueOf(String var0) {
      return valueOf(var0, '.');
   }

   public static Version valueOf(String var0, char var1) {
      if (var0 != null && var0.length() != 0) {
         String var2 = new String(new char[]{var1});
         StringTokenizer var4;
         int[] var5 = new int[(var4 = new StringTokenizer(var0, var2)).countTokens()];

         for(int var3 = 0; var4.hasMoreTokens(); var5[var3++] = Integer.valueOf(var4.nextToken())) {
         }

         return new Version(var5, var1);
      } else {
         throw new IllegalArgumentException("Input String cannot be null or empty");
      }
   }

   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof Version) {
         return this.compareTo((Version)var1) == 0;
      } else {
         return false;
      }
   }
}
