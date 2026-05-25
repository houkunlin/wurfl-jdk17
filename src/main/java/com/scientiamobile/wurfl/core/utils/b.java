package com.scientiamobile.wurfl.core.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

final class b {
   private b a;
   private boolean b;
   private TreeMap c;

   public b() {
      this(false);
   }

   private b(boolean var1) {
      this.b = var1;
      this.a = null;
      this.c = new TreeMap();
   }

   public final void a(String var1) {
      while(true) {
         b var2;
         if ((var2 = (b)this.c.get(var1.charAt(0))) == null) {
            if (var1.length() == 1) {
               var2 = new b(true);
               this.c.put(var1.charAt(0), var2);
               return;
            }

            var2 = new b(false);
            this.c.put(var1.charAt(0), var2);
            var1 = var1.substring(1);
            this = var2;
         } else {
            if (var1.length() <= 1) {
               return;
            }

            var1 = var1.substring(1);
            this = var2;
         }
      }
   }

   public final TreeMap a() {
      return this.c;
   }

   public final boolean b() {
      return this.b;
   }

   public final int c() {
      return this.c.size();
   }

   public final b a(int var1) {
      Iterator var2 = this.c.entrySet().iterator();
      int var3 = 0;

      while(var2.hasNext()) {
         if (var3 == var1) {
            return (b)((Map.Entry)var2.next()).getValue();
         }

         ++var3;
         var2.next();
      }

      return null;
   }

   public final b a(char var1) {
      return (b)this.c.get(var1);
   }

   public final Set d() {
      return this.c.keySet();
   }

   public final void a(b var1) {
      this.a = var1;
   }

   public final b e() {
      return this.a;
   }
}
