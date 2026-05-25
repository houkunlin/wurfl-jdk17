package com.scientiamobile.wurfl.core.resource;

import com.scientiamobile.wurfl.core.resource.exc.WURFLResourceException;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import javax.xml.parsers.SAXParserFactory;
import org.slf4j.LoggerFactory;

public class XMLResource implements WURFLResource {
   private final b a;
   private String b;
   private Set c;
   private String d;
   private static final SAXParserFactory e;

   public XMLResource(String var1) {
      this.d = var1;
      this.a = new b(var1);
   }

   public XMLResource(File var1) {
      this.d = var1.getAbsolutePath();
      this.a = new b(var1);
   }

   public XMLResource(URI var1) {
      this.a = new b(var1);
   }

   public XMLResource(InputStream var1, String var2) {
      this.a = new b(var1, var2);
   }

   public c getData(String... var1) {
      XMLResource var6 = this;
      if (var1 != null) {
         this.c = new HashSet(var1.length);

         String[] var8;
         for(String var5 : var8 = var1) {
            var6.c.add(var5);
         }
      } else {
         this.c = new HashSet(0);
      }

      c var7 = this.a(this.a.c());
      this.a.d();
      return var7;
   }

   public String getOriginalPath() {
      return this.d;
   }

   public String getInfo() {
      return this.a.a();
   }

   public String getVersion() {
      return this.b;
   }

   public void release() {
      this.a.b();
   }

   private c a(InputStream var1) {
      g var2 = new g(this.c, (byte)0);

      try {
         e.newSAXParser().parse(var1, var2);
      } catch (Exception var6) {
         throw new WURFLResourceException(this, var6);
      }

      String var3 = this.getInfo();
      String var7 = g.a(var2);
      String var4 = g.b(var2);
      String var5 = g.c(var2);
      this.b = var7 != null && var7.length() != 0 ? var7 : (var4 != null && var4.length() != 0 ? var4 : "(no version info)");
      boolean var8 = g.d(var2);
      ModelDevices var9 = g.e(var2);
      return new c(var3, this.b, var8, var9, var5);
   }

   static {
      LoggerFactory.getLogger(XMLResource.class);
      e = SAXParserFactory.newInstance();
   }
}
