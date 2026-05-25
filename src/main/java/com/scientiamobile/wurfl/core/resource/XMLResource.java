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
   private final ResourceInput a;
   private String b;
   private Set c;
   private String d;
   private static final SAXParserFactory e;

   public XMLResource(String var1) {
      this.d = var1;
      this.a = new ResourceInput(var1);
   }

   public XMLResource(File var1) {
      this.d = var1.getAbsolutePath();
      this.a = new ResourceInput(var1);
   }

   public XMLResource(URI var1) {
      this.a = new ResourceInput(var1);
   }

   public XMLResource(InputStream var1, String var2) {
      this.a = new ResourceInput(var1, var2);
   }

   public ModelDevicesSnapshot getData(String... var1) {
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

      ModelDevicesSnapshot var7 = this.a(this.a.openInputStream());
      this.a.reset();
      return var7;
   }

   public String getOriginalPath() {
      return this.d;
   }

   public String getInfo() {
      return this.a.getResourceName();
   }

   public String getVersion() {
      return this.b;
   }

   public void release() {
      this.a.close();
   }

   private ModelDevicesSnapshot a(InputStream var1) {
      WurflXmlHandler var2 = new WurflXmlHandler(this.c);

      try {
         e.newSAXParser().parse(var1, var2);
      } catch (Exception var6) {
         throw new WURFLResourceException(this, var6);
      }

      String var3 = this.getInfo();
      String var7 = var2.getWurflVersion();
      String var4 = var2.getWurflLastUpdated();
      String var5 = var2.getWurflSmid();
      this.b = var7 != null && var7.length() != 0 ? var7 : (var4 != null && var4.length() != 0 ? var4 : "(no version info)");
      boolean var8 = var2.isPatch();
      ModelDevices var9 = var2.getDevices();
      return new ModelDevicesSnapshot(var3, this.b, var8, var9, var5);
   }

   static {
      LoggerFactory.getLogger(XMLResource.class);
      e = SAXParserFactory.newInstance();
   }
}
