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
   private final ResourceInput resourceInput;
   private String version;
   private Set<String> includedCapabilities;
   private String originalPath;
   private static final SAXParserFactory SAX_PARSER_FACTORY;

   public XMLResource(String var1) {
      this.originalPath = var1;
      this.resourceInput = new ResourceInput(var1);
   }

   public XMLResource(File var1) {
      this.originalPath = var1.getAbsolutePath();
      this.resourceInput = new ResourceInput(var1);
   }

   public XMLResource(URI var1) {
      this.resourceInput = new ResourceInput(var1);
   }

   public XMLResource(InputStream var1, String var2) {
      this.resourceInput = new ResourceInput(var1, var2);
   }

   public ModelDevicesSnapshot getData(String... var1) {
      if (var1 != null) {
         this.includedCapabilities = new HashSet<>(var1.length);
         for(String var5 : var1) {
            this.includedCapabilities.add(var5);
         }
      } else {
         this.includedCapabilities = new HashSet<>(0);
      }

      ModelDevicesSnapshot var7 = this.parseSnapshot(this.resourceInput.openInputStream());
      this.resourceInput.reset();
      return var7;
   }

   public String getOriginalPath() {
      return this.originalPath;
   }

   public String getInfo() {
      return this.resourceInput.getResourceName();
   }

   public String getVersion() {
      return this.version;
   }

   public void release() {
      this.resourceInput.close();
   }

   private ModelDevicesSnapshot parseSnapshot(InputStream var1) {
      WurflXmlHandler var2 = new WurflXmlHandler(this.includedCapabilities);

      try {
         SAX_PARSER_FACTORY.newSAXParser().parse(var1, var2);
      } catch (Exception var6) {
         throw new WURFLResourceException(this, var6);
      }

      String var3 = this.getInfo();
      String var7 = var2.getWurflVersion();
      String var4 = var2.getWurflLastUpdated();
      String var5 = var2.getWurflSmid();
      this.version = var7 != null && var7.length() != 0 ? var7 : (var4 != null && var4.length() != 0 ? var4 : "(no version info)");
      boolean var8 = var2.isPatch();
      ModelDevices var9 = var2.getDevices();
      return new ModelDevicesSnapshot(var3, this.version, var8, var9, var5);
   }

   static {
      LoggerFactory.getLogger(XMLResource.class);
      SAX_PARSER_FACTORY = SAXParserFactory.newInstance();
   }
}
