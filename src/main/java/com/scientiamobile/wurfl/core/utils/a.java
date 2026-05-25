package com.scientiamobile.wurfl.core.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class a {
   private int[] a;
   private boolean[] b;
   private char[][] c;
   private int[][] d;

   public a(List var1) {
      ArrayList var2 = new ArrayList();
      b var3 = new b();
      Iterator var9 = var1.iterator();

      while(var9.hasNext()) {
         String var4;
         if ((var4 = (String)var9.next()) != null && var4.length() != 0) {
            var3.a(var4);
         }
      }

      LinkedList var10 = new LinkedList();
      var3.a(var3);
      var2.add(var3);

      for(int var11 = 0; var11 < var3.c(); ++var11) {
         b var5;
         (var5 = var3.a(var11)).a(var3);
         var10.add(var5);
      }

      while(!var10.isEmpty()) {
         b var12 = (b)var10.poll();
         var2.add(var12);

         for(Character var8 : var12.d()) {
            b var14 = var12.a(var8);
            var10.add(var14);

            b var6;
            for(var6 = var12.e(); var6.a(var8) == null && var6 != var3; var6 = var6.e()) {
            }

            if ((var6 = var6.a(var8)) != null) {
               var14.a(var6);
            } else {
               var14.a(var3);
            }
         }
      }

      this.a = new int[var2.size()];
      this.b = new boolean[var2.size()];
      this.c = new char[var2.size()][];
      this.d = new int[var2.size()][];

      for(int var13 = 0; var13 < var2.size(); ++var13) {
         b var15 = (b)var2.get(var13);
         this.a[var13] = var2.indexOf(var15.e());
         this.b[var13] = var15.b();
         Set var17 = var15.a().entrySet();
         this.c[var13] = new char[var17.size()];
         this.d[var13] = new int[var17.size()];
         int var19 = 0;

         for(Map.Entry var18 : var17) {
            this.c[var13][var19] = (Character)var18.getKey();
            this.d[var13][var19] = var2.indexOf(var18.getValue());
            ++var19;
         }
      }

   }

   public final boolean a(String var1) {
      char[] var2 = var1.toLowerCase().toCharArray();
      a var9 = this;
      int var3 = 0;

      label34:
      for(char var6 : var2 = var2) {
         while(true) {
            char[] var7 = var9.c[var3];

            for(int var8 = 0; var8 < var7.length; ++var8) {
               if (var7[var8] == var6) {
                  var3 = var9.d[var3][var8];
                  if (var9.b[var3]) {
                     return true;
                  }
                  continue label34;
               }
            }

            if (var3 == 0) {
               break;
            }

            var3 = var9.a[var3];
         }
      }

      return false;
   }
}
