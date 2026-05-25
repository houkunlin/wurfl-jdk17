package com.scientiamobile.wurfl.core.resource;

import java.io.InputStream;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.helpers.DefaultHandler;

public class XmlFileLoader {
   private final b a;
   private DefaultHandler b;

   public XmlFileLoader(String var1, DefaultHandler var2) {
      this.a = new b(var1);
      this.b = var2;
   }

   public final boolean parseFile() {
      InputStream var1 = this.a.c();

      try {
         SAXParserFactory.newInstance().newSAXParser().parse(var1, this.b);
      } catch (Exception var5) {
         throw new RuntimeException(var5);
      } finally {
         var1.close();
      }

      return true;
   }
}
