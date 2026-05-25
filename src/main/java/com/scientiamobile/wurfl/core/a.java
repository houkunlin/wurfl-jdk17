package com.scientiamobile.wurfl.core;

import java.util.Map;

abstract class a {
   public abstract String a(String var1);

   public final int b(String var1) {
      String var2 = this.a(var1);

      try {
         return Integer.parseInt(var2);
      } catch (NumberFormatException var3) {
         throw new NumberFormatException("WURFL invalid capability value: " + var1 + " expected an integer, received: \"" + var2 + "\"");
      }
   }

   public abstract Map a();
}
