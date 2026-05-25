package com.scientiamobile.wurfl.core.web.introspector;

final class b implements Comparable {
   private String a;
   private String b;
   private String c;
   private String d;

   private b(String var1, String var2, String var3, String var4) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
   }

   public final String toString() {
      String var1;
      int var2;
      if ((var2 = (var1 = this.a).indexOf("Matcher")) > 0) {
         var1 = var1.substring(0, var2);
      }

      return var1 + "\t" + this.b + "\t" + this.c + "\t" + this.d;
   }

   // $FF: synthetic method
   b(String var1, String var2, String var3, String var4, byte var5) {
      this(var1, var2, var3, var4);
   }
}
