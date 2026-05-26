package com.scientiamobile.wurfl.core.resource;

import java.io.InputStream;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.helpers.DefaultHandler;

public class XmlFileLoader {
   private final ResourceInput resourceInput;
   private DefaultHandler handler;

   public XmlFileLoader(String path, DefaultHandler handler) {
      this.resourceInput = new ResourceInput(path);
      this.handler = handler;
   }

   public final boolean parseFile() {
      InputStream inputStream = this.resourceInput.openInputStream();

      try {
         SAXParserFactory.newInstance().newSAXParser().parse(inputStream, this.handler);
      } catch (Exception e) {
         throw new RuntimeException(e);
      } finally {
         try {
            inputStream.close();
         } catch (Exception e) {
         }
      }

      return true;
   }
}
