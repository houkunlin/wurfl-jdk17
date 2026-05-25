package com.scientiamobile.wurfl.core;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

final class k extends DefaultHandler {
   // $FF: synthetic field
   private j a;

   private k(j var1) {
      this.a = var1;
      super();
   }

   public final void startElement(String var1, String var2, String var3, Attributes var4) {
      if (var3.equals("wurfl-api-config")) {
         var1 = var4.getValue("engine-target");
         if (j.a(this.a) == null) {
            if (var1 == null || "performance".equals(var1) || "accuracy".equals(var1)) {
               j.a(this.a, EngineTarget.defaultTarget);
               return;
            }

            j.a(this.a, EngineTarget.fastDesktopBrowserMatch);
         }
      }

   }

   // $FF: synthetic method
   k(j var1, byte var2) {
      this(var1);
   }
}
