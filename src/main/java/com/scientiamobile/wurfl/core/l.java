package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class l implements Serializable {
   private static final long serialVersionUID = 4934582187956400034L;
   private String a = null;
   private String b = null;
   private String[] c;

   public final String a() {
      return this.a;
   }

   public final void a(String var1) {
      this.a = var1;
   }

   public final String b() {
      return this.b;
   }

   public final void b(String var1) {
      this.b = var1;
   }

   public l() {
   }

   public final boolean a(String var1, Pattern var2, String var3, String var4) {
      if (this.a(var2, var1) != null) {
         this.a = var3.trim();
         if (var4 != null) {
            this.b = var4.trim();
         }

         return true;
      } else {
         this.c = null;
         return false;
      }
   }

   public final boolean a(String var1, Pattern var2, int var3) {
      Matcher var4;
      if ((var4 = this.a(var2, var1)) != null) {
         this.a = var4.group(var3) == null ? "" : var4.group(var3).trim();
         return true;
      } else {
         this.c = null;
         return false;
      }
   }

   public final boolean a(String var1, Pattern var2, String var3, int var4) {
      Matcher var5;
      if ((var5 = this.a(var2, var1)) != null) {
         if (var3 != null) {
            this.a = var3.trim();
         }

         String var6 = var5.group(var4);
         this.b = var6 == null ? "" : var6.trim();
         return true;
      } else {
         this.c = null;
         return false;
      }
   }

   public final boolean b(String var1, Pattern var2, int var3) {
      Matcher var4;
      if ((var4 = this.a(var2, var1)) != null) {
         this.a = var4.group(1) == null ? "" : var4.group(1).trim();
         this.b = var4.group(var3) == null ? "" : var4.group(var3).trim();
         return true;
      } else {
         this.c = null;
         return false;
      }
   }

   public final boolean a(String var1, String var2, String var3) {
      if (StringMatchUtils.indexOf(var1, var2) >= 0) {
         this.a = var3.trim();
         return true;
      } else {
         return false;
      }
   }

   private Matcher a(Pattern var1, String var2) {
      Matcher var3;
      if (!(var3 = var1.matcher(var2)).find()) {
         this.c = null;
         return null;
      } else {
         this.c = new String[var3.groupCount() + 1];

         for(int var4 = 0; var4 < this.c.length; ++var4) {
            this.c[var4] = var3.group(var4);
         }

         return var3;
      }
   }

   public final String a(int var1) {
      return this.c == null ? null : this.c[var1];
   }

   public final String toString() {
      return "[name: " + this.a + " - version: " + this.b + "]";
   }
}
